package com.bosha.star.api.dto.server;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: InsertStarRewardBatchResult
 * @Author liqingping
 * @Date 2020-03-08 14:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsertStarRewardBatchResult {

    private Long starRewardId;

    private String address;

    private BigDecimal amount;

    private String reamrk;
}
