package com.bosha.star.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "星系")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Star implements Serializable {
    public static final int STATUS_CONFIRMING = 0, STATUS_SUCCESS = 1;
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 星系名称
     */
    @ApiModelProperty(value = "星系名称", required = true)
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 宣言
     */
    @ApiModelProperty(value = "宣言", required = true)
    @NotBlank(message = "宣言不可为空")
    private String slogan;

    /**
     * logo
     */
    @ApiModelProperty(value = "logo", required = true)
    @NotBlank(message = "logo不可为空")
    private String logo;

    /**
     * 初始化费用
     */
    @ApiModelProperty(value = "初始化费用")
    private BigDecimal createFee;

    /**
     * 总能量值
     */
    @ApiModelProperty(value = "总能量值")
    private BigDecimal totalHz;

    /**
     * 慈善地址
     */
    @ApiModelProperty(value = "慈善地址")
    private String charityAddress;

    /**
     * 合约地址
     */
    @ApiModelProperty(value = "合约地址")
    private String contractAddress;

    /**
     * 系统地址
     */
    @ApiModelProperty(value = "打币地址", required = true)
    @NotBlank(message = "打币地址不可为空")
    private String systemAddress;

    /**
     * 交易hash
     */
    @ApiModelProperty(value = "交易hash", required = true)
    @NotBlank(message = "交易hash不可为空")
    private String hash;

    /**
     * 状态：0 区块确认中，1 创建成功
     */
    @ApiModelProperty(value = "状态：0 区块确认中，1 创建成功")
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

    private static final long serialVersionUID = 1L;
}