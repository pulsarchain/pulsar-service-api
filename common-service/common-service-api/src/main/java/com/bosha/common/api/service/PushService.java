package com.bosha.common.api.service;

import java.util.List;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.MessageRequestDto;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.entity.PushMessage;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(CommonServiceConstants.SERVER_NAME)
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/push")
public interface PushService {

    @ApiOperation("发送推送")
    @PostMapping("/send")
    void send(@RequestBody @Validated PushMessageDetail pmd);

    @ApiOperation("发送推送给所有用户")
    @PostMapping("/sendToAll")
    void sendToAll(@RequestBody PushMessageDetail pmd);

    @ApiOperation("根据id获取消息详情")
    @GetMapping("/{id}")
    Object getById(@PathVariable("id") Long id);

    @ApiOperation("根据id修改消息详情")
    @PostMapping("/update")
    void update(@RequestBody PushMessage pushMessage);

    @ApiOperation("获取消息列表")
    @GetMapping("/list")
    PageInfo<PushMessage> list(@ModelAttribute @Validated MessageRequestDto messageRequest);

    @ApiOperation("消息已读")
    @PostMapping("/read")
    boolean read(@RequestParam(value = "id", required = false) Long id);

    @ApiOperation("获取未读消息的数量")
    @GetMapping("/unreadCount")
    int unreadCount(@RequestParam("address") String address);

    @ApiOperation("")
    @GetMapping("/listByType")
    List<PushMessage> listByType(@RequestParam("address") String address, @RequestParam("type") PushMessageTypeEnum type, @RequestParam("subType") String subType);
}
