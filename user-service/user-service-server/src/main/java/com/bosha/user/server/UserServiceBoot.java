package com.bosha.user.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(basePackages =
        {"com.bosha.common.api.service",
        "com.bosha.finance.api.service","com.bosha.contract.api.service","com.bosha.star.api.service"})
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.bosha.user.server.mapper")
public class UserServiceBoot {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceBoot.class, args);
    }

}
