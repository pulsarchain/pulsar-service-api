package com.bosha.finance.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("提币")
public class WithDrawCoinDto implements Serializable {
    @ApiModelProperty("币种id")
    private Long coinId;
    @ApiModelProperty("余额")
    private BigDecimal balance;
    @ApiModelProperty("google验证码")
    private String googleCode;
    @ApiModelProperty("到账地址")
    private String toAddress;
    @ApiModelProperty("备注")
    private String remark;
    private Long userId;

}
