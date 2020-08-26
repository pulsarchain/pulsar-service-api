package com.bosha.star.api.dto.web;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: BuyGiftDto
 * @Author liqingping
 * @Date 2020-03-25 21:20
 */
@Data
@ApiModel("购买礼物")
public class BuyGiftDto {

    @ApiModelProperty(value = "礼物id", required = true)
    @NotNull(message = "礼物id不可为空")
    private Long giftId;
    @ApiModelProperty(value = "购买数量", required = true)
    @NotNull(message = "购买数量不可为空")
    @Min(value = 1, message = "最小值为1")
    private Integer num;
    @ApiModelProperty(value = "总金额", required = true)
    @NotNull(message = "总金额不可为空")
    private BigDecimal amount;
    @ApiModelProperty(value = "hash", required = true)
    @NotBlank(message = "打币hash不可为空")
    private String hash;
    @ApiModelProperty(value = "打币地址", required = true)
    @NotBlank(message = "系统打币地址不可为空")
    private String systemAddress;
}
