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
 * @DESCRIPTION: UserBindResultDto
 * @Author liqingping
 * @Date 2019-12-16 13:34
 */

@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBindResultDto implements Serializable {
    private static final long serialVersionUID = -3387261526224385847L;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("用户信息")
    private UserDto user;
}
