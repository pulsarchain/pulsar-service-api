package com.bosha.star.api.dto.server;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UpdateStarRewardDto
 * @Author liqingping
 * @Date 2020-03-08 17:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStarRewardDto {
    private String hash;
    private String address;
    private BigDecimal amount;
    private Long srId;
}
