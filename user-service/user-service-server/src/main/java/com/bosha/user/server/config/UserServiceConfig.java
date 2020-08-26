package com.bosha.user.server.config;

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
@ConfigurationProperties(prefix = "user-service.config", ignoreInvalidFields = true)
public class UserServiceConfig {

    private String inviteFriendContractAddress;

    private ExploerApi exploerApi;

    private CreditScore creditScore = new CreditScore();
    private Im im;

    private Admin admin = new Admin();

    private Authentication authentication = new Authentication();

    @Data
    public static final class Admin {
        private String account = "pulsar";
        private String password = "$2a$10$W4reoghhY1boz03wg.TYr.v6t3wDrJquGYV9ozMDManuO1pP3qp5q";
    }

    @Data
    public static final class Authentication {
        private String systemAddress;
    }

    @Data
    public static final class ExploerApi {

        private String creditScoreData;

    }

    @Data
    public static final class CreditScore {

        private int calcDays = 100;

    }

    @Data
    public static final class Im {

        private String url;

    }

}
