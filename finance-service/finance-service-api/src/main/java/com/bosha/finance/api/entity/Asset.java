package com.bosha.finance.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value="用户资产")
@Data
public class Asset {
    @ApiModelProperty(value="null")
    private Long id;

    /**
    * 用户id
    */
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
    * 地址
    */
    @ApiModelProperty(value="地址")
    private String address;

    /**
    * 可用余额
    */
    @ApiModelProperty(value="可用余额")
    private BigDecimal balance;

    /**
    * 冻结金额
    */
    @ApiModelProperty(value="冻结金额")
    private BigDecimal frozenBalance;

    /**
    * 理财冻结金额
    */
    @ApiModelProperty(value="理财冻结金额")
    private BigDecimal financeFrozenBalance;

    /**
    * 理财金额
    */
    @ApiModelProperty(value="理财金额")
    private BigDecimal financeBalance;

    /**
    * 币种id
    */
    @ApiModelProperty(value="币种id")
    private Long coinId;

    /**
    * 预留字段
    */
    @ApiModelProperty(value="预留字段")
    private String other;
}