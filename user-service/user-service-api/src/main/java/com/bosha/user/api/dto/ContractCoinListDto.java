package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractCoinListDto {

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
}
