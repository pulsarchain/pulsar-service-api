package com.bosha.dapp.api.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: InviteWitnessDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 14:02
 */
@Data
@ApiModel("邀请见证")
public class InviteWitnessDto {

    @ApiModelProperty(value = "邀请见证类型：4 机构，5 基金", required = true)
    @NotNull(message = "类型不可为空")
    @Max(value = 5, message = "最大值为5")
    @Min(value = 4, message = "最小值为4")
    private Integer type;
    @ApiModelProperty(value = "关联的id", required = true)
    @NotNull(message = "关联的id不可为空")
    private Long id;
    @ApiModelProperty(value = "地址列表", required = true)
    @NotEmpty(message = "关系列表不可为空")
    private List<String> addresses;
}
