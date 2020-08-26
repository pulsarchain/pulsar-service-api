package com.bosha.common.api.dto.market;

import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: WebsocketResult
 * @Author liqingping
 * @Date 2020-01-02 14:55
 */
@Data
public class WebsocketResult {

    private String message;

    private Overview overview;

    @Data
    public static class Overview {
        private String difficulty;
        private long hashRate;
        private long blockTime;
        private String tps;
        private long blockNumber;
    }
}
