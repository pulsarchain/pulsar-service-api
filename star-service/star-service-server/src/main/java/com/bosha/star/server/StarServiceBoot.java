package com.bosha.star.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(basePackages =
        {"com.bosha.common.api.service",
                "com.bosha.finance.api.service",
                "com.bosha.contract.api.service",
                "com.bosha.user.api.service"})
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.bosha.star.server.mapper")
public class StarServiceBoot {

    public static void main(String[] args) {
        SpringApplication.run(StarServiceBoot.class, args);
    }

}
