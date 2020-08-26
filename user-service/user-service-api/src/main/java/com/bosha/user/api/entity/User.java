package com.bosha.user.api.entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "用户")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {


    public static final Integer STATUS_AVAILABLE = 1, STATUS_DISABLE = 0;

    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 冷钱包地址
     */
    @ApiModelProperty(value = "冷钱包地址")
    private String address;

    /**
     * 1 安卓，2 iOS，3 web
     */
    @ApiModelProperty(value = "1 安卓，2 iOS，3 web")
    private Integer source;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headImg;

    /**
     * 状态：0 禁用，1 启用
     */
    @ApiModelProperty(value = "状态：0 禁用，1 启用")
    private Integer status;

    /**
     * 用户类型：2 未认证，3 已认证个人，4 已认证企业
     */
    @ApiModelProperty(value = "用户类型：2 未认证，3 已认证个人，4 已认证企业，5 已认证政府")
    private Integer type;

    /**
     * 设备号
     */
    @ApiModelProperty(value = "设备号")
    private String deviceId;

        /**
     * google密钥
     */
    @ApiModelProperty(value = "google密钥")
    private String googleSecret;

    /**
     * 是否开启支付google校验
     */
    @ApiModelProperty(value="是否开启支付google校验")
    private Boolean enableGoogleSecret;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("邀请人地址")
    private String inviteUserAddress;

    @ApiModelProperty("用户设备类型")
    private Integer userClientType;

    /**
     * 注册ip
     */
    @ApiModelProperty(value="注册ip")
    private String registerIp;

    private static final long serialVersionUID = 1L;
}