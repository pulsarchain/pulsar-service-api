package com.bosha.dapp.api.dto;

import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: MyDappReportListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 18:20
 */
@Data
@ApiModel("我的举报记录")
public class MyDappReportListDto {

    @ApiModelProperty("")
    private String logo;
    @ApiModelProperty("dapp名称")
    private String name;
    @ApiModelProperty("dapp分类名称")
    private String categoryName;
    @ApiModelProperty("dapp简介")
    private String intro;
    @ApiModelProperty("举报的id")
    private Long reportId;
    @ApiModelProperty("举报的时间")
    private Date reportTime;
}
