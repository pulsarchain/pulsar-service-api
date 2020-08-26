package com.bosha.dapp.api.dto;

import java.util.List;


import com.bosha.dapp.api.entity.SparksFundation;
import com.bosha.dapp.api.entity.SparksWitness;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: FundationDetailDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 10:36
 */
@Data
@ApiModel("基金详情")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundationDetailDto {

    @ApiModelProperty("基金")
    private SparksFundation fundation;
    @ApiModelProperty("见证记录")
    private List<SparksWitness> witness;
}
