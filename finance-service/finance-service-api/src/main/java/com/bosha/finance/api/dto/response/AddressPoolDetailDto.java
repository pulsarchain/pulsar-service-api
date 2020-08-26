package com.bosha.finance.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("地址池使用详情")
public class AddressPoolDetailDto implements Serializable {
    private Long id;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("状态：0 未使用，1 已使用")
    private Integer status;
}
