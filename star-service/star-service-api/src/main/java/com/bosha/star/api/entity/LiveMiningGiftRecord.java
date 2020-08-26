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

@ApiModel(value = "礼物购买消费记录")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningGiftRecord implements Serializable {
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 系统地址
     */
    @ApiModelProperty(value = "系统地址")
    private String systemAddress;

    /**
     * to
     */
    @ApiModelProperty(value = "to")
    private String toAddress;

    /**
     * 关联的直播挖矿id
     */
    @ApiModelProperty(value = "关联的直播挖矿id")
    private Long relatedId;

    /**
     * 礼物id
     */
    @ApiModelProperty(value = "礼物id")
    private Long giftId;

    /**
     * 个数
     */
    @ApiModelProperty(value = "个数")
    private Integer num;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    /**
     * 1 购买，2 消费
     */
    @ApiModelProperty(value = "1 购买，2 消费 ")
    private Integer type;

    /**
     * 状态：0 确认中，1 成功
     */
    @ApiModelProperty(value = "状态：0 确认中，1 成功")
    private Integer status;

    /**
     * hash
     */
    @ApiModelProperty(value = "hash")
    private String hash;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}