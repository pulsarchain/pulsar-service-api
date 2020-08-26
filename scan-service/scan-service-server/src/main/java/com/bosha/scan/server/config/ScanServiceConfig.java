package com.bosha.scan.server.config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: GatewayConfig
 * @Author liqingping
 * @Date 2019-04-03 13:42
 */
@Slf4j
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "scan-service.config", ignoreInvalidFields = true)
public class ScanServiceConfig {

    public static Web3j web3j;

    public static final BigDecimal RATE = BigDecimal.valueOf(Math.pow(10, 18));

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,##0.0000");

    //----------------------------------------------------------------------------------------------

    private String node;

    private boolean syncTransactionCount = false;

    @PostConstruct
    public void init() {
        web3j = Web3j.build(new HttpService(node));
        log.info("web3j 初始化完成！");
    }

}
