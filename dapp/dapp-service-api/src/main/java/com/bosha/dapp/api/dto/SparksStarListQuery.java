package com.bosha.dapp.api.dto;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: SparksStarListQuery
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 8:47
 */
@Data
@ApiModel("查询列表")
public class SparksStarListQuery extends Page {

    @ApiModelProperty("类型：1 点亮，2 擦亮，3 造星")
    private Integer type;
    @ApiModelProperty("机构的地址")
    private String address;
}
