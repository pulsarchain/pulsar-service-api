package com.bosha.scan.api.dto;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: Transactions
 * @Author liqingping
 * @Date 2020-04-10 16:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDto implements Serializable {
    private static final long serialVersionUID = -6590898399692195698L;

    private String from;

    private String to;

    private String hash;

    private String amount;

    private String timestamp;
}
