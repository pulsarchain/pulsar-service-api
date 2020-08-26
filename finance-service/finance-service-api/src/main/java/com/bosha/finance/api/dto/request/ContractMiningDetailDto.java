package com.bosha.finance.api.dto.request;

import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
@Data
@ApiModel("添加挖矿明细")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractMiningDetailDto {
    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("合约地址")
    private String contractAddress;
    @ApiModelProperty("服务Id")
    private Integer serviceType;
    @ApiModelProperty("关联的Id")
    private Long  relatedId;
    @ApiModelProperty("金额")
    private BigDecimal amount;
    @ApiModelProperty("备注")
    private String remark;
}
