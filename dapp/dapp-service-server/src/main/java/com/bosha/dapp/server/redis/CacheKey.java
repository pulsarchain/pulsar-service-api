package com.bosha.dapp.server.redis;

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
    public enum Dapp {
        PDD_URL("pdd_url:", 90, TimeUnit.DAYS),

        ;
        public String key;//key
        public long expireTime;//过期时间
        public TimeUnit timeUnit;//过期时间单位

        Dapp(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "DappService:" + key;
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }

}
