package com.bosha.star.server.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.alibaba.fastjson.JSON;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.utils.ReadRequestBodyUtils;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.callback.LivePushAndDisconnectCallbackDto;
import com.bosha.star.api.dto.callback.LiveRecordCallbackDto;
import com.bosha.star.api.dto.callback.VideoEditMediaComplete;
import com.bosha.star.api.service.LiveMiningDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningController
 * @Author liqingping
 * @Date 2020-03-25 12:17
 */
@Api(tags = "直播挖矿腾讯云回调")
@Slf4j
@RestController
@RequestMapping(StarServiceConstants.WEB_PRIFEX + "/liveMining/callback")
public class LiveMiningCallbackController extends BaseController {

    @Autowired
    private LiveMiningDetailService liveMiningDetailService;

    private static Map<String, Object> result;

    static {
        result = new HashMap<>();
        result.put("code", 0);
    }

    @ApiOperation("开始推流")
    @PostMapping("/startPush")
    Map<String, Object> startPush(@RequestBody LivePushAndDisconnectCallbackDto callback) {
        log.info("【直播回调】推流 --> streamId={}，msg={}，{}", callback.getStream_id(), callback.getErrorMsg(), callback);
        if (callback.correctStreamId())
            liveMiningDetailService.startPush(callback);
        return result;
    }

    @ApiOperation("断流")
    @PostMapping("/disconnect")
    Map<String, Object> disconnect(@RequestBody LivePushAndDisconnectCallbackDto callback) {
        log.info("【直播回调】断流 --> streamId={}，msg={}， {}", callback.getStream_id(), callback.getErrorMsg(), callback);
        if (callback.correctStreamId())
            liveMiningDetailService.disconnect(callback);
        return result;
    }

    @ApiOperation("录制")
    @PostMapping("/record")
    Map<String, Object> record(@RequestBody LiveRecordCallbackDto callback) {
        log.info("【直播回调】录制 --> streamId={}， {}", callback.getStream_id(), callback);
        if (callback.correctStreamId())
            liveMiningDetailService.record(callback);
        return result;
    }

    @ApiOperation("视频拼接完成")
    @PostMapping("/editMediaComplete")
    Map<String, Object> editMediaComplete(HttpServletRequest request) {
        String body = ReadRequestBodyUtils.readBody(request);
        log.info("【视频拼接完成回调】  原始body={}", body);
        VideoEditMediaComplete complete = JSON.parseObject(body, VideoEditMediaComplete.class);
        log.info("【视频拼接完成回调】  {}", complete);
        liveMiningDetailService.editMediaComplete(complete);
        return result;
    }
}
