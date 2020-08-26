package com.bosha.scan.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.bosha.scan.server.mapper")
public class ScanServiceBoot {

    public static void main(String[] args) {
        SpringApplication.run(ScanServiceBoot.class, args);
    }

}
