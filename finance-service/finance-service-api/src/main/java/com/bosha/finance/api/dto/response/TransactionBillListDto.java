package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("交易记录")
public class TransactionBillListDto implements Serializable {
    private Long id;
    @ApiModelProperty("流水号")
    private String serialNumber;
    @ApiModelProperty("业务类型：1 充值，2 提币，3 其他 待补充")
    private Integer serviceType;
    @ApiModelProperty("用户Id")
    private String userId;
    @ApiModelProperty("用户钱包地址")
    private String address;
    @ApiModelProperty("转账地址")
    private String toAddress;
    @ApiModelProperty("来源地址")
    private String fromAddress;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("转入数")
    private BigDecimal number;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("实际到账")
    private BigDecimal actualNumber;
    @ApiModelProperty("转账时间")
    private Date createTime;
    @ApiModelProperty("审核时间")
    private Date auditTime;
    @ApiModelProperty("状态：1 进行中，2 已完成 ，3 失败，4 审核中，5 已撤销，6 未通过，7 审核通过")
    private Integer status;
    @ApiModelProperty("交易hash")
    private String transactionHash;


}
