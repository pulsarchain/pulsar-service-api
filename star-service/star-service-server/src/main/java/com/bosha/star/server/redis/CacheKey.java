package com.bosha.star.server.redis;

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
    public enum Star {
        STAR_MEMBER("star_member:", 1, TimeUnit.DAYS),

        ;
        private String key;//key
        private long expireTime;//过期时间
        private TimeUnit timeUnit;//过期时间单位

        Star(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "StarService:Star:" + key;
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }

    @Getter
    public enum Live {
        USER_MINING("mining:", 59, TimeUnit.SECONDS),
        LIVE_MINING("live_mining:", 1, TimeUnit.DAYS),
        LIVE_PUSH_URL("live_push_url:", -1, TimeUnit.SECONDS),
        LIVE_MINING_SHARE_NUM("live_mining_share_num:", -1, null),
        LIVE_MINING_COMMENT_NUM("live_mining_comment_num:", -1, null),
        LIVE_MINING_VIEW_LIKE_GIFT_CASH_OUT("live_mining_view_like_gift_cash_out:", 3, TimeUnit.DAYS),
        LIVE_CHAT_ROOM_ONLINE_NUM("live_chat_room_online_num:", 60, TimeUnit.DAYS),
        LIVE_GIFT_SEND("live_gift_send",-1,null),

        ;
        private String key;//key
        private long expireTime;//过期时间
        private TimeUnit timeUnit;//过期时间单位

        Live(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "StarService:Live:" + key;
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }

}
