package com.bosha.user.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * admin_user
 *
 * @author
 */
@Data
@ApiModel("后台管理员用户")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser implements Serializable {

    @ApiModelProperty("用户id")
    private Long id;
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不可为空")
    @Size(max = 100, message = "最多100个字")
    private String accountName;
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不可为空")
    private String mobile;
    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank(message = "姓名不可为空")
    @Size(max = 100, message = "最多100个字")
    private String realName;
    @ApiModelProperty(value = "用户密码", required = true)
    @NotBlank(message = "密码不可为空")
    @Size(min = 6, max = 20, message = "登录密码为6-20位 数字+大小写的组合")
    private String password;
    @ApiModelProperty(value = "岗位ID", required = true)
    @NotNull(message = "岗位id不可为空")
    private Integer positionId;
    @ApiModelProperty("状态 0禁用 1启用")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}