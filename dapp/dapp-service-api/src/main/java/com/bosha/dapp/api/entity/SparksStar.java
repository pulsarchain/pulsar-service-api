package com.bosha.dapp.api.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@ApiModel(value = "星星之火")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksStar {
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
     * 类型：1 点亮星星，2 擦亮星星，3 造星计划
     */
    @ApiModelProperty(value = "类型：1 点亮星星，2 擦亮星星，3 造星计划", required = true)
    @NotNull(message = "类型不可为空")
    private Integer type;

    /**
     * 状态：1 未邀请见证，2 见证中，3 见证成功（筹款中），4 筹款结束，5 已下架
     */
    @ApiModelProperty(value = "状态：1 未邀请见证，2 见证中，3 见证成功（筹款中），4 筹款结束，5 已下架")
    private Integer status;

    /**
     * 总共需筹集金额
     */
    @ApiModelProperty(value = "总共需筹集金额（目标点亮）", required = true)
    @NotNull(message = "总金额不可为空")
    private BigDecimal total;

    /**
     * 每月
     */
    @ApiModelProperty(value = "每月")
    private BigDecimal perMonth;

    /**
     * 年
     */
    @ApiModelProperty(value = "年")
    private Integer year;

    /**
     * 故事
     */
    @ApiModelProperty(value = "故事", required = true)
    @NotBlank(message = "故事不可为空")
    @Length(max = 1024, message = "最多1024个字")
    private String story;

    /**
     * 心愿
     */
    @ApiModelProperty(value = "心愿", required = true)
    @NotBlank(message = "心愿不可为空")
    @Length(max = 100, message = "最多100个字")
    private String wish;

    /**
     * 视频链接
     */
    @ApiModelProperty(value = "视频链接")
    private String videoUrl;

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
    @Size(max = 9, min = 3, message = "size范围3-9")
    private List<String> imgs;
}