package com.bosha.common.api.service;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.commons.third.serverChan.ServerChanPush;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(CommonServiceConstants.SERVER_NAME)
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/serverChan")
public interface ServerChanService {

    @ApiOperation("通过server酱发送微信通知")
    @PostMapping("/push")
    void push(@RequestBody ServerChanPush push);

}
