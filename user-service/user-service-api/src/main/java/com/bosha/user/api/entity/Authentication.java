package com.bosha.user.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "认证")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authentication implements Serializable {

    public static final int STATUS_TO_BE_TRANSFER = 1,
            STATUS_SELF_CONFIRMING = 2,
            STATUS_SELF_SUCCESS = 3,
            STATUS_AUXILIARY_AUTHENTICATION_ING = 4,
            STATUS_SUCCESS = 5;

    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 认证类型：3 个人，4 企业，5 政府
     */
    @ApiModelProperty(value = "认证类型：3 个人，4 企业，5 政府", required = true)
    @Max(value = 5, message = "最大值5")
    @Min(value = 3, message = "最小值3")
    private Integer type;

    @ApiModelProperty(value = "状态：1 已提交资料，待转账，2，自我认证转账确认中，3 自我认证成功，4 辅助认证进行中，5 已完成认证")
    private Integer status;
    /**
     * 身份签名
     */
    @ApiModelProperty(value = "身份签名")
    private String identitySignature;
    /**
     * 名字
     */
    @ApiModelProperty(value = "名字")
    private String idName;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idNo;

    /**
     * 证件照正面
     */
    @ApiModelProperty(value = "证件照正面")
    private String idImgFront;

    /**
     * 证件照背面
     */
    @ApiModelProperty(value = "证件照背面")
    private String idImgBack;

    /**
     * 护照
     */
    @ApiModelProperty(value = "护照")
    private String passport;

    /**
     * 组织结构名称
     */
    @ApiModelProperty(value = "组织结构名称")
    private String orgName;

    /**
     * 组织结构代码
     */
    @ApiModelProperty(value = "组织结构代码")
    private String orgCode;

    /**
     * 组织结构简介
     */
    @ApiModelProperty(value = "组织结构简介")
    private String orgBrief;

    /**
     * 营业执照
     */
    @ApiModelProperty(value = "营业执照")
    private String orgLicense;

    /**
     * 政府名称
     */
    @ApiModelProperty(value = "政府名称")
    private String govName;

    /**
     * 政府地区
     */
    @ApiModelProperty(value = "政府地区")
    private Integer govArea;
    /**
     * 政府地区名称
     */
    @ApiModelProperty(value = "政府地区名称")
    private String govAreaName;
    /**
     * 政府简介
     */
    @ApiModelProperty(value = "政府简介")
    private String govBrief;

    /**
     * 政府证件
     */
    @ApiModelProperty(value = "政府证件")
    private String govLicense;

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