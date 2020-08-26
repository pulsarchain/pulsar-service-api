package com.bosha.dapp.api.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@ApiModel(value = "收款账户")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksReceiveAccount {
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 类型：1 微信，2 支付宝，3 银行卡
     */
    @ApiModelProperty(value = "类型：1 微信，2 支付宝，3 银行卡",required = true)
    @NotNull(message = "类型不可为空")
    @Range(max = 3, min = 1, message = "范围1-3")
    private Integer type;

    /**
     * 微信昵称
     */
    @ApiModelProperty(value = "微信昵称")
    private String wechatName;

    /**
     * 微信账号
     */
    @ApiModelProperty(value = "微信账号")
    private String wechatAccount;

    /**
     * 微信收款码
     */
    @ApiModelProperty(value = "微信收款码")
    private String wechatQrcode;

    /**
     * 支付宝昵称
     */
    @ApiModelProperty(value = "支付宝昵称")
    private String alipayName;

    /**
     * 支付宝账户
     */
    @ApiModelProperty(value = "支付宝账户")
    private String alipayAccount;

    /**
     * 支付宝收款码
     */
    @ApiModelProperty(value = "支付宝收款码")
    private String alipayQrcode;

    /**
     * 银行卡用户真实姓名
     */
    @ApiModelProperty(value = "银行卡用户真实姓名")
    private String bankUserName;

    /**
     * 银行卡卡号
     */
    @ApiModelProperty(value = "银行卡卡号")
    private Long bankNo;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;

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