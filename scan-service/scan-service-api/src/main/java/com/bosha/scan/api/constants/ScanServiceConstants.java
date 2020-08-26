package com.bosha.scan.api.constants;

/**
 * 每个微服务api接口都应该写一个这样的常量类
 */
public interface ScanServiceConstants {

    /**
     * @Description 服务内部之间访问的 @FeignClient的前缀
     */
    String SERVER_PRIFEX = "/server/scan";

    String WEB_PRIFEX = "/api/scan";

    String SERVER_NAME = "scan-service";

}
