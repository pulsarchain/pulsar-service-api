package com.bosha.dapp.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WitnessTransferDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 13:27
 */
@Data
@ApiModel("邀请见证打币")
public class WitnessTransferDto {

    @ApiModelProperty(value = "星星之火的id",required = true)
    @NotNull(message = "id不可为空")
    private Long id;
    @ApiModelProperty(value = "打币的hash",required = true)
    @NotBlank(message = "打币hash不可为空")
    private String hash;
}
