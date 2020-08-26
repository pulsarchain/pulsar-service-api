package com.bosha.scan.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AddressRateDto
 * @Author liqingping
 * @Date 2020-04-10 17:59
 */
@Data
@ApiModel("")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRateDto implements Serializable {
    private static final long serialVersionUID = 4031752275235979916L;

    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("余额")
    private BigDecimal balance;
    @ApiModelProperty("百分比")
    private BigDecimal percent;
    @ApiModelProperty("排名")
    private Integer ranking;
    @ApiModelProperty("0 普通地址，1 合约地址")
    private Integer type;
    @JsonIgnore
    @ApiModelProperty(value = "交易数量")
    private Long transactionCount;
}
