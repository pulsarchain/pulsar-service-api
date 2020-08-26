package com.bosha.scan.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("地址")
public class AddressBalance implements Serializable {

    @JsonIgnore
    private Long id;
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("备注")
    private String remark;


    /**
     * 是否是合约地址：0 否，1 是
     */
    @ApiModelProperty(value = "0 普通地址，1 合约地址")
    private Integer type;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    /**
     * 交易数量
     */
    @JsonIgnore
    @ApiModelProperty(value = "交易数量")
    private Long transactionCount;


    /**
     * 是否显示：0 否，1 是
     */
    @JsonIgnore
    private Integer show;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}