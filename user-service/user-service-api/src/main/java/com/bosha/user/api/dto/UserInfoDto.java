package com.bosha.user.api.dto;

import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserBindDto
 * @Author liqingping
 * @Date 2019-12-16 14:25
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserInfoDto implements Serializable {
    private static final long serialVersionUID = -6326228889286842556L;

    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("邀请人地址")
    private String inviteAddress;
    @ApiModelProperty("开启支付google校验")
    private Boolean enableGoogleSecret;
}
