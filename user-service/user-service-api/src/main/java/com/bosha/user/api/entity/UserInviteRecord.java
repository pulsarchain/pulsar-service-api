package com.bosha.user.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="用户邀请记录")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInviteRecord implements Serializable {
    @ApiModelProperty(value="id")
    private Long id;

    /**
    * 用户id
    */
    @ApiModelProperty(value="用户id")
    @JsonIgnore
    private Long userId;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    private String userAddress;

    /**
    * 被邀请人id
    */
    @ApiModelProperty(value="被邀请人id")
    @JsonIgnore
    private Long beInviteUserId;

    /**
    * 被邀请人地址
    */
    @ApiModelProperty(value="被邀请人地址")
    private String beInviteUserAddress;

    /**
    * 币种id
    */
    @ApiModelProperty(value="币种id")
    @JsonIgnore
    private Long coinId;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private BigDecimal amount;

    /**
    * 交易hash
    */
    @ApiModelProperty(value="交易hash")
    private String hash;


    /**
     * ip地址
     */
    @ApiModelProperty(value="ip地址")
    private String beInviteUserIp;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 合约地址
    */
    @ApiModelProperty(value="合约地址")
    private String contractAddress;

    private static final long serialVersionUID = 1L;
}