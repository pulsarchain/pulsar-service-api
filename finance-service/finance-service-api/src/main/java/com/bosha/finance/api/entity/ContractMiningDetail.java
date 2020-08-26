package com.bosha.finance.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel(value="com-bosha-finance-api-entity-ContractMiningDetail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractMiningDetail {
    /**
    * 主键id
    */
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    @NotBlank
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
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 关联业务对应的id
    */
    @ApiModelProperty(value="关联业务对应的id")
    private Long relatedId;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private BigDecimal amount;

    /**
    * 0 处理中，1 成功
    */
    @ApiModelProperty(value="0 处理中，1 成功")
    private Integer status;

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
}