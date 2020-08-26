package com.bosha.dapp.api.dto;

import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: SparksStarListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-25 19:57
 */
@Data
@ApiModel("列表")
public class SparksStarListDto {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("状态：1 未邀请见证，2 见证中，3 见证成功（筹款中），4 筹款结束，5 已下架")
    private Integer status;
    @ApiModelProperty("图片")
    private String img;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("类型：1 点亮，2 擦亮，3 造星")
    private Integer type;
    @ApiModelProperty("总共需要")
    private BigDecimal totalNeed;
    @ApiModelProperty("每月需要")
    private BigDecimal perMonth;
    @ApiModelProperty("年")
    private Integer year;
    @ApiModelProperty("总共点亮")
    private BigDecimal totalLight;
    @ApiModelProperty("每月点亮（造星时该字段范围null）")
    private BigDecimal monthLight;
    @ApiModelProperty("参与的数字身份")
    private Integer lightNum;
    @ApiModelProperty("点亮进度")
    private BigDecimal lightPercent;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
