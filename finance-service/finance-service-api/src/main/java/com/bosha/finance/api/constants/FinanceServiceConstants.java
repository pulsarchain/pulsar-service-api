package com.bosha.finance.api.constants;

/**
 * 每个微服务api接口都应该写一个这样的常量类
 */
public interface FinanceServiceConstants {

    /**
     * @Description 服务内部之间访问的 @FeignClient的前缀
     */
    String SERVER_PRIFEX = "/server/finance";

    String WEB_PRIFEX = "/api/finance";

    String SERVER_NAME = "finance-service";

}
