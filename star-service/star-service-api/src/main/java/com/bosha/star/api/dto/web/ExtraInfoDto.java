package com.bosha.star.api.dto.web;

import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ExtraInfoDto
 * @Author liqingping
 * @Date 2020-03-05 17:18
 */
@Data
@ApiModel("-")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtraInfoDto {

    @ApiModelProperty("星系给系统打币的地址")
    private String systemAddress;
    @ApiModelProperty("加入状态：null 未加入和创建任何星系，0 区块确认中，1 加入成功")
    private Integer join;
    @ApiModelProperty("不同级别对应的加入的金额")
    private Num num;

    @Data
    @ApiModel("")
    public static class Num {
        @ApiModelProperty("脉冲星")
        private BigDecimal star = BigDecimal.valueOf(1000);
        @ApiModelProperty("脉冲双星")
        private BigDecimal doubleStar = BigDecimal.valueOf(3000);
        @ApiModelProperty("小绿人")
        private BigDecimal greenMan = BigDecimal.valueOf(5000);
    }
}
