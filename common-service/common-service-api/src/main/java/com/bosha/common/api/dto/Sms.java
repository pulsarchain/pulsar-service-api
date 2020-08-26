package com.bosha.common.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;


import com.bosha.common.api.enums.SmsTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: Sms
 * @Author liqingping
 * @Date 2019-04-14 15:30
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sms implements Serializable {
    private static final long serialVersionUID = -8756438985062581800L;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不可为空")
    private String phone;
    @ApiModelProperty("短信内容")
    @NotBlank(message = "短信内容不可为空")
    private String content;
    @ApiModelProperty("短信类型")
    private SmsTypeEnum smsType = SmsTypeEnum.COMMON;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty("定时发送短信时间。格式为：yyyyMMddHHmm，值小于或等于当前时间则立即发送，默认立即发送，选填")
    private String sendTime;
    @ApiModelProperty("该条短信在您业务系统内的ID，如订单号或者短信发送记录流水号，选填")
    private String uid;
}
