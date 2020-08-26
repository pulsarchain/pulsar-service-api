package com.bosha.dapp.api.dto;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ActivityQuery
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 14:36
 */
@Data
@ApiModel("查询活动列表")
public class ActivityQuery extends Page {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("热门活动：1 热门，该字段不传表示查询所有")
    private Integer hot;

}
