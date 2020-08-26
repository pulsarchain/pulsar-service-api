package com.bosha.finance.api.dto.request;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class QueryCoinDto extends Page {
    @ApiModelProperty(value="英文名")
    private String cnName;
    @ApiModelProperty(value="币种")
    private String symbolName;
    @ApiModelProperty(value="状态：上下架状态：0 下架，1 上架")
    private Integer status;
    @ApiModelProperty(value="公链Id")
    private Integer typeId;
}
