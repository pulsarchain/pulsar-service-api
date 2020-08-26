package com.bosha.finance.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("充币到到账后记录")
@ToString
public class ChargingDto implements Serializable {
    @ApiModelProperty("充币地址")
    private String fromAddress;
    @ApiModelProperty("到账地址")
    private String toAddress;
    @ApiModelProperty("转账hash")
    private String transactionHash;
    @ApiModelProperty("区块号")
    private Long blockNum;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("币种id")
    private Long coinId;
    @ApiModelProperty("userId")
    private Long userId;
    @ApiModelProperty("金额")
    private BigDecimal money;
    @ApiModelProperty("手续费")
    private BigDecimal fee;



}
