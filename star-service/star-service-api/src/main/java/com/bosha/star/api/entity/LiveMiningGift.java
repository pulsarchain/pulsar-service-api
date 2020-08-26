package com.bosha.star.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "直播挖矿礼物")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningGift implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    private static final long serialVersionUID = 1L;
}