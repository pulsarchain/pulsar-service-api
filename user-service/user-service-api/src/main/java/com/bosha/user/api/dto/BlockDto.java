package com.bosha.user.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AccountArrivalNoticeDto
 * @Author liqingping
 * @Date 2020-02-11 9:15
 */

@Data
@ApiModel
public class BlockDto implements Serializable {
    private static final long serialVersionUID = -25409856935516644L;

    private long blockNumber;

    private List<TransactionDto> transactions;

    @Data
    @ApiModel
    public static final class TransactionDto{

        public static final String TOPIC = "PUL_ARRIVAL";

        private String from;

        private String to;

        private String hash;

        private BigDecimal value;

        private BigDecimal fee;

        private long timestamp;

        private BigDecimal amount;

        private Date transactionTime;

    }
}
