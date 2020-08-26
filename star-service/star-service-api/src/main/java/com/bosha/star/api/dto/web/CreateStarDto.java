package com.bosha.star.api.dto.web;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CreateStarDto
 * @Author liqingping
 * @Date 2020-02-18 15:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("创建星系")
public class CreateStarDto implements Serializable {
    private static final long serialVersionUID = -939539845113397706L;

    @ApiModelProperty(value = "星系名称，最长20", required = true)
    @NotBlank(message = "名称不可为空")
    @Size(max = 200, message = "名称最长200")
    private String name;
    @ApiModelProperty(value = "宣言，最长200", required = true)
    @NotBlank(message = "宣言不可为空")
    @Size(max = 200, message = "宣言最长200")
    private String slogan;
    @ApiModelProperty(value = "logo", required = true)
    @NotBlank(message = "logo不可为空")
    private String logo;
    @ApiModelProperty(value = "to地址/系统地址", required = true)
    @NotBlank(message = "to地址不可为空")
    private String systemAddress;
    @ApiModelProperty(value = "交易hash", required = true)
    @NotBlank(message = "hash不可为空")
    private String hash;
    @ApiModelProperty(value = "加入的费用",required = true)
    @NotNull(message = "金额不可为空")
    private BigDecimal value;
}
