package com.bosha.finance.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="地址池")
@Data
public class AddressPool {
    @ApiModelProperty(value="null")
    private Long id;

    /**
    * 地址
    */
    @ApiModelProperty(value="地址")
    private String address;

    /**
    * 币种id
    */
    @ApiModelProperty(value="币种id")
    private Long coin;

    /**
    * 状态：0 未使用，1 已使用
    */
    @ApiModelProperty(value="状态：0 未使用，1 已使用")
    private Integer status;
}