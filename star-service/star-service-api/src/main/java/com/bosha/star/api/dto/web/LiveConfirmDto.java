package com.bosha.star.api.dto.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveConfirmDto
 * @Author liqingping
 * @Date 2020-03-25 18:44
 */
@Data
@ApiModel("开播确认")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveConfirmDto {

    @ApiModelProperty("推流地址")
    private String pushUrl;
    @ApiModelProperty("状态：0 可以开播，1 当前直播不存在，2 当前直播区块确认中，\n3 当前直播结束时间已过，4 当前直播还未到开始时间，5 当前用户地址不是主播")
    private Integer status = 0;
}
