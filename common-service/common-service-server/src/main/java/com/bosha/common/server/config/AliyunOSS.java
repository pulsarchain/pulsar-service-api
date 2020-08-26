package com.bosha.common.server.config;

import com.aliyun.oss.OSSClient;

import java.io.InputStream;

/**
 * 阿里云OSS基础bean
 *
 * @author liyi
 * @create 2018-01-18 13:36
 **/
public class AliyunOSS {

    private String id;

    private String secret;

    private String bucket;

    private String baseUrl;

    private String endpoint;

    public static class Builder{
        private String id;

        private String secret;

        private String bucket;

        private String baseUrl;

        private String endpoint;

        public Builder setId(String id){
            this.id = id;
            return this;
        }

        public Builder setSecret(String secret){
            this.secret = secret;
            return this;
        }

        public Builder setBucket(String bucket){
            this.bucket = bucket;
            return this;
        }

        public Builder setBaseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setEndpoint(String endpoint){
            this.endpoint = endpoint;
            return this;
        }

        public AliyunOSS build(){
            return new AliyunOSS(this);
        }
    }

    private AliyunOSS(Builder builder){
        this.id = builder.id;
        this.baseUrl = builder.baseUrl;
        this.endpoint = builder.endpoint;
        this.bucket = builder.bucket;
        this.secret = builder.secret;
    }

    public static Builder options(){
        return new Builder();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getBucket() {
        return bucket;
    }

}
