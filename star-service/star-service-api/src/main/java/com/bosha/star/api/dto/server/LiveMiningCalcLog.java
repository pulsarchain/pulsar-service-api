package com.bosha.star.api.dto.server;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningCalcLog
 * @Author liqingping
 * @Date 2020-04-02 15:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveMiningCalcLog {

    private Integer share = 0;
    private Integer comment = 0;
    private Integer view = 0;
    private Integer like = 0;
    private Integer gift = 0;

    private BigDecimal shareAmount = BigDecimal.ZERO;
    private BigDecimal commentAmount = BigDecimal.ZERO;
    private BigDecimal viewAmount = BigDecimal.ZERO;
    private BigDecimal likeAmount = BigDecimal.ZERO;
    private BigDecimal giftAmount = BigDecimal.ZERO;

    private BigDecimal avgPerMinTotal = BigDecimal.ZERO;
    private BigDecimal avgPerMinPerCalc = BigDecimal.ZERO;
}
