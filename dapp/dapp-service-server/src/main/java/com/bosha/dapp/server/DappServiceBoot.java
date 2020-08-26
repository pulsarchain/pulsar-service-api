package com.bosha.dapp.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.bosha.user.api.service", "com.bosha.common.api.service"})
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.bosha.dapp.server.mapper")
public class DappServiceBoot {

    public static void main(String[] args) {
        SpringApplication.run(DappServiceBoot.class, args);
    }

}
