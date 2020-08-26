package com.bosha.star.api.dto.web;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: StarInvitePushDto
 * @Author liqingping
 * @Date 2020-03-06 19:19
 */
@Data
@ApiModel("邀请加入星系的推送通知")
public class StarInvitePushDto {

    @ApiModelProperty(value = "被邀请者的地址列表", required = true)
    @NotEmpty(message = "列表不可为空")
    private List<String> inviteList;

    @ApiModelProperty(value = "星系id", required = true)
    @NotNull(message = "星系id不可为空")
    private Long starId;
}
