package com.bosha.common.api.entity;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "推送配置")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushConfig implements Serializable {

    @ApiModelProperty(value = "id", hidden = true)
    @JsonIgnore
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    @JsonIgnore
    private Long userId;

    /**
     * 用户地址
     */
    @ApiModelProperty(value="用户地址")
    private String address;
    /**
     * 总开关
     */
    @ApiModelProperty(value = "总开关")
    private Boolean all;

    /**
     * 通知类型 0 关闭，1 打开
     */
    @ApiModelProperty(value = "通知类型 0 关闭（小红点），1 打开（数字）")
    private Boolean notice;

    /**
     * 系统
     */
    @ApiModelProperty(value = "系统")
    private Boolean system;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Boolean price;

    /**
     * 公链
     */
    @ApiModelProperty(value = "公链")
    private Boolean chain;

    /**
     * 星系
     */
    @ApiModelProperty(value = "星系")
    private Boolean star;

    /**
     * dapp
     */
    @ApiModelProperty(value = "dapp")
    private Boolean dapp;

    private static final long serialVersionUID = 1L;
}