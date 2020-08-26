package com.bosha.dapp.server.config;


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
@ConfigurationProperties(prefix = "dapp-service.config", ignoreInvalidFields = true)
public class DappServiceConfig {

    public static Web3j web3j;

    //--------------------------------------------------------//

    private TaoBaoConfig taoBaoConfig = new TaoBaoConfig();

    private PddConfig pddConfig = new PddConfig();

    private String node = "http://159.138.146.38:8545/";

    private String systemAddress = "0xc31197eddfa02605d6ad5c0e9231afe33b62e644";

    @PostConstruct
    public void init() {
        web3j = Web3j.build(new HttpService(node));
        log.info("web3j 初始化完成！");
    }
}
