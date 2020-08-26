package com.bosha.dapp.api.dto;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: OrgQuery
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 12:18
 */
@Data
@ApiModel("机构查询")
public class OrgQuery extends Page {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("分类")
    private String category;
}
