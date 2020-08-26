package com.bosha.finance.api.dto.request;

import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
@ToString
public class UserAssetDto implements Serializable {
    private Long userId;
    private Long coinId;
    private BigDecimal money;
    private FinanceServiceTypeEnum financeServiceTypeEnum;
    private String remark;
}
