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
 * @DESCRIPTION: DappSystemConfigInfo
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-27 14:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("")
public class DappSystemConfigInfo {

    @ApiModelProperty("系统打币地址")
    private String systemAddress ;
}
