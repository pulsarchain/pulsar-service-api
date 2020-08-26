package com.bosha.finance.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value="链上余额")
@Data
public class CoinBalance {
    @ApiModelProperty(value="null")
    private Long id;

    /**
    * 币种id
    */
    @ApiModelProperty(value="币种id")
    private Long coinId;

    /**
    * 链上余额
    */
    @ApiModelProperty(value="链上余额")
    private BigDecimal chainBalance;

    /**
    * 平台余额
    */
    @ApiModelProperty(value="平台余额")
    private BigDecimal platformBalance;
}