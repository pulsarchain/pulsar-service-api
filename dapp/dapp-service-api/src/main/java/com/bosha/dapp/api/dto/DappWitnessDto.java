package com.bosha.dapp.api.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappWitnessDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 14:55
 */
@Data
@ApiModel("见证记录")
public class DappWitnessDto {

    @ApiModelProperty(value = "dapp的id", required = true)
    @NotNull(message = "dappId不可为空")
    private Long dappId;
    @ApiModelProperty(value = "见证的地址", required = true)
    @Size(min = 1, max = 5, message = "范围1-5")
    @NotEmpty(message = "列表不可为空")
    private Set<String> address;
}
