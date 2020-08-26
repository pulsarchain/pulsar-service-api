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
 * @DESCRIPTION: SparksStarIndexDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-29 16:01
 */
@Data
@ApiModel("首页")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparksStarIndexDto {

    @ApiModelProperty("帮扶星星数量")
    private Integer helpNum;
    @ApiModelProperty("点亮星星")
    private SparksStarListDto light;
    @ApiModelProperty("擦亮星星")
    private SparksStarListDto wipe;
    @ApiModelProperty("造星计划")
    private SparksStarListDto make;
}
