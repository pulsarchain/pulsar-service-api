package com.bosha.finance.api.dto.request;

import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("挖矿收益")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiningDetailDto implements Serializable {
    private Long id;
    @ApiModelProperty("用户Id")
    private Long userId;
    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("合约地址")
    private String contractAddress;
    @ApiModelProperty("hash")
    private String hash;
    @ApiModelProperty("服务Id")
    private FinanceServiceTypeEnum serviceType;
    @ApiModelProperty("关联的Id")
    private Long  relatedId;
    @ApiModelProperty("1 成功，2 失败，3 其他")
    private Integer status;
    @ApiModelProperty("金额")
    private BigDecimal money;
}
