package com.bosha.star.api.dto.web;

import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveAcceptDto
 * @Author liqingping
 * @Date 2020-03-25 11:08
 */
@Data
@ApiModel("直播接受邀请")
public class LiveAcceptDto {

    @ApiModelProperty(value = "直播挖矿的id", required = true)
    @NotNull(message = "直播id不可为空")
    private Long liveMiningId;
}
