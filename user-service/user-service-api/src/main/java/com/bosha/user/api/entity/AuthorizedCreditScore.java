package com.bosha.user.api.entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "授权查看信用分")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedCreditScore implements Serializable {

    public static final int STATUS_CONFIRMING = 0, STATUS_ARRIVAL = 1, STATUS_EXPIRED = 2;
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 查看人的地址
     */
    @ApiModelProperty(value = "查看人的地址")
    private String from;

    /**
     * 被查看人地址
     */
    @ApiModelProperty(value = "被查看人地址")
    private String to;

    /**
     * 状态：0 已打币，待确认，1 已到账
     */
    @ApiModelProperty(value = "状态：0 已打币，区块待确认，1 已到账，2 已过期")
    private Integer status;

    /**
     * 交易hash
     */
    @ApiModelProperty(value = "交易hash")
    private String hash;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    /**
     * 更新时间/到账时间
     */
    @ApiModelProperty(value = "更新时间/到账时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}