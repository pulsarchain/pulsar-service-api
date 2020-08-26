package com.bosha.user.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-bosha-user-api-entity-ImRed")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImRed {
    /**
     * 自增id
     */
    @ApiModelProperty(value = "自增id")
    private Long id;

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

    /**
     * 红包币种(1:PUL)
     */
    @ApiModelProperty(value = "红包币种(1:PUL)")
    private Byte currency;

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

    /**
     * 是否支付，1,待加入，2未支付，3已支付，4已失效
     */
    @ApiModelProperty(value = "是否支付，1,待加入，2未支付，3已支付，4已失效")
    private Integer status;

    /**
     * 是否已经推送过消息：1未通知，2已通知
     */
    @ApiModelProperty(value = "是否已经推送过消息：1未通知，2已通知")
    private Integer notify;

    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private Date createTime;

    /**
     * 自动发红包时间
     */
    @ApiModelProperty(value = "自动发红包时间")
    private Long seconds;

    public static ImRedBuilder builder() {
        return new ImRedBuilder();
    }
}