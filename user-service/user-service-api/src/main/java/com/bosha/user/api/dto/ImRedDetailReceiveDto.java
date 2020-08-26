package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("红包详情")
public class ImRedDetailReceiveDto {

    /**
     * 领取人地址
     */
    @ApiModelProperty(value = "领取红包人")
    private String userAddress;

    @ApiModelProperty(value = "领取人hash")
    private String hash;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String headImg;
    /**
     * 红包金额
     */
    @ApiModelProperty(value = "红包金额")
    private BigDecimal money;

    @ApiModelProperty(value = "加入时间")
    private Date createTime;

}
