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

@ApiModel(value = "星系成员")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarMember implements Serializable {
    public static final int ADDRESS_TYPE_USER = 1, ADDRESS_TYPE_CHARITY = 2, ADDRESS_TYPE_SYSTEM = 3;
    public static final int STATUS_CONFIRMING = 0, STATUS_SUCCESS = 1;
    @ApiModelProperty(value = "主键健id")
    private Long id;

    /**
     * 星系id
     */
    @ApiModelProperty(value = "星系id")
    private Long starId;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 推荐人地址
     */
    @ApiModelProperty(value = "推荐人地址")
    private String recommendAddress;

    /**
     * 初始化级别：1 脉冲星，2 脉冲双星，3 小绿人
     */
    @ApiModelProperty(value = "初始化级别：1 脉冲星，2 脉冲双星，3 小绿人")
    private Integer initLevel;

    /**
     * 现在的级别：1 脉冲星，2 脉冲双星，3 小绿人
     */
    @ApiModelProperty(value = "现在的级别：1 脉冲星，2 脉冲双星，3 小绿人")
    private Integer currentLevel;
    /**
     * 加入的初始化费用
     */
    @ApiModelProperty(value = "加入的初始化费用")
    private BigDecimal initFee;
    /**
     * 初始化赫兹
     */
    @ApiModelProperty(value = "初始化赫兹")
    private BigDecimal initHz;

    /**
     * 现在的赫兹
     */
    @ApiModelProperty(value = "现在的赫兹")
    private BigDecimal currentHz;

    /**
     * 状态：0 区块确认中，1 加入成功
     */
    @ApiModelProperty(value = "状态：0 区块确认中，1 加入成功")
    private Integer status;

    /**
     * 系统地址
     */
    @ApiModelProperty(value = "系统地址")
    private String systemAddress;

    /**
     * 交易hash
     */
    @ApiModelProperty(value = "交易hash")
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

    public String seatName() {
        switch (this.getCurrentLevel()) {
            case 1:
                return "脉冲星";
            case 2:
                return "脉冲双星";
            case 3:
                return "小绿人";
        }
        return "-";
    }

}