package com.bosha.user.server.redis;

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
    public enum User {
        CREDIT_SCORE_MAP("credit_score_map", 1, TimeUnit.DAYS),
        BLOCK_NEW("block_new", -1, null),
        BLOCK_NEW_ARRIVAL("block_new_arrival", -1, null),
        USER("", 7, TimeUnit.DAYS),

        ;
        private String key;//key
        private long expireTime;//过期时间
        private TimeUnit timeUnit;//过期时间单位

        User(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "UserService:User:" + key;
            //当前服务名+模块名+key 中间使用英文 : 间隔，
            // 加服务名和模块名是因为 在redis中查看数据时 一眼便能根据key知道是哪个服务哪个业务模块相关的，方便定位问题
            // 加英文冒号是因为 使用RedisDesktopManager查看redis中的数据时 会以树形结构显示 非常方便查找数据
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }


    public enum Admin {
        STATUS_DISABLED_MAP("status_disabled_map", -1, null),
        RESET_PASSWORD_MAP("reset_password_map", -1, null),
        PERMISSION_MAP("permission_map:", -1, null),
        LOGIN_MAP("login_map", 7, TimeUnit.DAYS),

        ;
        public String key;//key
        public long expireTime;//过期时间
        public TimeUnit timeUnit;//过期时间单位

        Admin(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "UserService:Admin:" + key;
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }


    @Getter
    public enum Friend {
        Friend("friend_", -1, null),

        ;
        private String key;//key
        private long expireTime;//过期时间
        private TimeUnit timeUnit;//过期时间单位

        Friend(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "UserService:Friend:" + key;
            //当前服务名+模块名+key 中间使用英文 : 间隔，
            // 加服务名和模块名是因为 在redis中查看数据时 一眼便能根据key知道是哪个服务哪个业务模块相关的，方便定位问题
            // 加英文冒号是因为 使用RedisDesktopManager查看redis中的数据时 会以树形结构显示 非常方便查找数据
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }
    @Getter
    public enum ImGroup {
        Group("group_", -1, null),
        Group_member("group_member_", -1, null),

        ;
        private String key;//key
        private long expireTime;//过期时间
        private TimeUnit timeUnit;//过期时间单位

        ImGroup(String key, long expireTime, TimeUnit timeUnit) {
            this.key = "UserService:Group:" + key;
            //当前服务名+模块名+key 中间使用英文 : 间隔，
            // 加服务名和模块名是因为 在redis中查看数据时 一眼便能根据key知道是哪个服务哪个业务模块相关的，方便定位问题
            // 加英文冒号是因为 使用RedisDesktopManager查看redis中的数据时 会以树形结构显示 非常方便查找数据
            this.expireTime = expireTime;
            this.timeUnit = timeUnit;
        }
    }


}
