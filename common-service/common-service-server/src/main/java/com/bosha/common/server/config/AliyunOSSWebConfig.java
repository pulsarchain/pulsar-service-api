package com.bosha.common.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置类
 *
 * @author liyi
 * @create 2018-01-18 13:39
 **/
@Component
@ConfigurationProperties(prefix = "oss", ignoreInvalidFields = true)
@RefreshScope
public class AliyunOSSWebConfig {

    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
    private String baseUrl;
    private String endpoint;
    private String callbackUrl;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Bean
    public AliyunOSS aliyunOSS(){
        return AliyunOSS.options()
                .setId(accessKeyId)
                .setSecret(accessKeySecret)
                .setBucket(bucket)
                .setBaseUrl(baseUrl)
                .setEndpoint(endpoint)
                .build();
    }
}
