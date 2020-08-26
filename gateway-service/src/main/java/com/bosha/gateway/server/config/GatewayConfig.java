package com.bosha.gateway.server.config;

import java.util.Map;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: GatewayConfig
 * @Author liqingping
 * @Date 2019-04-03 13:42
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "gateway.config", ignoreInvalidFields = true)
public class GatewayConfig {

    private Sign sign = new Sign();

    private Map<String, String> noAuthUrlMap;

    private Manager manager;

    @Data
    public static class Manager {
        private Boolean apiAuth;
    }

    @Data
    public static class Sign {

        private String secretKey = "";

        private boolean verifySignature = false;

        private long expireMilliseconds = 10 * 1000;
    }
}
