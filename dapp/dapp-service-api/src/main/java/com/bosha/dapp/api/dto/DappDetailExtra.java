package com.bosha.dapp.api.dto;

import java.util.List;


import com.bosha.dapp.api.entity.DappWitness;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappDetailExtra
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 14:06
 */
@Data
@ApiModel("dapp详情额外信息")
@Builder
public class DappDetailExtra {

    @ApiModelProperty("举报数量")
    private Integer report;

    @ApiModelProperty("见证记录列表")
    private List<DappWitness> witnessList;
}
