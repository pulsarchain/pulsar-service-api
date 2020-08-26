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
@ApiModel("待支付红包")
public class ImRedList {

    @ApiModelProperty("id")
    private Long id;

    /**
     * 发红包人
     */
    @ApiModelProperty(value="发红包人")
    private String userAddress;

    /**
     * 红包类型(1:个人红包; 2:群发手气红包 ;3:群发固定红包)
     */
    @ApiModelProperty(value="红包类型(1:个人红包; 2:群发手气红包 ;3:群发固定红包)")
    private Integer type;

    /**
     * 红包数量
     */
    @ApiModelProperty(value="红包数量")
    private Integer number;

    /**
     * 红包金额
     */
    @ApiModelProperty(value="红包金额")
    private BigDecimal money;

    /**
     * 群id
     */
    @ApiModelProperty(value="群id")
    private Long groupId;

    /**
     * 红包留言
     */
    @ApiModelProperty(value="红包留言")
    private String remark;

    @ApiModelProperty(value = "加入的人")
    private List<ImRedSendMessage> imRedSendMessages;

}
