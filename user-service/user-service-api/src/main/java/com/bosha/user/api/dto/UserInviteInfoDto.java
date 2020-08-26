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
 * @DESCRIPTION: UserInviteInfoDto
 * @Author liqingping
 * @Date 2019-12-16 16:22
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInviteInfoDto implements Serializable {
    private static final long serialVersionUID = -7334679515918049981L;

    @ApiModelProperty("当天累计获得奖励")
    private BigDecimal dayTotal;
    @ApiModelProperty("当月累计获得奖励")
    private BigDecimal monthTotal;
    @ApiModelProperty("累计邀请地址数")
    private Integer inviteTotal;
    @ApiModelProperty("累计获得奖励")
    private BigDecimal rewardTotal;
}
