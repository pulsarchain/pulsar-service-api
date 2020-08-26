package com.bosha.dapp.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappSlideshowDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-18 10:07
 */
@Data
@ApiModel("dapp首页轮播图")
public class DappSlideshowDto {

    @ApiModelProperty(value="名称")
    private String name;

    @ApiModelProperty(value="链接地址")
    private String url;

    @ApiModelProperty(value="关联的dapp_id（不一定有值）")
    private Long dappId;

    @ApiModelProperty(value="类型（暂不确定）")
    private Integer type;
}
