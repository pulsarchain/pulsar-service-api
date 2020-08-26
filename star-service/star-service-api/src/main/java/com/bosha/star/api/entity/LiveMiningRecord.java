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

@ApiModel(value = "挖矿记录")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningRecord implements Serializable {

    public static final int TYPE_VIEW = 1, TYPE_COMMENT = 2, TYPE_LIKE = 3, TYPE_SHARE = 4, TYPE_GIFT = 5;

    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 直播挖矿id
     */
    @ApiModelProperty(value = "直播挖矿id")
    private Long liveMiningId;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    /**
     * 星系id
     */
    @ApiModelProperty(value = "星系id")
    private Long starId;

    /**
     * 状态：0 未结算，1 已结算
     */
    @ApiModelProperty(value = "状态：0 未结算，1 已结算")
    private Integer status;

    /**
     * 类型：1 观看，2 评论，3 点赞，4 分享，5 礼物
     */
    @ApiModelProperty(value = "类型：1 观看，2 评论，3 点赞，4 分享，5 礼物")
    private Integer type;

    /**
     * 昵称+星系等级
     */
    @ApiModelProperty(value="昵称+星系等级")
    private String remark;

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