package com.bosha.common.api.dto.market;

import java.io.Serializable;
import java.util.List;


import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: BlockCCResult
 * @Author liqingping
 * @Date 2019-12-17 13:42
 */
@Data
public class BlockCCResult implements Serializable {
    private static final long serialVersionUID = 5749136421601135309L;

    private int code;

    private String message;

    private Object data;

    @Data
    public static final class ExchangeRate {
        private Long timestamp;
        private String base;
        private List<CoinRate> rates;
    }

    public boolean isSuccess() {
        return this.code == 0 && "success".equalsIgnoreCase(this.message);
    }

}
