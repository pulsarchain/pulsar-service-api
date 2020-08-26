package com.bosha.dapp.api.dto;

import com.bosha.dapp.api.entity.SparksOrg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: OrgDetailDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 13:14
 */
@Data
@ApiModel("机构详情")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrgDetailDto {

    @ApiModelProperty("机构")
    private SparksOrg org;
    @ApiModelProperty("点亮")
    private SparksStarListDto light;
    @ApiModelProperty("擦亮")
    private SparksStarListDto wipe;
    @ApiModelProperty("造星计划")
    private SparksStarListDto make;
    @ApiModelProperty("活动")
    private ActivityListDto activity;
}
