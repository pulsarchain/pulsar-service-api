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

@ApiModel(value = "Dapp")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dapp {
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 合约地址
     */
    @ApiModelProperty(value = "合约地址", required = true)
    @NotBlank(message = "合约地址不可为空")
    private String contractAddress;

    /**
     * dapp名称
     */
    @ApiModelProperty(value = "dapp名称", required = true)
    @NotBlank(message = "dapp名称不可为空")
    private String name;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介", required = true)
    @NotBlank(message = "简介不可为空")
    private String intro;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id", required = true)
    @NotNull(message = "分类id不可为空")
    private Long categoryId;

    /**
     * logo
     */
    @ApiModelProperty(value = "logo",required = true)
    @NotBlank(message = "logo不可为空")
    private String logo;

    /**
     * 网址
     */
    @ApiModelProperty(value = "网址",required = true)
    @NotBlank(message ="网址不可为空")
    private String website;

    /**
     * 状态：1 未邀请见证，2 发布见证中，3 发布成功，4 已撤销，5 已隐藏
     */
    @ApiModelProperty(value = "状态：1 未邀请见证，2 发布见证中，3 发布成功，4 已撤销，5 已隐藏，6 已下架")
    private Integer status;

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