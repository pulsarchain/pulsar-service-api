package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ImRedResDto implements Serializable {
    @ApiModelProperty(value = "已分配币数")
    private BigDecimal amount = BigDecimal.ZERO ;

    /**
     * minedNumber
     */
    @ApiModelProperty(value = "已分配人数")
    private Integer number = 0;
}
