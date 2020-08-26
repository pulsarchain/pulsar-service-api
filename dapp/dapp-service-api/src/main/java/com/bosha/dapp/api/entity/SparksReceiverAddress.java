package com.bosha.dapp.api.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "用户收货地址")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksReceiverAddress {
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字", required = true)
    @NotBlank(message = "名字不可为空")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不可为空")
    private String phone;

    /**
     * 省市区id（区的id）
     */
    @ApiModelProperty(value = "省市区id（区的id）", required = true)
    @NotNull(message = "省市区id不可为空")
    private Integer areaId;

    /**
     * 省市区的名称
     */
    @ApiModelProperty(value = "省市区的名称，例如：四川省成都市武侯区", required = true)
    @NotBlank(message = "省市区名称不可为空")
    private String areaName;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址", required = true)
    @NotBlank(message = "详细不可为空")
    private String detail;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}