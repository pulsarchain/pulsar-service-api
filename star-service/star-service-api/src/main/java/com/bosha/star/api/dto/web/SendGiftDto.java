package com.bosha.star.api.dto.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SendGiftDto
 * @Author liqingping
 * @Date 2020-03-25 21:25
 */
@Data
@ApiModel("送礼物")
public class SendGiftDto {

    @ApiModelProperty(value = "直播挖矿的id", required = true)
    @NotNull(message = "直播id不可为空")
    private Long liveMiningId;
    @ApiModelProperty(value = "礼物id", required = true)
    @NotNull(message = "礼物id不可为空")
    private Long giftId;
    @ApiModelProperty(value = "打赏个数", required = true)
    @NotNull(message = "打赏个数不可为空")
    @Min(value = 1, message = "最小值为1")
    private Integer num;
    @ApiModelProperty
    private String address;
}
