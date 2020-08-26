package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("地址池列表")
public class AddressPoolListDto implements Serializable {
    private Integer id;
    @ApiModelProperty("币种中文名")
    private String cnName;
    @ApiModelProperty("币种简称")
    private String symbolName;
    @ApiModelProperty("可用数量")
    private Integer number;


}
