package com.bosha.user.api.dto;

import java.io.Serializable;
import java.util.List;


import com.bosha.user.api.entity.Authentication;
import com.bosha.user.api.entity.AuxiliaryAuthentication;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AuthenticationInfoDto
 * @Author liqingping
 * @Date 2020-02-09 21:12
 */

@Data
@ApiModel("认证信息")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationInfoDto implements Serializable {
    private static final long serialVersionUID = 2620689240638004857L;

    @ApiModelProperty("认证信息")
    private Authentication authentication;
    @ApiModelProperty("辅助认证信息列表")
    private List<AuxiliaryAuthentication> auxiliaries;
    @ApiModelProperty("身份认证系统打币地址")
    private String systemAddress;

}
