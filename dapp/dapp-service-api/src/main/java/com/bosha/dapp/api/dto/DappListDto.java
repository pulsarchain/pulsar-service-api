package com.bosha.dapp.api.dto;

import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 13:14
 */
@Data
@ApiModel("dapp列表")
public class DappListDto implements Serializable {
    private static final long serialVersionUID = -2818819051240086916L;

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("logo")
    private String logo;
    @ApiModelProperty("dapp名称")
    private String name;
    @ApiModelProperty("分类名称")
    private String categoryName;
    @ApiModelProperty("分类id")
    private Long categoryId;
    @ApiModelProperty("简介")
    private String intro;
}
