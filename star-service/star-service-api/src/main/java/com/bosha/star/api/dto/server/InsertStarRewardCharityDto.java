package com.bosha.star.api.dto.server;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: InsertStarRewardDto
 * @Author liqingping
 * @Date 2020-03-07 12:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsertStarRewardCharityDto {
    private Long starMemberId;
    private String contractAddress;
    private BigDecimal scale;
}
