package com.bosha.user.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserInviteConfigDto
 * @Author liqingping
 * @Date 2019-12-16 16:26
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInviteConfigDto implements Serializable {
    private static final long serialVersionUID = 6244036728938007587L;

    @ApiModelProperty(value="每邀请一个地址奖励")
    private BigDecimal reward;

    @ApiModelProperty(value="每日最高奖励")
    private BigDecimal rewardPerDay;

    @ApiModelProperty(value="每月最高奖励")
    private BigDecimal rewardPerMonth;
}
