package com.bosha.dapp.api.dto;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappListQuery
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 13:24
 */
@Data
@ApiModel("分类列表查询")
public class DappListQuery extends Page {

    @ApiModelProperty("dapp名称")
    private String name;
    @ApiModelProperty("类型id")
    private Long categoryId;
}
