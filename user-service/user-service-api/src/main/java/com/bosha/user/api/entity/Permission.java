package com.bosha.user.api.entity;

import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("权限")
public class Permission implements Serializable {

    private Integer id;

    @ApiModelProperty("权限名称")
    private String name;

    @ApiModelProperty("上一级ID")
    private Integer parentId;

    @ApiModelProperty("后台api接口地址")
    private String url;

    @ApiModelProperty("tag")
    private String tag;

    private static final long serialVersionUID = 1L;
}