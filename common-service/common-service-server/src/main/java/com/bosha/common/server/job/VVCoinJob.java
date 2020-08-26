package com.bosha.common.server.job;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.alibaba.fastjson.JSONObject;
import com.bosha.common.api.dto.market.CoinPrice;
import com.bosha.common.api.service.MarketService;
import com.bosha.common.server.redis.CacheKey;
import com.bosha.commons.utils.HttpUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: 获取汇率
 * @Author liqingping
 * @Date 2019-12-16 11:16
 */

@Component
@Slf4j
public class VVCoinJob {

    private static final String URL = "https://www.vvcoin.com/api/spot/v3/instruments/ticker";

    public static final String USDT = "USDT";//TODO 后期增加多个交易区时再优化

    @Autowired
    private RedissonClient redis;
    @Autowired
    private MarketService marketService;

    @XxlJob("VVCoinJob")//每5秒一次
    public ReturnT<String> execute(String s) throws Exception {
        try {
            long start = System.currentTimeMillis();
            String result = HttpUtils.get(URL, String.class);
            List<CoinPrice> tickerList = JSONObject.parseArray(result, CoinPrice.class)
                    .stream()
                    .filter(vvCoinTicker -> vvCoinTicker.getTradeType().equalsIgnoreCase(USDT))
                    .collect(Collectors.toList());
            RMap<String, CoinPrice> map = redis.getMap(CacheKey.Market.COIN_PRICE_MAP.key);
            Map<String, BigDecimal> legalTender = marketService.getLegalTender(USDT);
            tickerList.forEach(cp -> {
                cp.setCny(legalTender.getOrDefault("CNY", BigDecimal.ZERO).multiply(cp.getLast()).setScale(2, BigDecimal.ROUND_HALF_UP));
                cp.setUsd(legalTender.getOrDefault("USD", BigDecimal.ZERO).multiply(cp.getLast()).setScale(2, BigDecimal.ROUND_HALF_UP));
                map.put(cp.getCoin().toUpperCase(), cp);
            });
            XxlJobLogger.log("success - cost[{}]ms", System.currentTimeMillis() - start);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            return ReturnT.FAIL;
        }
    }
}
