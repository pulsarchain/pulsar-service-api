package com.bosha.star.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "发放奖励")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarReward implements Serializable {
    public static final int ADDRESS_TYPE_USER = 1, ADDRESS_TYPE_CHARITY = 2, ADDRESS_TYPE_SYSTEM = 3;
    public static final int STATUS_CONFIRMING = 0, STATUS_SUCCESS = 1;
    @ApiModelProperty(value = "")
    private Long id;


    /**
     * 星系成员id（触发奖励的成员id）
     */
    @ApiModelProperty(value = "星系成员id（触发奖励的成员id）")
    private Long starMemberId;

    /**
     * 合约地址
     */
    @ApiModelProperty(value = "合约地址")
    private String contractAddress;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 地址类型：1 普通用户地址，2 慈善地址，3 系统地址
     */
    @ApiModelProperty(value = "地址类型：1 普通用户地址，2 慈善地址，3 系统地址")
    private Integer addressType;

    /**
     * 用户当前等级
     */
    @ApiModelProperty(value = "用户当前等级")
    private Integer level;

    /**
     * 用户当前赫兹
     */
    @ApiModelProperty(value = "用户当前赫兹")
    private BigDecimal currentHz;

    /**
     * 增加的赫兹/发放的币
     */
    @ApiModelProperty(value = "增加的赫兹/发放的币")
    private BigDecimal hz;

    /**
     * 状态：0 待打币，1 已到账
     */
    @ApiModelProperty(value = "状态：0 待打币，1 已到账 ")
    private Integer status;

    /**
     * 打币hash
     */
    @ApiModelProperty(value = "打币hash")
    private String hash;

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