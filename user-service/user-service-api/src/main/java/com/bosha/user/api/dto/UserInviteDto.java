package com.bosha.user.api.dto;

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
 * @DESCRIPTION: UserInviteDto
 * @Author liqingping
 * @Date 2019-12-16 16:18
 */

@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInviteDto implements Serializable {
    private static final long serialVersionUID = 4599754532715234207L;

    @ApiModelProperty("用户邀请上限信息")
    private UserInviteInfoDto inviteInfo;
    @ApiModelProperty("系统邀请配置")
    private UserInviteConfigDto inviteConfig;
}
