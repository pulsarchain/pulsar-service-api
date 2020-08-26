package com.bosha.scan.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import cn.hutool.core.date.DateTime;
import com.bosha.scan.server.config.ScanServiceConfig;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: Transactions
 * @Author liqingping
 * @Date 2020-04-10 16:09
 */
@Data
@Document("transactions")
public class Transactions implements Serializable {
    private static final long serialVersionUID = -6590898399692195698L;

    private String id;

    private String from;

    private String to;

    private Long timestamp;

    private String hash;

    private String value;

    private BigDecimal amount;

    private DateTime dateTime;


    public BigDecimal getAmount() {
        if (StringUtils.isBlank(this.value))
            return BigDecimal.ZERO;
        return new BigDecimal(this.value).divide(ScanServiceConfig.RATE).setScale(4, BigDecimal.ROUND_DOWN);
    }

    public DateTime getDateTime() {
        try {
            return new DateTime(new Date(Long.parseLong(this.timestamp + "000")));
        } catch (Exception e) {
            return null;
        }
    }
}
