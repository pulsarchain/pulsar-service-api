package com.bosha.dapp.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ActivityCalendarDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 17:59
 */
@Data
@ApiModel("星星日历")
public class ActivityCalendarDto {

    @ApiModelProperty("日期")
    private String date;
    @ApiModelProperty("是否有数据：0 否，1 是")
    private Integer count;
}
