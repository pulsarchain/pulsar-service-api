package com.bosha.user.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: BindGoogleSecretDto
 * @Author liqingping
 * @Date 2019-12-16 14:07
 */

@Data
@ApiModel
public class BindGoogleSecretDto implements Serializable {
    private static final long serialVersionUID = 8796407443227722949L;

    @ApiModelProperty(value = "google密钥",required = true)
    @NotBlank(message = "密钥不可为空")
    private String googleSecret;

    @ApiModelProperty(value = "code",required = true)
    @NotBlank(message = "code不可为空")
    private String code;
}
