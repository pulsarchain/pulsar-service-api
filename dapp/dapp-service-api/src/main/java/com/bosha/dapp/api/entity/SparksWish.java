package com.bosha.dapp.api.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "心愿")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksWish {
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 物品名称
     */
    @ApiModelProperty(value = "物品名称", required = true)
    private String title;

    /**
     * 类型：1 淘宝，2 拼多多，3 京东
     */
    @ApiModelProperty(value = "类型：1 淘宝，2 拼多多，3 京东", required = true)
    private Integer type;

    /**
     * 链接地址
     */
    @ApiModelProperty(value = "链接地址：淘宝传 couponShareUrl 字段，pdd传 mobile_url ")
    private String url;

    /**
     * schema链接
     */
    @ApiModelProperty(value = "schema链接，淘宝暂无此值，拼多多传 schema_url ")
    private String schemaUrl;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", required = true)
    private String desc;

    /**
     * 收货人名字
     */
    @ApiModelProperty(value = "收货人名字", required = true)
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址", required = true)
    private String receiveAddress;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("图片列表")
    @NotEmpty(message = "列表不可为空")
    private List<String> imgs;
}