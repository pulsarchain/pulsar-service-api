package com.bosha.common.api.entity;

import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SystemConfig
 * @Author liqingping
 * @Date 2019-12-13 13:42
 */
@Data
@ApiModel("系统参数配置")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemConfig implements Serializable {
    private static final long serialVersionUID = 8821522264905436211L;

    @ApiModelProperty("邀请好友配置")
    private InviteFriendConfig inviteFriendConfig;
    @ApiModelProperty("星系配置")
    private StarConfig starConfig;
    @ApiModelProperty("直播挖矿配置")
    private LiveMiningConfig liveMiningConfig;

}
