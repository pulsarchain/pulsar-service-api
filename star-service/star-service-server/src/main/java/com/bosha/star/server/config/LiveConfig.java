package com.bosha.star.server.config;

import java.math.BigDecimal;


import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveConfig
 * @Author liqingping
 * @Date 2020-03-28 10:30
 */
@Data
public class LiveConfig {

    private String region = "ap-chengdu";

    private String videoSecretId;

    private String videoSecretKey;

    private Long sdkAppid;

    private String secretKey;

    private String pushDomain;

    private String pullDomain;

    private BigDecimal giftScale = BigDecimal.valueOf(0.08);

    private BigDecimal giftMin = BigDecimal.valueOf(0.01);

    private String giftAddress;

    private BigDecimal liveFeePerMin = BigDecimal.ZERO;

    private Integer push = 1;

    private Integer androidPush = 0;

    private String contractAddress = "0xd71b77410980E4c096fd7f1AFA27eafE2bCB9792";

    private String imDomain;
}
