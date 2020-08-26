package com.bosha.star.api.entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="直播流")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningStream implements Serializable {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 直播挖矿的id
    */
    @ApiModelProperty(value="直播挖矿的id")
    private Long liveMiningId;

    /**
    * 推流地址
    */
    @ApiModelProperty(value="推流地址")
    private String pushUrl;

    /**
    * 拉流地址
    */
    @ApiModelProperty(value="拉流地址")
    private String pullUrl;

    /**
    * 流id
    */
    @ApiModelProperty(value="流id")
    private String streamId;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}