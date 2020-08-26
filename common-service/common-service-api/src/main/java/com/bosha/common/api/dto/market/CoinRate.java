package com.bosha.common.api.dto.market;

import java.io.Serializable;
import java.math.BigDecimal;


import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CoinRate
 * @Author liqingping
 * @Date 2019-12-17 13:41
 */
@Data
public class CoinRate implements Serializable {
    private static final long serialVersionUID = -6066740511577957775L;

    private String currency;

    private BigDecimal rate;
}
