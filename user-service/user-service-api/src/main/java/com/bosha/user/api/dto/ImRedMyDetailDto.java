package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("我的发送和接收红包")
public class ImRedMyDetailDto {
    /**
     * 发红包人
     */
    @ApiModelProperty(value = "发红包人")
    private String userAddress;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String headImg;
    /**
     * 红包数量
     */
    @ApiModelProperty(value = "发送红包数量")
    private Integer sendNumber;
    /**
     * 红包数量
     */
    @ApiModelProperty(value = "接收红包数量")
    private Integer receiveNumber;

    /**
     * 红包金额
     */
    @ApiModelProperty(value = "红包金额")
    private BigDecimal money;

    @ApiModelProperty(value = "接收明细")
    private List<ImMyReceiveRedDto> imMyReceiveRedDtos;

    @ApiModelProperty(value = "发送明细")
    private List<ImMySendRedDto> imMySendRedDtos;

}
