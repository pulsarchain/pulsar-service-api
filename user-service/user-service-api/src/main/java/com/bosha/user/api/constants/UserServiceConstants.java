package com.bosha.user.api.constants;

/**
 * 每个微服务api接口都应该写一个这样的常量类
 */
public interface UserServiceConstants {

    /**
     * @Description 服务内部之间访问的 @FeignClient的前缀
     */
    String SERVER_PRIFEX = "/server/user";

    String WEB_PRIFEX = "/api/user";

    String SERVER_NAME = "user-service";

    String IM_RED_TOPIC ="im_red_topic";

}
