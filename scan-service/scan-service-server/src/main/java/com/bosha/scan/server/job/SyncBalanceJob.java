package com.bosha.scan.server.job;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


import com.bosha.scan.api.entity.AddressBalance;
import com.bosha.scan.server.config.ScanServiceConfig;
import com.bosha.scan.server.dto.Transactions;
import com.bosha.scan.server.mapper.AddressBalanceMapper;
import com.bosha.scan.server.redis.CacheKey;
import com.mongodb.MongoException;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.util.ShardingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.DefaultBlockParameterName;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SyncBalanceJob
 * @Author liqingping
 * @Date 2020-04-10 16:40
 */
@Component
@Slf4j
public class SyncBalanceJob {

    @Autowired
    private AddressBalanceMapper addressBalanceMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ScanServiceConfig scanServiceConfig;

    public static ExecutorService executorService;

    static {
        executorService = new ThreadPoolExecutor(1000, 1000, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @XxlJob("syncBalance")//每2min运行一次
    public ReturnT<String> syncBalanceJob(String s) {
        int batch = 1000;
        if (StringUtils.isNumeric(s)) {
            batch = Integer.parseInt(s);
        }
        long start = System.currentTimeMillis();
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        int total = shardingVO.getTotal();
        int index = shardingVO.getIndex();
        RBucket<Date> bucket = redis.getBucket(CacheKey.Scan.SYNC_BALANCE.getKey() + index);
        if (bucket.isExists())
            return ReturnT.SUCCESS;
        bucket.set(new Date(), 10, TimeUnit.MINUTES);
        int count = addressBalanceMapper.count(total, index);
        int time = count % batch == 0 ? count / batch : (count / batch) + 1;
        log.info("【更新地址余额】开始：分片index={}，batch={}，需要次数={}", index, batch, time);
        for (int i = 1; i <= time; i++) {
            List<AddressBalance> list = addressBalanceMapper.list(total, index, (i - 1) * batch, batch);
            List<Future<AddressBalance>> futureList = new ArrayList<>(list.size());
            for (AddressBalance addressBalance : list) {
                Future<AddressBalance> future = executorService.submit(() -> {
                    try {
                        addressBalance.setUpdateTime(new Date());
                        BigInteger balance = ScanServiceConfig.web3j.ethGetBalance(addressBalance.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
                        addressBalance.setBalance(new BigDecimal(balance).divide(ScanServiceConfig.RATE));
                        if (scanServiceConfig.isSyncTransactionCount()) {
                            Criteria criteria = new Criteria();
                            criteria.orOperator(Criteria.where("from").is(addressBalance.getAddress()), Criteria.where("to").is(addressBalance.getAddress()));
                            Long transactionCount = mongoTemplate.count(Query.query(criteria), Transactions.class);
                            addressBalance.setTransactionCount(transactionCount);
                        }
                    } catch (MongoException e) {
                        log.error(e.getMessage());
                    } catch (IOException e) {
                        log.error("批量更新地址余额失败：{}", e.getMessage());
                        return null;
                    }
                    return addressBalance;
                });
                futureList.add(future);
            }
            List<AddressBalance> updateList = new ArrayList<>(list.size());
            for (Future<AddressBalance> future : futureList) {
                AddressBalance addressBalance = null;
                try {
                    addressBalance = future.get();
                } catch (Exception e) {
                    log.error("批量更新地址余额失败：{}", e.getMessage());
                }
                if (addressBalance != null)
                    updateList.add(addressBalance);
            }
            if (CollectionUtils.isNotEmpty(updateList))
                addressBalanceMapper.updateBatchSelective(updateList);
            log.info("【更新地址余额】 -> [ {} ] ：batch={}，当前分片[totalCount={}，size={}，updateSize={}，耗时= {} 秒]",
                    i, batch, count, list.size(), updateList.size(), (System.currentTimeMillis() - start) / 1000D);
        }
        log.info("【更新地址余额】当前分片全部完成：total={}，index={}，当前分片[totalCount={} ，耗时= {} 秒]",
                total, index, count, (System.currentTimeMillis() - start) / 1000D);
        bucket.delete();
        return ReturnT.SUCCESS;
    }
}
