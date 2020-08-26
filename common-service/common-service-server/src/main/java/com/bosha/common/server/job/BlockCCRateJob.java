package com.bosha.common.server.job;

import static com.bosha.common.server.job.VVCoinJob.USDT;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.bosha.common.api.dto.market.BlockCCResult;
import com.bosha.common.api.dto.market.CoinRate;
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
public class BlockCCRateJob {

    private static final String URL = "https://data.block.cc/api/v1/exchange_rate?api_key=%s&format=array&symbols=USD,CNY&base=USDT";

    @Autowired
    private RedissonClient redis;

    @XxlJob("BlockCCRateJob")//每5分钟一次
    public ReturnT<String> execute(String s) throws Exception {
        try {
            BlockCCResult blockCCResult = HttpUtils.get(String.format(URL, s), BlockCCResult.class);
            if (blockCCResult.isSuccess()) {
                BlockCCResult.ExchangeRate data = JSON.parseObject(JSON.toJSONString(blockCCResult.getData()), BlockCCResult.ExchangeRate.class);
                List<CoinRate> rateList = data.getRates();
                RMap<String, Map<String, BigDecimal>> map = redis.getMap(CacheKey.Market.COIN_RATE_MAP.key);
                Map<String, BigDecimal> coinMap = new HashMap<>();
                rateList.forEach(coinRate -> coinMap.put(coinRate.getCurrency().toUpperCase(), coinRate.getRate()));
                map.put(USDT, coinMap);
            } else {
                log.error("获取BlockCC汇率失败：{}", blockCCResult.getMessage());
                XxlJobLogger.log("获取BlockCC汇率失败：{}", blockCCResult.getMessage());
                return ReturnT.FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }


}
