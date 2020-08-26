package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractCoinDto {
    /**
     * 发布者
     */
    @ApiModelProperty(value="发布者")
    private String userAddress;

    /**
     * 总量
     */
    @ApiModelProperty(value="总量")
    private BigDecimal total;

    /**
     * 合约币名称
     */
    @ApiModelProperty(value="合约币名称")
    private String coinName;

    /**
     * 小数点位数
     */
    @ApiModelProperty(value="小数点位数")
    private Integer digit;

    /**
     * 图标
     */
    @ApiModelProperty(value="图标")
    private String logo;

    /**
     * 合约地址
     */
    @ApiModelProperty(value="合约地址")
    private String contractAddress;

    /**
     * hash
     */
    @ApiModelProperty(value="hash")
    private String hash;
}
