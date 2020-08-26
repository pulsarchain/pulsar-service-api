package com.bosha.common.server.config;

import java.util.Arrays;
import java.util.List;


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
@ConfigurationProperties(prefix = "common-service.config", ignoreInvalidFields = true)
public class CommonServiceConfig {

    private Sms sms;

    private List<String> instructions = Arrays.asList("miningRule", "minerFee");

    private int pushBatchSize = 100;

    @Data
    public static final class Sms {

        private String url;

        private String account;

        private String password;

        private Boolean noVerify = false;

    }
}
