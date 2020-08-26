package com.bosha.common.server.controller;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.entity.PushConfig;
import com.bosha.common.api.service.PushConfigService;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.utils.RequestContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MessageController
 * @Author liqingping
 * @Date 2019-12-12 13:31
 */
@RestController
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + "/pushConfig")
@Slf4j
@Api(tags = "推送配置")
public class PushConfigController extends BaseController {

    @Autowired
    private PushConfigService pushConfigService;

    @ApiOperation("更改用户推送配置")
    @PostMapping("/update")
    boolean update(@RequestBody   PushConfig pushConfig) {
        pushConfig.setUserId(RequestContextUtils.getUserId());
        pushConfig.setAddress(getAddress());
        return pushConfigService.update(pushConfig);
    }

    @ApiOperation("获取推送配置")
    @GetMapping
    PushConfig getByAddress() {
        return pushConfigService.getByAddress(RequestContextUtils.getRequestInfo().getAddress());
    }
}
