package com.bosha.common.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="直播挖矿配置")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningConfig implements Serializable {
    @ApiModelProperty(value="id")
    private Long id;

    /**
    * 最小值
    */
    @ApiModelProperty(value="最小值")
    private BigDecimal min;

    /**
    * 最大值
    */
    @ApiModelProperty(value="最大值")
    private BigDecimal max;

    /**
    * 币种id
    */
    @ApiModelProperty(value="币种id")
    @JsonIgnore
    private Long coinId;

    private static final long serialVersionUID = 1L;
}