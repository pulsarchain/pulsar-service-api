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
 * @DESCRIPTION: UserGoogleSecretDto
 * @Author liqingping
 * @Date 2020-01-08 16:15
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGoogleSecretDto implements Serializable {

    private static final long serialVersionUID = -2632985327750401879L;

    @ApiModelProperty(value = "是否开启支付google校验")
    private Boolean enableGoogleSecret;
    @ApiModelProperty(value = "是否已设置google验证码")
    private Boolean isSetGoogleSecret;
}
