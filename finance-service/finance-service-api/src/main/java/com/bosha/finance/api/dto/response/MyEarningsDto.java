package com.bosha.finance.api.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MyEarningsDto
 * @Author liqingping
 * @Date 2020-04-15 8:47
 */
@Data
@ApiModel("我的收益")
public class MyEarningsDto implements Serializable {
    private static final long serialVersionUID = -7706036049435900695L;

    @ApiModelProperty("星系")
    private BigDecimal star;
    @ApiModelProperty("星系百分比")
    private BigDecimal starPercent= BigDecimal.ZERO;

    @ApiModelProperty("委托挖矿")
    private BigDecimal commissionedMining = BigDecimal.ZERO;
    @ApiModelProperty("委托挖矿百分比")
    private BigDecimal commissionedMiningPercent = BigDecimal.ZERO;

    @ApiModelProperty("直播")
    private BigDecimal live;
    @ApiModelProperty("直播百分比")
    private BigDecimal livePercent= BigDecimal.ZERO;

    @ApiModelProperty("资讯")
    private BigDecimal news;
    @ApiModelProperty("资讯百分比")
    private BigDecimal newsPercent= BigDecimal.ZERO;

    @ApiModelProperty("其他")
    private BigDecimal other;
    @ApiModelProperty("其他百分比")
    private BigDecimal otherPercent= BigDecimal.ZERO;
}
