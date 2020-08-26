package com.bosha.finance.api.entity;

import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-bosha-finance-api-entity-MiningDetail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiningDetail {
    /**
    * 主键id
    */
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
    * 用户id
    */
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
    * 合约地址
    */
    @ApiModelProperty(value="合约地址")
    private String contractAddress;

    /**
    * 交易hash
    */
    @ApiModelProperty(value="交易hash")
    private String hash;

    /**
    * 业务类型：具体查看枚举值
    */
    @ApiModelProperty(value="业务类型：具体查看枚举值")
    private Integer serviceType;

    /**
    * 关联业务对应的id
    */
    @ApiModelProperty(value="关联业务对应的id")
    private Long relatedId;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private BigDecimal money;

    /**
    * 1 成功，2 失败，3 其他
    */
    @ApiModelProperty(value="1 成功，2 失败，3 其他")
    private Integer status;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}