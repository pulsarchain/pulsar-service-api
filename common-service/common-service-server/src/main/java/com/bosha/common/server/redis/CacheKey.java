package com.bosha.common.server.redis;

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
    public enum Common {

        PUSH_CONFIG_MAP("push_config_map", 7, TimeUnit.DAYS),
        STAR_CONFIG_BUCKET("star_config_bucket", -1, null),
        CODE_BUCKET("code_bucket:", 3, TimeUnit.MINUTES),
        ;
        public String key;//key
        public long expireTime;//过期时间
        public TimeUnit timeUnit;//过期时间单位

        Common(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "CommonService:Common:" + key;
            //当前服务名+模块名+key 中间使用英文 : 间隔，
            // 加服务名和模块名是因为 在redis中查看数据时 一眼便能根据key知道是哪个服务哪个业务模块相关的，方便定位问题
            // 加英文冒号是因为 使用RedisDesktopManager查看redis中的数据时 会以树形结构显示 非常方便查找数据
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }

    @Getter
    public enum Market {
        COIN_RATE_MAP("coin_rate_map", -1, null),
        COIN_PRICE_LIST("coin_price_list", 5, TimeUnit.SECONDS),
        COIN_PRICE_MAP("coin_price_map", -1, null),

        ;
        public String key;//key
        public long expireTime;//过期时间
        public TimeUnit timeUnit;//过期时间单位

        Market(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "CommonService:Market:" + key;
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }


}
