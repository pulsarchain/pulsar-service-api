package com.bosha.user.server.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.user.api.dto.ImRedSendMessage;
import com.bosha.user.api.dto.Message;
import com.bosha.user.api.entity.CreditScore;
import com.bosha.user.api.entity.ImRed;
import com.bosha.user.api.service.UserService;
import com.bosha.user.server.config.UserServiceConfig;
import com.bosha.user.server.mapper.CreditScoreMapper;
import com.bosha.user.server.mapper.ImRedMapper;
import com.bosha.user.server.mapper.ImRedReceiveMapper;
import com.bosha.user.server.mapper.UserMapper;
import com.bosha.user.server.redis.CacheKey;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.bosha.user.api.constants.UserServiceConstants.IM_RED_TOPIC;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @Author liqingping
 * @Date 2020-02-09 17:00
 */
@Service
@Slf4j
public class ImRedJob {
    @Autowired
    private ImRedMapper imRedMapper;
    @Autowired
    private ImRedReceiveMapper imRedReceiveMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @XxlJob("ImRedJob")//每5秒运行一次
    public ReturnT<String> sendRed(String s) throws Exception {
        List<ImRed> imReds = imRedMapper.selectByNotPayStatus();
        if (imReds.isEmpty()) {
            return ReturnT.SUCCESS;
        }
        log.info("当前未支付的红包个数为：{}", imReds.size());
        for (ImRed imRed : imReds) {
            if (imRed.getCreateTime().getTime() + imRed.getSeconds() <= System.currentTimeMillis()) {
                log.info("发红包的时间还没有到，当前红包Id:{}", imRed.getId());
                return ReturnT.SUCCESS;
            }
            //如果发红包的时间加上红包到期的秒数大于当前时间，证明时间已经到期，就直接发送红包推送消息
            List<ImRedSendMessage> imRedSendMessages = imRedReceiveMapper.selectByReadId(imRed.getId());
            if (imRedSendMessages.isEmpty()) {
                log.info("当前红包没有一个人加入领取，修改红包状态为失效,{}",imRed.getId());
                ImRed build = ImRed.builder().status(4).id(imRed.getId()).build();
                imRedMapper.updateByPrimaryKeySelective(build);
                return ReturnT.SUCCESS;
            }
            //如果该红包没有发送过消息提醒
            if (imRed.getNotify().equals(1)){
                log.info("红包id为：{}，领取人数为：{}",imRed.getId(),imRedSendMessages.size());
                ImRed build = ImRed.builder().status(2).id(imRed.getId()).build();
                imRedMapper.updateByPrimaryKeySelective(build);
                log.info("发送消息给当前人，修改红包状态为已发送提示消息,{}",imRed.getId());
                Message message = new Message();
                if (imRed.getType().equals(1)) {
                    message.setType("chat");
                    message.setTo(imRed.getUserAddress());
                } else {
                    message.setType("groupchat");
                    message.setTo(imRed.getGroupId().toString());
                    message.setReceive(imRed.getUserAddress());
                }
                message.setMsgId(UUID.randomUUID().toString());
                message.setFrom(imRed.getUserAddress());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("red", imRed.getId());
                jsonObject.put("redlist", imRedSendMessages);
                message.setBody(jsonObject);
                rocketMQTemplate.syncSend(IM_RED_TOPIC, message);
                log.info("发送消息到聊天服务：{}", message);
            }

        }
        return ReturnT.SUCCESS;
    }

}
