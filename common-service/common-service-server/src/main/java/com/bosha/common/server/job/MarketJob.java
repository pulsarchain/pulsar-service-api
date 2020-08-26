package com.bosha.common.server.job;

import java.util.List;


import com.bosha.common.api.dto.market.CoinPrice;
import com.bosha.common.server.redis.CacheKey;
import com.bosha.common.server.service.impl.MarketServiceImpl;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MarketJob
 * @Author liqingping
 * @Date 2019-12-18 14:05
 */
@Slf4j
@Component
public class MarketJob {

    @Autowired
    private RedissonClient redis;
    @Autowired
    private MarketServiceImpl marketService;

    @XxlJob("MarketJob")//每5秒一次
    public ReturnT<String> execute(String s) throws Exception {
        try {
            long start = System.currentTimeMillis();
            List<CoinPrice> coinPriceList = marketService.getCoinPrices();
            if (CollectionUtils.isEmpty(coinPriceList))
                return ReturnT.SUCCESS;
            RList<CoinPrice> list = redis.getList(CacheKey.Market.COIN_PRICE_LIST.key);
            list.delete();
            list.addAll(coinPriceList);
            XxlJobLogger.log("success - cost[{}]ms", System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
            return   ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }
}
