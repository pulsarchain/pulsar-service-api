package com.bosha.dapp.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DonateBuyDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-27 17:38
 */
@Data
@ApiModel("")
public class DonateBuyDto {

    @ApiModelProperty(value = "捐赠的id", required = true)
    @NotNull(message = "id不可为空")
    private Long id;
    @ApiModelProperty("购买/领取的数量，不传默认为1")
    private Integer num = 1;
    @ApiModelProperty(value="名字",required = true)
    @NotBlank(message = "收货人名字不可为空")
    private String name;

    /**
     * 电话
     */
    @ApiModelProperty(value="电话",required = true)
    @NotBlank(message = "收货人电话不可为空")
    private String phone;

    /**
     * 收货地址
     */
    @ApiModelProperty(value="收货地址",required = true)
    @NotBlank(message = "收货人地址不可为空")
    private String receiveAddress;
}
