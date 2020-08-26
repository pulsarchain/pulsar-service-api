package com.bosha.star.api.constants;

/**
 * 每个微服务api接口都应该写一个这样的常量类
 */
public interface StarServiceConstants {

    /**
     * @Description 服务内部之间访问的 @FeignClient的前缀
     */
    String SERVER_PRIFEX = "/server/star";

    String WEB_PRIFEX = "/api/star";

    String SERVER_NAME = "star-service";

     String SECOND_FORMAT = "yyyy-MM-dd HH:mm:00";

}
