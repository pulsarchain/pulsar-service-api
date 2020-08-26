package com.bosha.common.api.dto.market;

import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CoinPrice
 * @Author liqingping
 * @Date 2019-12-17 16:53
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class CoinPrice extends VVCoinTicker implements Serializable {
    private static final long serialVersionUID = 4068160617361659947L;

    @ApiModelProperty("人民币价格")
    private BigDecimal cny = BigDecimal.ZERO;
    @ApiModelProperty("美元价格")
    private BigDecimal usd = BigDecimal.ZERO;
}
