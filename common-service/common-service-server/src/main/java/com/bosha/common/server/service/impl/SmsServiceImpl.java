package com.bosha.common.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.bosha.common.api.dto.Sms;
import com.bosha.common.api.dto.SmsSendRequest;
import com.bosha.common.api.dto.SmsSendResponse;
import com.bosha.common.api.enums.SmsTypeEnum;
import com.bosha.common.api.service.SmsService;
import com.bosha.common.server.config.CommonServiceConfig;
import com.bosha.common.server.redis.CacheKey;
import com.bosha.commons.config.EnvConfig;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.HttpUtils;
import com.bosha.commons.utils.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SmsServiceImpl
 * @Author liqingping
 * @Date 2019-04-13 12:36
 */
@Slf4j
@RestController
public class SmsServiceImpl implements SmsService {

    @Autowired
    private CommonServiceConfig commonsServiceConfig;
    @Autowired
    private RedissonClient redis;

    private boolean noVerify() {
        return !EnvConfig.isProd() && Boolean.TRUE.equals(commonsServiceConfig.getSms().getNoVerify());
    }

    @Override
    public boolean verify(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        return verify(phone, SmsTypeEnum.COMMON, code);
    }

    @Override
    public boolean verify(@RequestParam("phone") String phone, @RequestParam("type") SmsTypeEnum type, @RequestParam("code") String code) {
        if (!SmsUtils.verifyPhone(phone))
            throw new BaseException("错误的手机号");
        if (noVerify())
            return true;
        RBucket<String> bucket = getCodeBucket(type, phone);
        return bucket.isExists() && code.equals(bucket.get());
    }

    @Override
    public boolean send(@RequestBody @Validated Sms sms) {
        if (!SmsUtils.verifyPhone(sms.getPhone()))
            throw new BaseException("错误的手机号");
        if (StringUtils.isNotBlank(sms.getCode())) {
            RBucket<String> codeBucket = getCodeBucket(sms.getSmsType(), sms.getPhone());
            if (codeBucket.isExists() && codeBucket.remainTimeToLive() > 0) {
                long overTime = (CacheKey.Common.CODE_BUCKET.timeUnit.toMillis(CacheKey.Common.CODE_BUCKET.expireTime) - codeBucket.remainTimeToLive()) / 1000;
                if (overTime < 59)
                    throw new BaseException("请在" + (60 - overTime) + "秒后重新获取");
            }
            codeBucket.set(sms.getCode(), CacheKey.Common.CODE_BUCKET.expireTime, CacheKey.Common.CODE_BUCKET.timeUnit);
        }
        CommonServiceConfig.Sms smsComfig = commonsServiceConfig.getSms();
        SmsSendRequest sendRequest = SmsSendRequest.builder().account(smsComfig.getAccount()).password(smsComfig.getPassword())
                .msg(sms.getContent()).phone(sms.getPhone()).sendtime(sms.getSendTime()).uid(sms.getUid()).build();
        String data = JSON.toJSONString(sendRequest);
        try {
            SmsSendResponse response = HttpUtils.post(smsComfig.getUrl(), data, SmsSendResponse.class);
            if (StringUtils.isNotBlank(response.getErrorMsg()))
                throw new RuntimeException(response.getCode() + "-" + response.getErrorMsg());
        } catch (Exception e) {
            log.error("短信发送失败：dto={} ：\n{}", data, e.getMessage());
            return false;
        }
        log.info("发送短信成功：{}", JSON.toJSONString(sms));
        return true;
    }

    private RBucket<String> getCodeBucket(SmsTypeEnum typeEnum, String phone) {
        return redis.getBucket(CacheKey.Common.CODE_BUCKET.key + typeEnum.getType() + ":" + phone);
    }
}
