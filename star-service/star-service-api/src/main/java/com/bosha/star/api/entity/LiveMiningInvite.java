package com.bosha.star.api.entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="直播推送邀请")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningInvite implements Serializable {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
    * 星系id
    */
    @ApiModelProperty(value="星系id")
    private Long starId;

    /**
    * 直播挖矿id
    */
    @ApiModelProperty(value="直播挖矿id")
    private Long liveMiningId;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}