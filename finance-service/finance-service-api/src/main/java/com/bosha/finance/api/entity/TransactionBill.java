package com.bosha.finance.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="交易记录")
@Data
public class TransactionBill {
    @ApiModelProperty(value="null")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 流水号
     */
    @ApiModelProperty(value="流水号")
    private String serialNumber;

    /**
     * 类型：1 转入，2 转出
     */
    @ApiModelProperty(value="类型：1 转入，2 转出")
    private Integer type;

    /**
     * 业务类型：1 充值，2 提币，3 其他 待补充
     */
    @ApiModelProperty(value="业务类型：1 充值，2 提币，3 其他 待补充")
    private Integer serviceType;

    /**
     * 币种id
     */
    @ApiModelProperty(value="币种id")
    private Long coin;

    /**
     * 交易数量
     */
    @ApiModelProperty(value="交易数量")
    private BigDecimal number;

    /**
     * 实际数量
     */
    @ApiModelProperty(value="实际数量")
    private BigDecimal actualNumber;

    /**
     * 手续费
     */
    @ApiModelProperty(value="手续费")
    private BigDecimal fee;

    /**
     * 交易时间
     */
    @ApiModelProperty(value="交易时间")
    private Date transactionTime;

    /**
     * 状态：1 进行中，2 已完成 ，3 失败，4 审核中，5 已撤销，6 审核不通过，7 审核通过
     */
    @ApiModelProperty(value="状态：1 进行中，2 已完成 ，3 失败，4 审核中，5 已撤销，6 审核不通过，7 审核通过")
    private Integer status;

    /**
     * 来源地址
     */
    @ApiModelProperty(value="来源地址")
    private String fromAddress;

    /**
     * 到账地址
     */
    @ApiModelProperty(value="到账地址")
    private String toAddress;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 交易hash
     */
    @ApiModelProperty(value="交易hash")
    private String transactionHash;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 当时余额
     */
    @ApiModelProperty(value="当时余额")
    private BigDecimal balance;

    /**
     * 区块高度
     */
    @ApiModelProperty(value="区块高度")
    private Long blockNum;

    /**
     * 审核时间
     */
    @ApiModelProperty(value="审核时间")
    private Date auditTime;

    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
    private String operator;

    /**
     * 审核失败原因
     */
    @ApiModelProperty(value="审核失败原因")
    private Integer auditReason;
}