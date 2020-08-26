package com.bosha.common.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="邀请好友配置")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteFriendConfig implements Serializable {
    @ApiModelProperty(value="id")
    private Long id;

    /**
    * 每邀请一个地址奖励
    */
    @ApiModelProperty(value="每邀请一个地址奖励")
    private BigDecimal reward;

    /**
    * 每日最高奖励
    */
    @ApiModelProperty(value="每日最高奖励")
    private BigDecimal rewardPerDay;

    /**
    * 每月最高奖励
    */
    @ApiModelProperty(value="每月最高奖励")
    private BigDecimal rewardPerMonth;

    /**
    * 币种id
    */
    @ApiModelProperty(value="币种id")
    private Long coinId;

    private static final long serialVersionUID = 1L;
}