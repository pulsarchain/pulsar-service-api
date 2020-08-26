package com.bosha.star.api.entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="用户礼物资产")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningGiftAsset implements Serializable {
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
     * 礼物id
     */
    @ApiModelProperty(value="礼物id")
    private Long giftId;

    /**
     * 个数
     */
    @ApiModelProperty(value="个数")
    private Integer num;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}