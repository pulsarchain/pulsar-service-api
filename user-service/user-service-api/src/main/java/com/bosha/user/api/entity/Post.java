package com.bosha.user.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel
@EqualsAndHashCode
public class Post implements Serializable {
    private static final long serialVersionUID = -8306559989283302308L;

    private Integer id;

    @ApiModelProperty(value = "岗位名称", required = true)
    @NotBlank(message = "岗位名称不可为空")
    @Length(max = 100, message = "岗位名最多100个字")
    private String name;

    @ApiModelProperty("岗位状态 0禁用 1启用")
    private Integer status;

    @ApiModelProperty(value = "权限tag集合，逗号分隔 ",required = true)
    @NotBlank(message = "岗位权限不可为空")
    private String permissionTags;

    @ApiModelProperty("权限列表")
    private List<Permission> permissionList;

    @ApiModelProperty("创建时间")
    private Date createTime;

}