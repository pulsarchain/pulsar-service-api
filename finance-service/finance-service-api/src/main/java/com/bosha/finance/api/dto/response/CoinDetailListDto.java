package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class CoinDetailListDto implements Serializable {
    @ApiModelProperty("最大提币数")
    private BigDecimal withdrawMax;
    @ApiModelProperty("最小提币数")
    private BigDecimal withdrawMin;
    private Long id;
    @ApiModelProperty("币种名称")
    private String symbolName;
    @ApiModelProperty("手续费")
    private BigDecimal fee;
    @ApiModelProperty("logo")
    private String logo;
}
