package com.bosha.common.api.constants;

/**
 * 每个微服务api接口都应该写一个这样的常量类
 */
public interface CommonServiceConstants {

    /**
     * @Description 服务内部之间访问的 @FeignClient的前缀
     */
    String SERVER_PRIFEX = "/server/common";

    String WEB_PRIFEX = "/api/common";

    String SERVER_NAME = "common-service";

    String ORIGIN_PULSAR_DIR = "pulsar/";
    String ENCODED_POLICY = "encodedPolicy_";



    String SEPARATOR = "/";

}
