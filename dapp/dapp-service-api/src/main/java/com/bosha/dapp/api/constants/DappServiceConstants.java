package com.bosha.dapp.api.constants;

/**
 * 每个微服务api接口都应该写一个这样的常量类
 */
public interface DappServiceConstants {

    /**
     * @Description 服务内部之间访问的 @FeignClient的前缀
     */
    String SERVER_PRIFEX = "/server/dapp";

    String WEB_PRIFEX = "/api/dapp";

    String SERVER_NAME = "dapp-service";

}
