package com.bosha.scan.server.job;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.bosha.scan.api.entity.AddressBalance;
import com.bosha.scan.api.service.AddressBalanceService;
import com.bosha.scan.server.config.ScanServiceConfig;
import com.bosha.scan.server.dto.Blocks;
import com.bosha.scan.server.dto.Transactions;
import com.bosha.scan.server.mapper.AddressBalanceMapper;
import com.bosha.scan.server.redis.CacheKey;
import com.mongodb.MongoException;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
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
 * @DESCRIPTION: SyncBlockJob
 * @Author liqingping
 * @Date 2020-04-10 11:21
 */
@Component
@Slf4j
public class SyncAddressJob {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private AddressBalanceService addressBalanceService;
    @Autowired
    private ScanServiceConfig scanServiceConfig;
    @Autowired
    private AddressBalanceMapper addressBalanceMapper;

    @XxlJob("syncAddress")//每20s运行一次
    public ReturnT<String> syncAddress(String s) throws IOException {
        long start = System.currentTimeMillis();
        int batch = 300;
        if (StringUtils.isNumeric(s)) {
            batch = Integer.parseInt(s);
        }
        Long startBlock = null;
        RBucket<Long> bucket = redis.getBucket(CacheKey.Scan.BLOCK_NUM.getKey());
        if (!bucket.isExists())
            startBlock = 1L;
        else
            startBlock = bucket.get();
        long lastest = ScanServiceConfig.web3j.ethBlockNumber().send().getBlockNumber().longValue();
        long endBlock = lastest - 10;
        if (startBlock >= endBlock)
            return ReturnT.SUCCESS;
        if ((endBlock - startBlock) > batch)
            endBlock = startBlock + batch;
        Query query = Query.query(Criteria.where("number").gte(startBlock).lt(endBlock));
        List<Blocks> blocks = mongoTemplate.find(query, Blocks.class);
        Date date = new Date();
        if (CollectionUtils.isEmpty(blocks))
            return ReturnT.SUCCESS;
        log.info("【同步区块地址】开始，batch={}，startBlock={}，endBlock={}，maxBlock={}，size={}", batch, startBlock, endBlock, lastest, blocks.size());
        for (Blocks block : blocks) {
            String address = block.getMiner();
            Long blockNumber = block.getNumber();
            Long transactionCount = block.getTransactionCount();
            if (StringUtils.isBlank(address))
                continue;
            if (transactionCount > 0) {
                saveAddress(String.valueOf(blockNumber));
            }
            if (addressBalanceService.isExist(address))
                continue;
            doSave(date, address);
        }
        bucket.set(endBlock);
        return ReturnT.SUCCESS;
    }

    private void saveAddress(String blockNumber) {
        List<Transactions> transactions = mongoTemplate.find(Query.query(Criteria.where("blockNumber").is(blockNumber)), Transactions.class);
        if (CollectionUtils.isEmpty(transactions))
            return;
        Set<String> addresses = new HashSet<>();
        transactions.forEach(t -> {
            if (StringUtils.isNotBlank(t.getFrom()) && !addressBalanceService.isExist(t.getFrom()))
                addresses.add(t.getFrom());
            if (StringUtils.isNotBlank(t.getTo()) && !addressBalanceService.isExist(t.getTo()))
                addresses.add(t.getTo());
        });
        Date date = new Date();
        for (String address : addresses) {
            doSave(date, address);
        }
    }

    private void doSave(Date date, String address) {
        BigDecimal divide = null;
        int type = 0;
        long transactionCount = 0L;
        try {
            BigInteger balance = ScanServiceConfig.web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
            divide = new BigDecimal(balance).divide(ScanServiceConfig.RATE);
            String code = ScanServiceConfig.web3j.ethGetCode(address, DefaultBlockParameterName.LATEST).send().getCode();
            if (!"0x".equals(code))
                type = 1;
            if (scanServiceConfig.isSyncTransactionCount()) {
                Criteria criteria = new Criteria();
                criteria.orOperator(Criteria.where("from").is(address), Criteria.where("to").is(address));
                transactionCount = mongoTemplate.count(Query.query(criteria), Transactions.class);
            }
        } catch (MongoException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("获取PUL地址余额失败：", e);
            divide = BigDecimal.ZERO;
        }
        AddressBalance build = AddressBalance.builder()
                .address(address).balance(divide).show(1).createTime(date).updateTime(date).type(type).transactionCount(transactionCount)
                .build();
        if (addressBalanceService.isExist(address))
            return;
        addressBalanceService.insert(build);
    }


}
