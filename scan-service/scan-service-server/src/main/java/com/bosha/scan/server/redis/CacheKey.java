package com.bosha.scan.server.redis;

import java.util.concurrent.TimeUnit;


import lombok.Data;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA 17.0.1
 *
 * @DESCRIPTION: 统一管理当前服务用到的所有Redis的key
 * @Author liqingping
 * @Date 2018-11-18 16:01
 */
@Data
public class CacheKey {

    @Getter
    public enum Scan {
        BLOCK_NUM("block_num", -1, null),
        SYNC_BALANCE("sync_balance:", -1, null),
        PERCENT("percent", -1, null),
        DEAL_BLOCK_NUMBER("deal_block_number:", 2, TimeUnit.MINUTES),
        ALL_DELEGATE_MINERS("AllDelegateMiners", -1, null),
        MONITOR_TIMESTAMP("monitor_timestamp", -1, null),
        MONITOR_TRANSACTION("monitor_transaction", -1, null),
        WEIXIN_PUSH_KEY("weixin_push_key:", 1, TimeUnit.MINUTES),
        PUSH_HASH("push_hash:", 1, TimeUnit.MINUTES),
        ADDRESS_INFO("address_info:", 10, TimeUnit.MINUTES),

        ;
        public String key;//key
        public long expireTime;//过期时间
        public TimeUnit timeUnit;//过期时间单位

        Scan(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "ScanService:" + key;
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }

}
