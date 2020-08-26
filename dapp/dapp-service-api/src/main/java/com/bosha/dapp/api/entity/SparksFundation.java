package com.bosha.dapp.api.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "基金")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksFundation {

    public static final int STATUS_UN_INVITE = 1, STATUS_WITNESSING = 2, STATUS_SUCCESS = 3, STATUS_OFF_LINE = 4;

    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 状态：1 未邀请见证，2 邀请见证中，3 成功，4 已下架
     */
    @ApiModelProperty(value = "状态：1 未邀请见证，2 邀请见证中，3 成功，4 已下架")
    private Integer status;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String contact;

    /**
     * logo
     */
    @ApiModelProperty(value = "logo", required = true)
    @NotBlank(message = "logo不可为空")
    private String logo;

    /**
     * 投入
     */
    @ApiModelProperty(value = "投入", required = true)
    @NotNull(message = "投入不可为空")
    private BigDecimal invest;

    /**
     * 时间
     */
    @ApiModelProperty(value = "时间", required = true)
    @NotNull(message = "时间不可为空")
    private Integer year;

    /**
     * 年返
     */
    @ApiModelProperty(value = "年返", required = true)
    @NotNull(message = "年返不可为空")
    private BigDecimal rebate;

    /**
     * 购买建议
     */
    @ApiModelProperty(value = "购买建议")
    private String advice;

    /**
     * 信用分
     */
    @ApiModelProperty(value="信用分")
    private Integer creditScoreMin;

    /**
     * 信用分
     */
    @ApiModelProperty(value="信用分")
    private Integer creditScoreMax;

    /**
     * hash
     */
    @ApiModelProperty(value="hash")
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

    @ApiModelProperty(value = "资质文件", required = true)
    @NotEmpty(message = "资质文件不可为空")
    private List<SparksFundationExtra> certificates;
    @ApiModelProperty(value = "合同", required = true)
    @NotEmpty(message = "合同不可为空")
    private List<SparksFundationExtra> contracts;
}