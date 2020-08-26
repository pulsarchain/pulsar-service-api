package com.bosha.user.api.dto;

import java.io.Serializable;


import com.bosha.user.api.entity.AdminUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserLoginResultDto
 * @Author liqingping
 * @Date 2018-12-20 10:11
 */

@Data
@ApiModel("管理用户登录返回值")
public class AdminUserLoginResultDto implements Serializable {
    private static final long serialVersionUID = 9011208194959844252L;

    private String token;

    @ApiModelProperty("tags 英文逗号分隔")
    private String tags;

    @ApiModelProperty("用户")
    private AdminUser user;
}
