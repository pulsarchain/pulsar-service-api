package com.bosha.dapp.api.dto;

import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: TaoBaoSearchResult
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-06-01 10:59
 */
@Data
@ApiModel("淘宝搜索返回值 ")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaoBaoSearchResult<T> {

    @ApiModelProperty("淘宝返回的搜索到的总数量")
    private Long total_results;
    @ApiModelProperty("列表：具体字段含义参考 http://dwz.win/CU2 ")
    private List<T> result_list;
}
