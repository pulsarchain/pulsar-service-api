package com.bosha.finance.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.bosha.finance.server.mapper")
@EnableFeignClients(basePackages = {"com.bosha.common.api","com.bosha.user.api"})
public class FinanceServiceBoot {

    public static void main(String[] args) {
        SpringApplication.run(FinanceServiceBoot.class, args);
    }

}
