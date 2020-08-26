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
 * @DESCRIPTION: UserDto
 * @Author liqingping
 * @Date 2019-12-16 13:35
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = -2256940195913192286L;

    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("用户类型：2 未认证，3 已认证个人，4 已认证企业，5 已认证政府")
    private Integer type;
    @ApiModelProperty("Google密钥，null 表示没有绑定")
    private String googleSecret;
    @ApiModelProperty("是否已关联后台用户")
    private boolean bind;
    @ApiModelProperty
    private Long userId;
    @ApiModelProperty("是否开启google支付验证")
    private boolean enableGoogleSecret;
}
