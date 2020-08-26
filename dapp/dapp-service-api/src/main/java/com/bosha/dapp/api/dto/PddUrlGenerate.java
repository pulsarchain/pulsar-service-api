package com.bosha.dapp.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: PddUrlGenerate
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-06-01 13:14
 */
@Data
@ApiModel("拼多多推广链接转换返回值")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PddUrlGenerate {
    @ApiModelProperty("唤醒拼多多app的推广长链接")
    private String mobile_url;
    @ApiModelProperty("唤醒拼多多app的schema的链接")
    private String schema_url;
}
