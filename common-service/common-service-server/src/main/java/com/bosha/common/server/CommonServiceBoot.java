package com.bosha.common.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableFeignClients(basePackages = {"com.bosha.user.api.service"})
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.bosha.common.server.mapper")
@EnableScheduling
public class CommonServiceBoot {

    public static void main(String[] args) {
        SpringApplication.run(CommonServiceBoot.class, args);
    }

}
