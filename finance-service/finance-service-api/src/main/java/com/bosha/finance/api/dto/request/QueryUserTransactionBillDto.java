package com.bosha.finance.api.dto.request;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class QueryUserTransactionBillDto extends Page {
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("币种Id")
    private Long coinId;
    @ApiModelProperty("排序字段:create_time,actual_number")
    private String sort;
    @ApiModelProperty("排序字段:asc升序,desc降序")
    private String desc;
}
