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

@ApiModel(value="爱心捐赠购买记录")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksDonateBuy {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 地址
    */
    @ApiModelProperty(value="地址")
    private String address;

    /**
    * 捐赠id
    */
    @ApiModelProperty(value="捐赠id")
    private Long donateId;

    /**
    * 数量
    */
    @ApiModelProperty(value="数量")
    private Integer num;

    /**
    * 是否已通知
    */
    @ApiModelProperty(value="是否已通知：0 否，1 是")
    private Integer notice;

    @ApiModelProperty(value="名字")
    private String name;

    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
    private String phone;

    /**
     * 收货地址
     */
    @ApiModelProperty(value="收货地址")
    private String receiveAddress;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}