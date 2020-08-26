package com.bosha.user.api.entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="信用分")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditScore implements Serializable {
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
    * 信用分
    */
    @ApiModelProperty(value="信用分")
    private Integer score;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 评估时间
    */
    @ApiModelProperty(value="评估时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}