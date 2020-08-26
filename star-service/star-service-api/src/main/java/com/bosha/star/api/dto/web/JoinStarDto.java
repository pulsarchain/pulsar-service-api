package com.bosha.star.api.dto.web;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: JoinMinerPoolResultDto
 * @Author liqingping
 * @Date 2019-08-01 14:15
 */
@Data
@ApiModel("加入星系")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinStarDto implements Serializable {
    private static final long serialVersionUID = -4664431957024023050L;

    @ApiModelProperty(value = "星系id",required = true)
    @NotNull(message = "星系id不可为空")
    private Long id;
    @ApiModelProperty(value = "交易hash", required = true)
    @NotBlank(message = "hash不可为空")
    private String hash;
    @ApiModelProperty(value = "推荐人地址")
    private String recommendAddress;
    @ApiModelProperty(value = "加入的费用",required = true)
    @NotNull(message = "金额不可为空")
    private BigDecimal value;
}
