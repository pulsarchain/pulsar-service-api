package com.bosha.dapp.api.dto;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DonateQuery
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 17:25
 */
@Data
@ApiModel("爱心捐赠查询")
public class DonateQuery extends Page {

    @ApiModelProperty("分类")
    private String category;
    @ApiModelProperty("类型：1 点亮，2 擦亮，3 造星")
    private Integer type;
    @ApiModelProperty("开始时间，格式：yyyy-MM-dd HH:mm:ss")
    private String startTime;
    @ApiModelProperty("结束时间，格式：yyyy-MM-dd HH:mm:ss")
    private String endTime;
    @ApiModelProperty("是否捐赠成功：1 是")
    private Integer success ;
    @ApiModelProperty("是否免费：0 否，1 是")
    private Integer free;
    @ApiModelProperty(value = "",hidden = true)
    private String address;
}
