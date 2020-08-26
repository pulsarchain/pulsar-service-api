package com.bosha.common.api.service;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.Sms;
import com.bosha.common.api.enums.SmsTypeEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(CommonServiceConstants.SERVER_NAME)//服务名
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/sms")//内部服务前缀
public interface SmsService {

    @ApiOperation("检验短信验证是否正确")
    @GetMapping("/verify")
    boolean verify(@RequestParam("phone") String phone, @RequestParam("code") String code);

    @ApiOperation("检验短信验证是否正确")
    @GetMapping("/verifyWithType")
    boolean verify(@RequestParam("phone") String phone, @RequestParam("type") SmsTypeEnum type, @RequestParam("code") String code);

    @ApiOperation("发送短信")
    @PostMapping("/send")
    boolean send(@RequestBody @Validated Sms sms);
}
