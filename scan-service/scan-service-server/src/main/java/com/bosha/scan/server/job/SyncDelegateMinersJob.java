package com.bosha.scan.server.job;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bosha.commons.utils.HttpUtils;
import com.bosha.scan.server.config.ScanServiceConfig;
import com.bosha.scan.server.redis.CacheKey;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.utils.Numeric;


@Component
@Slf4j
public class SyncDelegateMinersJob {

    @Autowired
    private RedissonClient redis;
    @Autowired
    private ScanServiceConfig scanServiceConfig;

    private static final Map<String, Object> BODY = new HashMap<>();

    static {
        BODY.put("method", "eth_getAllDelegateMiners");
        BODY.put("jsonrpc", "2.0");
        BODY.put("id", 0);
    }

    @XxlJob("syncDelegateMinersJob")//每15s运行一次
    public ReturnT<String> syncDelegateMinersJob(String s) throws Exception {
        String hexStringWithPrefix = Numeric.toHexStringWithPrefix(ScanServiceConfig.web3j.ethBlockNumber().send().getBlockNumber());
        BODY.put("params", Collections.singletonList(hexStringWithPrefix));
        String result = HttpUtils.post(scanServiceConfig.getNode(), JSON.toJSONString(BODY));
        JSONObject jsonObject = JSON.parseObject(result).getJSONObject("result");
        Set<String> keySet = jsonObject.keySet();
        RMap<String, BigDecimal> map = redis.getMap(CacheKey.Scan.ALL_DELEGATE_MINERS.getKey());
        if (map.isExists()) {
            Set<String> keys = map.keySet();
            for (String key : keys) {
                if (!keySet.contains(key))
                    map.remove(key);
            }
        }
        for (String key : keySet) {
            JSONObject jso = jsonObject.getJSONObject(key);
            BigDecimal depositBalance = jso.getBigDecimal("DepositBalance");
            map.put(key, depositBalance.divide(ScanServiceConfig.RATE));
        }
        return ReturnT.SUCCESS;
    }

}
