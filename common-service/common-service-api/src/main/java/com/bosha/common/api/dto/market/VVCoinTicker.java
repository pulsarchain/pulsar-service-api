package com.bosha.common.api.dto.market;

import java.io.Serializable;
import java.math.BigDecimal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: VVCoinTicker
 * @Author liqingping
 * @Date 2019-12-17 9:29
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class VVCoinTicker implements Serializable {
    private static final long serialVersionUID = 8898798913959455195L;

    @ApiModelProperty("交易对：BTC-USDT")
    @JsonIgnore
    private String instrument_id = "";
    @ApiModelProperty("最新成交价")
    private BigDecimal last = BigDecimal.ZERO;

    //-----------------------------
    @ApiModelProperty("币种：BTC")
    private String coin = "";
    @ApiModelProperty("交易方式：USDT")
    private String tradeType = "";
    @ApiModelProperty("交易对：BTC/USDT")
    private String tradePairs = "";


    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
        try {
            if (!instrument_id.contains("-"))
                return;
            String[] split = instrument_id.split("-");
            setCoin(split[0]);
            setTradeType(split[1]);
            setTradePairs(getCoin() + "/" + getTradeType());
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
    }
}
