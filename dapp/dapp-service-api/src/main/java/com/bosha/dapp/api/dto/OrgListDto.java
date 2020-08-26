package com.bosha.dapp.api.dto;

import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: OrgListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 12:13
 */
@Data
@ApiModel("机构列表")
public class OrgListDto {

    @ApiModelProperty("logo")
    private String logo;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("分类")
    private String category;
    @ApiModelProperty("创建人地址")
    private String address;
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("星星之火个数")
    private Integer starNum;
    @ApiModelProperty("走进星星个数")
    private Integer activityNum;
    @ApiModelProperty("共筹捐款")
    private BigDecimal totalAmount;
    @ApiModelProperty("捐款人数")
    private Integer totalDonateNum;
    @ApiModelProperty("简介")
    private String intro;
    @ApiModelProperty("是否关注：0 否，1 是")
    private Integer follow;
}
