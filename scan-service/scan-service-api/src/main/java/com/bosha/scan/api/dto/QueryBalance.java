package com.bosha.scan.api.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: QueryBalance
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-17 12:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryBalance {

    private String address;

    private BigDecimal balance;
}
