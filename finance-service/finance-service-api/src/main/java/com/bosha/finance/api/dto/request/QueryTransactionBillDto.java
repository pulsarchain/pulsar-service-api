package com.bosha.finance.api.dto.request;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class QueryTransactionBillDto extends Page implements Serializable {
    @ApiModelProperty(value="用户Id")
    private Long userId;
    @ApiModelProperty(value="流水号")
    private String serialNumber;
    @ApiModelProperty(value="钱包地址")
    private Integer address;
    @ApiModelProperty(value="开始时间")
    private String startTime;
    @ApiModelProperty(value="结束时间")
    private String endTime;
    @ApiModelProperty("状态:1 转入中，2 成功 ，3 失败")
    private Integer status;
    @ApiModelProperty("燃料")
    private Long coinId;
    @ApiModelProperty("开始交易数量")
    private BigDecimal startNumber;
    @ApiModelProperty("结束交易数量")
    private BigDecimal endNumber;
    @ApiModelProperty("业务类型：1 充值，2 提币，3 其他 待补充")
    private Integer serviceType;


}
