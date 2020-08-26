package com.bosha.scan.server.dto;

import java.math.BigDecimal;


import lombok.Data;
import org.bson.types.Decimal128;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: TransactionGroupInfo
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-16 19:08
 */
@Data
public class TransactionGroupInfo {

    public static final BigDecimal RATE = BigDecimal.valueOf(Math.pow(10, 18));

    private Decimal128 total;

    private String _id;

    private Integer count;

    private Long firstTime;

    private Long lastTime;

    public BigDecimal getTotal() {
        if (this.total==null)
            return BigDecimal.ZERO;
        return this.total.bigDecimalValue().divide(RATE);
    }
}
