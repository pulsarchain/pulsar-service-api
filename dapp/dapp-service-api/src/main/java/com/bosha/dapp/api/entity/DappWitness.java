package com.bosha.dapp.api.entity;

import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="dapp见证记录")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DappWitness {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 关联的dapp_id
    */
    @ApiModelProperty(value="关联的dapp_id")
    private Long dappId;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
    * 辅助认证地址
    */
    @ApiModelProperty(value="辅助认证地址")
    private String auxiliaryAddress;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private BigDecimal money;

    /**
    * 交易hash
    */
    @ApiModelProperty(value="交易hash")
    private String hash;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 到账时间
    */
    @ApiModelProperty(value="到账时间")
    private Date updateTime;
}