package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("收益汇总")
public class MiningDetailStatisticsDto {
    @ApiModelProperty("最新收益")
    private BigDecimal todayMoney;
    @ApiModelProperty("累计收益")
    private BigDecimal totalMoney;

}
