package com.bosha.dapp.api.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WitnessDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 11:52
 */
@Data
@ApiModel("见证")
public class WitnessDto {

    @ApiModelProperty(value = "关联的id", required = true)
    @NotNull(message = "关联的id不可为空")
    private Long relatedId;

    @ApiModelProperty("见证地址")
    @NotEmpty(message = "地址列表不可为空")
    private List<String> addresses;

}
