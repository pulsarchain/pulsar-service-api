package com.bosha.gateway.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author liqingping
 * @Date 2019-4-6 006 14:15
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class GatewayServiceBoot {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceBoot.class, args);
    }


    @RequestMapping(value = "/check")
    public String check() {
        return "ok";
    }

    @GetMapping("/")
    public String welcome() {
        return "welcome to pulsar ";
    }

}
