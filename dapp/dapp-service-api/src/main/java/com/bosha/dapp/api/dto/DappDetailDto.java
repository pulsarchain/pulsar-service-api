package com.bosha.dapp.api.dto;

import java.io.Serializable;


import com.bosha.dapp.api.entity.Dapp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappDetailDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 12:16
 */
@Data
@ApiModel("dapp")
public class DappDetailDto implements Serializable {

    private static final long serialVersionUID = 6948281550897940517L;

    @ApiModelProperty("dapp")
    private Dapp dapp;

    @ApiModelProperty("自己是该dapp的创建者才返回值，其他时候返回null")
    private DappDetailExtra extra;
}
