package com.bosha.dapp.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.dapp.api.dto.ActivityCalendarDto;
import com.bosha.dapp.api.dto.ActivityDetailDto;
import com.bosha.dapp.api.dto.ActivityListDto;
import com.bosha.dapp.api.dto.ActivityQuery;
import com.bosha.dapp.api.entity.SparksActivity;
import com.bosha.dapp.api.entity.SparksActivityImg;
import com.bosha.dapp.api.entity.SparksActivityJoin;
import com.bosha.dapp.api.entity.SparksOrg;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.service.ActivityService;
import com.bosha.dapp.server.mapper.SparksActivityImgMapper;
import com.bosha.dapp.server.mapper.SparksActivityJoinMapper;
import com.bosha.dapp.server.mapper.SparksActivityMapper;
import com.bosha.dapp.server.mapper.SparksOrgMapper;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ActivityServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 14:51
 */
@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private SparksActivityMapper activityMapper;
    @Autowired
    private SparksActivityImgMapper activityImgMapper;
    @Autowired
    private SparksActivityJoinMapper activityJoinMapper;
    @Autowired
    private SparksOrgMapper orgMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private UserService userService;

    @Override
    public Long add(SparksActivity activity) {
        SparksOrg org = orgMapper.getByAddress(activity.getAddress());
        if (org == null)
            throw new BaseException(DappErrorMsgEnum.UN_APPLY_ORG);
        if (org.getStatus() != 3)
            throw new BaseException(DappErrorMsgEnum.APPLY_ORG_ING);
        activity.setCreateTime(new Date());
        activity.setUpdateTime(new Date());
        activityMapper.insertSelective(activity);
        List<SparksActivityImg> list = new ArrayList<>();
        activity.getImgs().forEach(s -> list.add(SparksActivityImg.builder().activityId(activity.getId()).url(s).build()));
        activityImgMapper.batchInsert(list);
        log.info("【走进星星】新增：{}", activity);
        push(activity);
        return activity.getId();
    }

    @Override
    public boolean updateHash(SparksActivity activity) {
        SparksActivity select = activityMapper.selectByPrimaryKey(activity.getId());
        if (select == null || StringUtils.isNotBlank(select.getHash()))
            return false;
        log.info("【走进星星】打币推送更新hash：{}", activity);
        select.setHash(activity.getHash());
        push(select);
        return activityMapper.updateByPrimaryKeySelective(activity) > 0;
    }

    @Override
    public PageInfo<ActivityListDto> list(ActivityQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        return PageInfo.of(activityMapper.list(RequestContextUtils.getAddress(), query.getName(), query.getHot()));
    }

    @Override
    public PageInfo<ActivityListDto> my(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(activityMapper.my(RequestContextUtils.getAddress()));
    }

    @Override
    public ActivityDetailDto detail(Long id) {
        SparksActivity select = activityMapper.selectByPrimaryKey(id);
        ActivityDetailDto detail = activityJoinMapper.detail(RequestContextUtils.getAddress(), id);
        detail.setActivity(select);
        if (select != null) {
            int diff = select.getNum() - detail.getJoinNum();
            detail.setLeft(Math.max(diff, 0));
            if (detail.getJoinNum() > select.getNum())
                detail.setJoinNum(select.getNum());
        }
        return detail;
    }

    @Override
    @RedissonDistributedLock(key = "@address", waitTime = 0, failStrategy = RedissonDistributedLock.FailStrategy.RETURN_NULL)
    public boolean join(SparksActivityJoin join) {
        SparksActivity select = activityMapper.selectByPrimaryKey(join.getActivityId());
        ActivityDetailDto detail = activityJoinMapper.detail(RequestContextUtils.getAddress(), join.getActivityId());
        if (select == null)
            return false;
        if (detail.getJoinNum() > select.getNum())
            throw new BaseException(DappErrorMsgEnum.PERSON_NUM_LIMIT);
        activityJoinMapper.insertSelective(join);
        return true;
    }

    @Override
    @RedissonDistributedLock(key = "@address", waitTime = 0, failStrategy = RedissonDistributedLock.FailStrategy.RETURN_NULL)
    public boolean cancel(SparksActivityJoin join) {
        return activityJoinMapper.deleteByAddressAndId(join.getAddress(), join.getActivityId(), join.getType()) > 0;
    }

    @Override
    public PageInfo<ActivityListDto> myFavorite(Integer type, Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        if (type == null || type == 3) {
            return PageInfo.of(activityMapper.myFavoriteAll(type, new Date()));
        }
        return PageInfo.of(activityMapper.myFavorite(type, new Date()));
    }

    @Override
    public List<ActivityCalendarDto> calendar(String address, String startTime, String endTime) {
        if (DateUtil.between(DateUtil.parse(startTime), DateUtil.parseDate(endTime), DateUnit.DAY, true) > 31) {
            log.error("【星星日历】时间超过限制：startTime={}，endTime={}", startTime, endTime);
            throw new BaseException("时间超过限制");
        }
        return activityMapper.calendar(address, startTime, endTime);
    }

    @Override
    public PageInfo<ActivityListDto> calendar(String address, String startTime, String endTime, Integer type, Page page) {
        if (DateUtil.parse(startTime).after(new Date()) || DateUtil.parse(startTime).before(DateUtil.parse("2020-06-01 00:00:00")))
            return PageInfo.of(new ArrayList<>());
        PageHelper.startPage(page.getPage(), page.getSize());
        if (type == 3)
            return PageInfo.of(activityMapper.calendarListExpire(RequestContextUtils.getAddress(), DateUtil.parseTime(startTime), new Date()));
        return PageInfo.of(activityMapper.calendarList(RequestContextUtils.getAddress(), type, startTime, endTime));
    }

    private void push(SparksActivity activity) {
        if (activity.getCreditScoreMin() != null && activity.getCreditScoreMax() != null && StringUtils.isNotBlank(activity.getHash())) {
            GlobalExecutorService.executorService.execute(() -> {
                User user = userService.getByAddress(activity.getAddress());
                PushMessageDetail detail = PushMessageDetail.builder()
                        .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.ACTIVITY_RELEASE.name())
                        .pushEnum(AliyunPushEnum.NOTICE)
                        .title(user.getNickName() + "创建了走进星星")
                        .content(user.getNickName() + "创建了走进星星，快来看看吧")
                        .data(activity.getId())
                        .build();
                commonService.pushByScoreRange(activity.getCreditScoreMin(), activity.getCreditScoreMax(), detail);
            });
        }
    }
}
