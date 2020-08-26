package com.bosha.common.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value="星系配置")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StarConfig implements Serializable {
    @ApiModelProperty(value="id")
    private Long id;

    /**
    * 恒星
    */
    @ApiModelProperty(value="恒星")
    private Integer fixedStar;

    /**
    * 巨星
    */
    @ApiModelProperty(value="巨星")
    private Integer giantStar;

    /**
    * 超级巨星
    */
    @ApiModelProperty(value="超级巨星")
    private Integer superGiantStar;

    /**
    * 初始化值
    */
    @ApiModelProperty(value="初始化值")
    private BigDecimal initialValue;

    /**
    * 币种id
    */
    @ApiModelProperty(value="币种id")
    @JsonIgnore
    private Long coinId;

    private static final long serialVersionUID = 1L;
}