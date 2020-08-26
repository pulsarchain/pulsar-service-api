package com.bosha.star.server.service.impl;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.callback.EditMediaCompleteEvent;
import com.bosha.star.api.dto.callback.LivePushAndDisconnectCallbackDto;
import com.bosha.star.api.dto.callback.LiveRecordCallbackDto;
import com.bosha.star.api.dto.callback.VideoEditMediaComplete;
import com.bosha.star.api.dto.server.ImGroupInfoResult;
import com.bosha.star.api.dto.server.ImGroupMember;
import com.bosha.star.api.dto.web.ImGroupPushMessage;
import com.bosha.star.api.dto.web.LiveRoomInfoDto;
import com.bosha.star.api.dto.web.MiningDto;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.api.entity.LiveMiningRecord;
import com.bosha.star.api.entity.LiveMiningVod;
import com.bosha.star.api.entity.LiveMiningVodConfirm;
import com.bosha.star.api.entity.StarMember;
import com.bosha.star.api.enums.LiveMiningChatRoomPushEnum;
import com.bosha.star.api.enums.LiveMiningTypeEnum;
import com.bosha.star.api.service.LiveMiningDetailService;
import com.bosha.star.api.service.StarService;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.bosha.star.server.mapper.LiveMiningRecordMapper;
import com.bosha.star.server.mapper.LiveMiningVodConfirmMapper;
import com.bosha.star.server.mapper.LiveMiningVodMapper;
import com.bosha.star.server.mapper.StarMapper;
import com.bosha.star.server.redis.CacheKey;
import com.bosha.star.server.utils.ImUtils;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MiningCalcServiceImpl
 * @Author liqingping
 * @Date 2020-03-26 16:15
 */
@RestController
@Slf4j
public class LiveMiningDetailServiceImpl implements LiveMiningDetailService {

    @Autowired
    private RedissonClient redis;
    @Autowired
    private StarService starService;
    @Autowired
    private LiveMiningRecordMapper liveMiningRecordMapper;
    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private StarMapper starMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ImUtils imUtils;
    @Autowired
    private LiveMiningVodMapper liveMiningVodMapper;
    @Autowired
    private LiveMiningVodConfirmMapper liveMiningVodConfirmMapper;

    @Override
    public void mining(MiningDto mining) {
        Date miningTime = new Date();
        String address = RequestContextUtils.getAddress();
        Long liveMiningId = mining.getLiveMiningId();
        LiveMiningTypeEnum typeEnum = mining.getType();
        String value = address + "_" + liveMiningId + "_" + typeEnum;
        RBucket<String> join = redis.getBucket(CacheKey.Live.USER_MINING.getKey() + value);
        if (join.isExists()) {
            log.debug("【挖矿行为】[{}]，address={}，当前{}分钟内已挖矿！", typeEnum, address, miningTime);
            return;
        }
        RBucket<LiveMining> open = redis.getBucket(CacheKey.Live.LIVE_MINING.getKey() + liveMiningId);
        if (!open.isExists() || open.get() == null) {
            log.debug("【挖矿行为】[{}]，直播id={}，还未开始或已结束，无法继续挖矿！", typeEnum, liveMiningId);
            return;
        }
        StarMember starMember = starService.getStarMemberFromCache(address);
        if (starMember == null || starMember.getStatus() != StarMember.STATUS_SUCCESS) {
            log.debug("【挖矿行为】[{}]，address={}，不是星系成员，无法挖矿", typeEnum, address);
            return;
        }
        LiveMining liveMining = open.get();
        if (liveMining == null) {
            log.debug("【挖矿行为】[{}]，直播挖矿已结束 id={}，无法继续挖矿！", typeEnum, liveMiningId);
            return;
        }
        if (address.equalsIgnoreCase(liveMining.getAddress())) {
            log.debug("【挖矿行为】[{}]，address={}，暂不支持主播自己挖矿", typeEnum, address);
            return;
        }
        if (miningTime.after(liveMining.getLiveEndTime())) {
            log.debug("【挖矿行为】[{}]，直播时间已到 id={}，无法继续挖矿！", typeEnum, liveMiningId);
            return;
        }
        DateTime startTime = DateUtil.parseDateTime(DateUtil.format(miningTime, StarServiceConstants.SECOND_FORMAT));
        DateTime endTime = DateUtil.offsetMinute(DateUtil.parseDateTime(DateUtil.format(miningTime, StarServiceConstants.SECOND_FORMAT)), 1);
        boolean exist = liveMiningRecordMapper.countByAddressAndTimeAndLiveMiningId(address, liveMiningId, typeEnum.getType(), startTime, endTime) > 0;
        if (exist) {
            log.debug("【挖矿行为】[{}]，address={}，id={}，当前[ {} - {} ]分钟内已挖矿！", typeEnum, address, liveMiningId, startTime, endTime);
            return;
        }
        if (typeEnum == LiveMiningTypeEnum.SHARE || typeEnum == LiveMiningTypeEnum.COMMENT) {
            if (!commonService.hasLeft(liveMining, typeEnum.getType())) {
                log.debug("【挖矿行为】[{}]，id={}，title={}，type={}，该类型已挖完，不再记录！！！", typeEnum, liveMiningId, liveMining.getTitle(), typeEnum);
                return;
            }
        } else {
            RBucket<Long> bucket = redis.getBucket(CacheKey.Live.LIVE_MINING_VIEW_LIKE_GIFT_CASH_OUT.getKey() + mining.getLiveMiningId());
            if (bucket.isExists())
                return;
        }
        join.set(value, CacheKey.Live.USER_MINING.getExpireTime(), CacheKey.Live.USER_MINING.getTimeUnit());
        User user = userService.getByAddress(address);
        LiveMiningRecord record = LiveMiningRecord.builder().remark(remark(user, starMember))
                .address(address).createTime(miningTime).liveMiningId(liveMiningId).status(0).type(typeEnum.getType()).starId(starMember.getStarId())
                .build();
        liveMiningRecordMapper.insertSelective(record);
        log.info("【挖矿行为】成功记录，address={}，id={}，type={}，耗时：{}", address, liveMiningId, typeEnum, System.currentTimeMillis() - miningTime.getTime());
    }

    @Override
    public LiveRoomInfoDto liveRoomInfo(Long id, boolean push) {
        LiveRoomInfoDto info = liveMiningMapper.liveRoomInfo(id);
        RBucket<ImGroupInfoResult> bucket = redis.getBucket(CacheKey.Live.LIVE_CHAT_ROOM_ONLINE_NUM.getKey() + id);
        if (bucket.isExists()) {
            ImGroupInfoResult groupInfo = bucket.get();
            info.setOnlineNum(groupInfo.getMemberNum());
            List<ImGroupMember> memberList = groupInfo.getMemberList();
            if (CollectionUtils.isNotEmpty(memberList)) {
                List<String> list = memberList.stream().map(ImGroupMember::getMember_Account).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(list)) {
                    List<String> headImgs = liveMiningMapper.headImgs(list);
                    info.setHeadImgs(headImgs);
                }
            }
        }
        if (push)
            return info;
        String address = RequestContextUtils.getAddress();
        StarMember starMember = starService.getStarMemberFromCache(address);
        if (starMember != null) {
            info.setCurrentUserStarLevel(starMember.getCurrentLevel());
            info.setCurrentUserStarName(starMapper.getStarName(starMember.getStarId()));
        }
        User user = userService.getByAddress(address);
        if (user != null) {
            info.setCurrentUserNickName(user.getNickName());
            info.setCurrentUserHeadImg(user.getHeadImg());
        }
        return info;
    }

    @Override
    public void startPush(LivePushAndDisconnectCallbackDto callback) {
        String streamId = callback.getStream_id();
        String[] split = streamId.split("_");
        String address = split[0];
        long liveMiningId = Long.parseLong(split[1]);
        LiveMining liveMining = liveMiningMapper.selectByPrimaryKey(liveMiningId);
        log.info("【直播 推流 回调】开始推流：address={}，id={}\n{}", address, liveMiningId, liveMining);
        RBucket<LiveMining> open = redis.getBucket(CacheKey.Live.LIVE_MINING.getKey() + liveMiningId);
        if (open.isExists()) {
            imUtils.pushNotice(liveMiningId, ImGroupPushMessage.builder().data("").type(LiveMiningChatRoomPushEnum.LIVE_START).build());
            return;
        }
        if (liveMining.getStatus() == LiveMining.STATUS_TO_BE_LIVE) {
            open.set(liveMining);
            open.expireAt(liveMining.getLiveEndTime().getTime() - 500);
            liveMiningMapper.updateByPrimaryKeySelective(LiveMining.builder()
                    .id(liveMiningId)
                    .actualLiveStartTime(new Date(Long.parseLong(callback.getEvent_time() + "000")))
                    .status(LiveMining.STATUS_LIVING)
                    .updateTime(new Date())
                    .build());
            log.info("【直播开始】开始直播了，live=[id={},addr={},title={},startTime={},endTime={},onlineNum={}" +
                            "\namount={},shareNum={},sharePul={},commentNum={},commentPul={},view={},createTime={}]",
                    liveMining.getId(), liveMining.getAddress(), liveMining.getTitle(), liveMining.getLiveStartTime(),
                    liveMining.getLiveEndTime(), liveMining.getOnlineNum(), liveMining.getAmount(), liveMining.getShareNum(), liveMining.getSharePul(),
                    liveMining.getCommentNum(), liveMining.getCommentPul(), liveMining.getView(), liveMining.getCreateTime());
            imUtils.pushNotice(liveMiningId, ImGroupPushMessage.builder().data("").type(LiveMiningChatRoomPushEnum.LIVE_START).build());
        }
    }

    @Override
    public void disconnect(LivePushAndDisconnectCallbackDto callback) {
        String stream_id = callback.getStream_id();
        String[] split = stream_id.split("_");
        String address = split[0];
        long liveMiningId = Long.parseLong(split[1]);
        Duration duration = Duration.ofMillis(Long.parseLong(callback.getPush_duration()));
        log.info("【直播 断流 回调】id={}，msg={}，本次直播时长={}", stream_id, callback.getErrorMsg(), duration);
        log.info("live={}", liveMiningMapper.selectByPrimaryKey(liveMiningId));
    }

    @Override
    public void record(LiveRecordCallbackDto callback) {
        LiveMiningVod build = LiveMiningVod.builder().build();
        build.setStreamId(callback.getStream_id());
        build.setChannelId(callback.getChannel_id());
        build.setFileId(callback.getFile_id());
        build.setFileFormat(callback.getFile_format());
        build.setStartTime(callback.getStart_time());
        build.setEndTime(callback.getEnd_time());
        build.setDuration(callback.getDuration());
        build.setFileSize(callback.getFile_size());
        build.setStreamParam(callback.getStream_param());
        build.setVideoUrl(callback.getVideo_url());
        build.setSign(callback.getSign());
        build.setT(callback.getT());
        build.setLiveMiningId(Long.parseLong(callback.getStream_id().split("_")[1]));
        build.setCreateTime(new Date());
        liveMiningVodMapper.insertSelective(build);
    }

    @Override
    public void editMediaComplete(VideoEditMediaComplete complete) {
        EditMediaCompleteEvent event = complete.getEditMediaCompleteEvent();
        String taskId = event.getTaskId();
        EditMediaCompleteEvent.Output output = event.getOutput();
        LiveMiningVodConfirm confirm = liveMiningVodConfirmMapper.getByTaskId(taskId);
        if (confirm == null) {
            log.error("【视频拼接完成回调】数据库记录不存在！");
            return;
        }
        confirm.setUpdateTime(new Date());
        confirm.setFileId(output.getFileId());
        confirm.setVideoUrl(output.getFileUrl());
        confirm.setFileType(output.getFileType());
        confirm.setStatus(2);
        liveMiningVodConfirmMapper.updateByPrimaryKeySelective(confirm);
        log.info("【视频拼接完成】live={}，回看列表视频拼接完成！！", confirm.getLiveMiningId());
    }

    private static String remark(User user, StarMember starMember) {
        String nickName = user.getNickName();
        String seatName = starMember.seatName();
        if (StringUtils.isBlank(nickName)) {
            nickName = user.getAddress().subSequence(0, 8).toString();
        } else {
            nickName = nickName.length() > 8 ? nickName.subSequence(0, 8).toString() : nickName;
        }
        return nickName + "（" + seatName + "）";
    }

}
