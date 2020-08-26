package com.bosha.dapp.api.dto;

import com.bosha.dapp.api.entity.DappCategories;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappCategoriesDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 10:37
 */
@Data
@ApiModel("dapp分类")
public class DappCategoriesDto extends DappCategories {

    @ApiModelProperty("分类使用数量")
    private Integer count = 0;
}
