package com.bosha.star.server.job;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import com.bosha.star.api.dto.server.ImGroupInfoResult;
import com.bosha.star.api.dto.web.ImGroupPushMessage;
import com.bosha.star.api.dto.web.LiveRoomInfoDto;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.api.enums.LiveMiningChatRoomPushEnum;
import com.bosha.star.api.service.LiveMiningDetailService;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.bosha.star.server.mapper.LiveMiningRecordMapper;
import com.bosha.star.server.redis.CacheKey;
import com.bosha.star.server.utils.ImUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveNoticePushJob
 * @Author liqingping
 * @Date 2020-03-25 15:38
 */
@Component
@Slf4j
public class LiveRoomInfoJob {

    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private LiveMiningRecordMapper liveMiningRecordMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private ImUtils imUtils;
    @Autowired
    private LiveMiningDetailService liveMiningDetailService;

    @XxlJob("LiveRoomInfoJob")//每5s运行一次
    public ReturnT<String> roomInfo(String s) {
        try {
            Date date = new Date();
            List<LiveMining> liveMinings = liveMiningMapper.calcOnlineNum();
            for (LiveMining liveMining : liveMinings) {
                ImGroupInfoResult info = imUtils.groupInfo(liveMining.getId());
                Integer memberNum = info.getMemberNum();
                if (memberNum > liveMining.getOnlineNum()) {
                    liveMiningMapper.updateByPrimaryKeySelective(LiveMining.builder().id(liveMining.getId()).onlineNum(memberNum).build());
                }
                RBucket<ImGroupInfoResult> bucket = redis.getBucket(CacheKey.Live.LIVE_CHAT_ROOM_ONLINE_NUM.getKey() + liveMining.getId());
                bucket.set(info, CacheKey.Live.LIVE_CHAT_ROOM_ONLINE_NUM.getExpireTime(), CacheKey.Live.LIVE_CHAT_ROOM_ONLINE_NUM.getTimeUnit());
            }
            List<LiveMining> list = liveMiningMapper.calcMinerNum(date);
            for (LiveMining liveMining : list) {
                LiveMining update = LiveMining.builder().id(liveMining.getId()).build();
                int minerNum = liveMiningRecordMapper.countMinerNum(liveMining.getId());
                BigDecimal giveOut = liveMiningRecordMapper.giveOut(liveMining.getId(), null);
                update.setLeftAmount(liveMining.getAmount().subtract(giveOut));
                update.setMinerNum(minerNum);
                update.setUpdateTime(date);
                liveMiningMapper.updateByPrimaryKeySelective(update);
                LiveRoomInfoDto roomInfo = liveMiningDetailService.liveRoomInfo(liveMining.getId(), true);
                imUtils.pushNotice(liveMining.getId(), ImGroupPushMessage.builder().data(roomInfo).type(LiveMiningChatRoomPushEnum.LIVE_ROOM_INFO).build());
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ReturnT.FAIL;
        }
    }
}
