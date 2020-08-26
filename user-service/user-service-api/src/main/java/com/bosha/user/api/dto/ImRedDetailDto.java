package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("红包详情")
public class ImRedDetailDto {
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 发红包人
     */
    @ApiModelProperty(value = "发红包人")
    private String userAddress;

    /**
     * 红包类型(1:个人红包; 2:群发手气红包 ;3:群发固定红包)
     */
    @ApiModelProperty(value = "红包类型(1:个人红包; 2:群发手气红包 ;3:群发固定红包)")
    private Integer type;

    /**
     * 红包数量
     */
    @ApiModelProperty(value = "红包数量")
    private Integer number;

    /**
     * 红包金额
     */
    @ApiModelProperty(value = "红包金额")
    private BigDecimal money;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String headImg;

    /**
     * 群id
     */
    @ApiModelProperty(value = "群id")
    private Long groupId;

    /**
     * 红包留言
     */
    @ApiModelProperty(value = "红包留言")
    private String remark;

    @ApiModelProperty(value = "状态,1等待加入，2，待支付，3，已支付,4，失效")
    private Integer status;

    @ApiModelProperty(value = "是否有支付按扭,1有，2没有")
    private Integer pay = 2;

    @ApiModelProperty(value = "已加入人数")
    private Integer joinNumber;

    @ApiModelProperty(value = "详情")
    private List<ImRedDetailReceiveDto> imRedDetailReceiveDtos;


}
