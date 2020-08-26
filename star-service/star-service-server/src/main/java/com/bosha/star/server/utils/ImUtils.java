package com.bosha.star.server.utils;

import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bosha.commons.config.EnvConfig;
import com.bosha.commons.third.weixinpush.WeixinPush;
import com.bosha.commons.third.weixinpush.autoconfiguration.WeixinPushProperties;
import com.bosha.commons.utils.HttpUtils;
import com.bosha.commons.utils.UUIDUtils;
import com.bosha.star.api.dto.server.ImGroupInfo;
import com.bosha.star.api.dto.server.ImGroupInfoResult;
import com.bosha.star.api.dto.server.ImGroupMember;
import com.bosha.star.api.dto.web.ImGroupPushMessage;
import com.bosha.star.server.config.StarServiceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ImUtils
 * @Author liqingping
 * @Date 2020-04-02 15:27
 */
@Slf4j
@Component
public class ImUtils {

    private static final String CREATE_GROUP = "/room/create";

    private static final String GROUP_INFO = "/room/info";

    private static final String GROUP_SYSTEM_NOTICE = "/room/send";

    @Autowired
    private StarServiceConfig starServiceConfig;
    @Autowired
    private WeixinPushProperties weixinPushProperties;

    public void createGroup(Long id, Long expireTime) {
        JSONObject post = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roomId", id);
            jsonObject.put("expire", expireTime);
            post = HttpUtils.post(starServiceConfig.getLiveConfig().getImDomain() + CREATE_GROUP, jsonObject.toJSONString(), JSONObject.class);
            if (post.getInteger("returnCode") != 200) {
                log.error("【创建聊天群组】失败，id={},result={}", id, post);
                throw new RuntimeException(post.toJSONString());
            } else
                log.info("【创建聊天群组】成功！result={}", post);
        } catch (Exception e) {
            if (EnvConfig.isProd())
                WeixinPush.push(weixinPushProperties, "【创建聊天群组】失败，id=" + id + "\n" + post);
            throw new RuntimeException(e);
        }
    }

    public ImGroupInfoResult groupInfo(Long id) {
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpUtils.get(starServiceConfig.getLiveConfig().getImDomain() + GROUP_INFO + "?roomId=" + id, JSONObject.class);
            if (jsonObject.getInteger("returnCode") != 200) {
                log.error("【创建聊天群组】失败，id={},result={}", id, jsonObject);
                throw new RuntimeException(jsonObject.toJSONString());
            } else {
                ImGroupInfoResult result = new ImGroupInfoResult();
                ImGroupInfo data = jsonObject.getObject("data", ImGroupInfo.class);
                if (data != null) {
                    result.setMemberNum(data.getNumber());
                    List<ImGroupMember> members = new ArrayList<>();
                    for (String address : data.getAddress()) {
                        members.add(ImGroupMember.builder().Member_Account(address).build());
                    }
                    result.setMemberList(members);
                }
                return result;
            }
        } catch (Exception e) {
            if (EnvConfig.isProd())
                WeixinPush.push(weixinPushProperties, "【获取聊天群组信息】失败，id=" + id + "\n" + jsonObject);
            throw new RuntimeException(e);
        }
    }

    public void pushNotice(Long id, ImGroupPushMessage pushMessage) {
        pushMessage.setLiveMiningId(id);
        log.debug("【聊天室消息推送】id={}，type={}，msg={}", id, pushMessage.getType(), JSON.toJSONString(pushMessage.getData()));
        JSONObject post = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msgId", UUIDUtils.getUUID());
            jsonObject.put("roomId", String.valueOf(id));
            jsonObject.put("messageType", 1);
            jsonObject.put("content", JSON.toJSONString(pushMessage));
            post = HttpUtils.post(starServiceConfig.getLiveConfig().getImDomain() + GROUP_SYSTEM_NOTICE, jsonObject.toJSONString(),JSONObject.class);
            if (post.getInteger("returnCode") != 200)  {
                log.error("【聊天室推送】失败，id={},result={}", id, post);
                throw new RuntimeException(post.toJSONString());
            }
        } catch (Exception e) {
            if (EnvConfig.isProd())
                WeixinPush.push(weixinPushProperties, "【聊天室推送】失败，id=" + id + "\n" + post);
            throw new RuntimeException(e);
        }
    }


}
