package com.bosha.star.server.job;

import java.util.Collections;
import java.util.Date;
import java.util.List;


import cn.hutool.core.date.DateUtil;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.utils.StrUtils;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class LiveNoticePushJob {

    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private PushService pushService;

    @XxlJob("LiveNoticeStartPushJob")//每5分钟运行一次
    public ReturnT<String> startNotice(String s) {
        try {
            int value = 5;
            if (StringUtils.isNumeric(s))
                value = Integer.parseInt(s);
            if (value < 5)
                value = 5;
            List<LiveMining> list = liveMiningMapper.pushStartNoticeList(
                    DateUtil.offsetMinute(DateUtil.parseDateTime(DateUtil.format(new Date(), StarServiceConstants.SECOND_FORMAT)), value));
            list.forEach(liveMining -> {
                pushService.send(PushMessageDetail.builder()
                        .type(PushMessageTypeEnum.SYSTEM).subType(PushMessageSubTypeEnum.System.LIVE_NOTICE_START.name())
                        .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(liveMining.getAddress()))
                        .title("您创建的直播" + StrUtils.cutStr(liveMining.getTitle(), 8) + "快开始了").db(true)
                        .content("快去准备一下吧！").data(liveMining.getId())
                        .build());
                log.info("【开播提醒】直播开始时间马上到了： {}", liveMining);
            });
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ReturnT.FAIL;
        }
    }
}
