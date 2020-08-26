package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("我收到的红包明细")
public class ImMyReceiveRedDto {
    /**
     * 领取人地址
     */
    @ApiModelProperty(value = "红包类型")
    private Integer nickName;
    /**
     * 红包金额
     */
    @ApiModelProperty(value = "红包金额")
    private BigDecimal money;

    @ApiModelProperty(value = "发送时间")
    private Date createTime;

}
