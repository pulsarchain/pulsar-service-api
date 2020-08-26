package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("转入")
public class IntoTransactionBillListDto implements Serializable {
    private Long id;
    @ApiModelProperty("流水号")
    private String serialNumber;
    @ApiModelProperty("流水号")
    private String userId;
    @ApiModelProperty("流水号")
    private String address;
    @ApiModelProperty("币种名称")
    private String coinName;
    @ApiModelProperty("转入数")
    private BigDecimal number;
    @ApiModelProperty("转入时间")
    private Date createTime;
    @ApiModelProperty("到账时间")
    private Date transactionTime;
    @ApiModelProperty("状态：1 进行中，2 已完成 ，3 失败，4 审核中，5 已撤销，6 未通过，7 审核通过")
    private Integer status;
    @ApiModelProperty("交易hash")
    private String transactionHash;
    @ApiModelProperty("备注")
    private String remark;



}
