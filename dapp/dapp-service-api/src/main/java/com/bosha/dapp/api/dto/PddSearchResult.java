package com.bosha.dapp.api.dto;

import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: PddSearchResult
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-06-01 13:04
 */
@Data
@ApiModel("拼多多搜索返回值")
public class PddSearchResult<T> {

    @ApiModelProperty("拼多多搜索到的总数量")
    private Integer total_count;
    @ApiModelProperty("搜索的id")
    private String search_id;
    @ApiModelProperty("拼多多商品列表，具体字段含义参考：http://dwz.win/CVe ")
    private List<T> goods_list;
}
