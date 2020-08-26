package com.bosha.user.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserLoginDto
 * @Author liqingping
 * @Date 2018-12-20 10:15
 */

@Data
@ApiModel
public class AdminUserLoginDto implements Serializable {
    private static final long serialVersionUID = 3390116915137034981L;

    @ApiModelProperty(value = "登录账号",required = true)
    @NotNull(message = "账号不可为空")
    private String loginAccount;
    @ApiModelProperty(value = "密码",required = true)
    @NotNull(message = "密码不可为空")
    private String password;

}
