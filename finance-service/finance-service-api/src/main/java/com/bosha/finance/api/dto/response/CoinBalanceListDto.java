package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value="链上余额")
@Data
public class CoinBalanceListDto {
    private Long id;

    /**
    * 币种id
    */
    @ApiModelProperty(value="币种名称")
    private Long coinName;

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