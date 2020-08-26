package com.bosha.dapp.api.entity;

import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="机构关注")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksOrgFollow {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
    * 机构id
    */
    @ApiModelProperty(value="机构id")
    private Long orgId;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}