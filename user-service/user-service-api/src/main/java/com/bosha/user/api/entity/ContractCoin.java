package com.bosha.user.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-bosha-user-api-entity-ContractCoin")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractCoin {
    @ApiModelProperty(value = "null")
    private Long id;

    /**
     * 发布者
     */
    @ApiModelProperty(value = "发布者")
    private String userAddress;

    /**
     * 总量
     */
    @ApiModelProperty(value = "总量")
    private BigDecimal total;

    /**
     * 合约币名称
     */
    @ApiModelProperty(value = "合约币名称")
    private String coinName;

    /**
     * 合约币简称
     */
    @ApiModelProperty(value = "合约币简称")
    private String shortName;

    /**
     * 小数点位数
     */
    @ApiModelProperty(value = "小数点位数")
    private Integer digit;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String logo;

    /**
     * 状态，1显示，2隐藏
     */
    @ApiModelProperty(value = "状态，1显示，2隐藏")
    private Integer status;

    /**
     * 合约地址
     */
    @ApiModelProperty(value = "合约地址")
    private String contractAddress;

    /**
     * hash
     */
    @ApiModelProperty(value = "hash")
    private String hash;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public static ContractCoinBuilder builder() {
        return new ContractCoinBuilder();
    }
}