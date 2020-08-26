package com.bosha.common.server.controller;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.MessageRequestDto;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.entity.PushMessage;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.annotation.NoLog;
import com.bosha.commons.config.EnvConfig;
import com.bosha.commons.controller.BaseController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MessageController
 * @Author liqingping
 * @Date 2019-12-12 13:31
 */
@RestController
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + "/message")
@Slf4j
@Api(tags = "通知消息")
public class MessageController extends BaseController {

    @Autowired
    private PushService pushService;

    @ApiOperation("根据id获取消息详情")
    @GetMapping("/{id}")
    Object getById(@ApiParam(value = "消息的id", required = true) @PathVariable("id") Long id) {
        return pushService.getById(id);
    }

    @ApiOperation("已读 ")
    @PostMapping("/read")
    boolean read(@ApiParam(value = "消息的id，如果id为空则会把所有通知标记为已读") @RequestParam(value = "id", required = false) Long id) {
        return pushService.read(id);
    }

    @ApiOperation("获取消息列表")
    @GetMapping("/list")
    PageInfo<PushMessage> list(@ApiParam(value = "") @ModelAttribute @Validated MessageRequestDto messageRequest) {
        return pushService.list(messageRequest);
    }

    @ApiOperation("获取未读消息的数量")
    @GetMapping("/unreadCount")
    @NoLog
    int unreadCount() {
        return pushService.unreadCount(getAddress());
    }

    @ApiOperation("测试推送")
    @PostMapping("/testPush")
    void testPush(@RequestBody PushMessageDetail pushMessageDetail) {
        if (EnvConfig.isProd())
            return;
        if (CollectionUtils.isEmpty(pushMessageDetail.getAddresses()))
            pushService.sendToAll(pushMessageDetail);
        else
            pushService.send(pushMessageDetail);
    }


}
