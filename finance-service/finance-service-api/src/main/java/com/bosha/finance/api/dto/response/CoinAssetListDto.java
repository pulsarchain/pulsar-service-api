package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("用户资产列表")
public class CoinAssetListDto implements Serializable {
    private Long id;

    /**
     * 中文名
     */
    @ApiModelProperty(value="中文名")
    private String cnName;

    /**
     * 币种
     */
    @ApiModelProperty(value="币种")
    private String symbolName;

    /**
     * 提现状态：0 关闭，1 开启
     */
    @ApiModelProperty(value="提现状态：0 关闭，1 开启")
    private Integer withdrawStatus;

    /**
     * 充值状态：0 关闭，1 开启
     */
    @ApiModelProperty(value="充值状态：0 关闭，1 开启")
    private Integer depositStatus;
    /**
     * 合约地址
     */
    @ApiModelProperty(value="合约地址")
    private String contractAddress;

    @ApiModelProperty(value = "可用余额")
    private String balance;

    @ApiModelProperty(value = "冻结金额")
    private String frozenBalance;

    @ApiModelProperty("价格人民币")
    private BigDecimal priceCny = BigDecimal.ZERO;

    @ApiModelProperty("价格美元")
    private BigDecimal priceUsd = BigDecimal.ZERO;

}
