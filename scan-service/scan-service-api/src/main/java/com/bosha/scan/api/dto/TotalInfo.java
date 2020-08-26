package com.bosha.scan.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;


import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: TotalInfo
 * @Author liqingping
 * @Date 2020-04-10 20:35
 */
@Data
public class TotalInfo implements Serializable {
    private static final long serialVersionUID = -5609460208856586903L;

    private BigDecimal totalBalance;

    private Integer totalNum;

    private Long totalTransactionCount;
}
