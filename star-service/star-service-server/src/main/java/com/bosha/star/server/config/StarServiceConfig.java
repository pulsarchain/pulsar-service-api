package com.bosha.star.server.config;

import com.bosha.star.api.dto.web.ExtraInfoDto;
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
@ConfigurationProperties(prefix = "star-service.config", ignoreInvalidFields = true)
public class StarServiceConfig {

    private String charityAddress;

    private LiveConfig liveConfig;

    private ExtraInfoDto.Num num = new ExtraInfoDto.Num();

}
