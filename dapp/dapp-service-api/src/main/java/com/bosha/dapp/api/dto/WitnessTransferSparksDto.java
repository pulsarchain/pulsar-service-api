package com.bosha.dapp.api.dto;

import javax.validation.constraints.NotBlank;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WitnessTransferSparksDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 13:53
 */

@Data
public class WitnessTransferSparksDto extends WitnessTransferDto{
    @ApiModelProperty(value = "故事",required = true)
    @NotBlank(message = "故事不可为空")
    private String story;
}
