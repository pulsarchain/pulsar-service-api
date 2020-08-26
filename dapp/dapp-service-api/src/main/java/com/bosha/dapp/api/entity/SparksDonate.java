package com.bosha.dapp.api.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "爱心捐赠")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksDonate {
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @JsonIgnore//TODO
    private Integer status;

    /**
     * 种类
     */
    @ApiModelProperty(value = "种类", required = true)
    @NotBlank(message = "种类不可为空")
    private String category;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不可为空")
    @Min(value = 1, message = "最小值为1")
    private Integer num;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Integer sku;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位", required = true)
    @NotBlank(message = "单位不可为空")
    private String unit;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = true)
    @NotBlank(message = "描述不可为空")
    private String desc;

    /**
     * 类型：1 点亮，2 擦亮，3 造星
     */
    @ApiModelProperty(value = "类型：1 点亮，2 擦亮，3 造星", required = true)
    @NotNull(message = "类型不可为空")
    private Integer type;

    /**
     * 成本
     */
    @ApiModelProperty(value = "成本", required = true)
    @NotNull(message = "成本不可为空")
    private BigDecimal cost;

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

    @ApiModelProperty(value = "图片列表", required = true)
    @NotEmpty(message = "图片列表不可为空")
    private List<String> imgs;
}