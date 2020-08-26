package com.bosha.common.api.dto;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MessageRequestDto
 * @Author liqingping
 * @Date 2019-12-12 11:56
 */

@Data
@ApiModel
public class MessageRequestDto extends Page {

    @ApiModelProperty("系统：SYSTEM，价格：PRICE，星系：STAR，公链：CHAIN，DAPP DAPP")
    private String type;

    @ApiModelProperty("0 未读，1 已读")
    private Integer status;
}
