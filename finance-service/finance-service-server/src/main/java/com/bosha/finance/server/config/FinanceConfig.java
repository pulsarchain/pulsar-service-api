package com.bosha.finance.server.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @Author liqingping
 * @Date 2019-12-11 15:57
 */
@Component
@ConfigurationProperties(prefix = "finance", ignoreInvalidFields = true)
@RefreshScope
@Slf4j
@Data
public class FinanceConfig {
    private Sms sms;

    @Data
    public static class Sms {
        private List<String> phone;
        private String message;
    }

}
