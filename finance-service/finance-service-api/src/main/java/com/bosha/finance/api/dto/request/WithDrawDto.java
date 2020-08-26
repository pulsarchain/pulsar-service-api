package com.bosha.finance.api.dto.request;

import cn.hutool.db.DaoTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class WithDrawDto implements Serializable {
    private Long id;
    @ApiModelProperty("操作人")
    private String operator;
    @ApiModelProperty("状态：1 进行中，2 已完成 ，3 失败，4 审核中，5 已撤销，6 未通过，7 审核通过")
    private Integer status;
    @ApiModelProperty("原因")
    private String auditReason;
    @ApiModelProperty("审核时间")
    private Date auditTime;
}
