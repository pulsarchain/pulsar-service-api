package com.bosha.dapp.api.dto;

import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: FundationListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 10:26
 */
@Data
@ApiModel("基金列表")
public class FundationListDto {

    @ApiModelProperty("logo")
    private String logo;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("投入")
    private BigDecimal invest;
    @ApiModelProperty("年")
    private Integer year;
    @ApiModelProperty("年返")
    private BigDecimal rebate;
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("创建人的地址")
    private String address;
}
