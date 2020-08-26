package com.bosha.dapp.server.job;

import java.util.Collections;
import java.util.Date;
import java.util.List;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.dapp.api.entity.Dapp;
import com.bosha.dapp.api.entity.DappReport;
import com.bosha.dapp.api.enums.DappReportEnum;
import com.bosha.dapp.api.enums.DappStatusEnum;
import com.bosha.dapp.server.mapper.DappMapper;
import com.bosha.dapp.server.mapper.DappReportMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappReportJob
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-13 14:30
 */
@Component
@Slf4j
public class DappReportJob {

    @Autowired
    private PushService pushService;
    @Autowired
    private DappReportMapper dappReportMapper;
    @Autowired
    private DappMapper dappMapper;

    @XxlJob("DappReportOverPublicDate")//每5分钟一次
    public ReturnT<String> publicDate(String s) throws Exception {
        List<DappReport> list = dappReportMapper.publicDateJob(new Date());
        if (CollectionUtils.isEmpty(list))
            return ReturnT.SUCCESS;
        for (DappReport dappReport : list) {
            dappReport.setUpdateTime(new Date());
            dappReport.setStatus(DappReportEnum.OVER_PUBLIC_TIME.getStatus());
            dappReportMapper.updateByPrimaryKeySelective(dappReport);
            Dapp dapp = dappMapper.selectByPrimaryKey(dappReport.getDappId());
            pushService.send(PushMessageDetail.builder()
                    .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.REPORT_NOT_TRUE.name())
                    .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(dappReport.getAddress()))
                    .title("举报不真实提醒")
                    .content("您向"+dapp.getName()+"发起的举报信息在公示期内被证实不真实")
                    .data("您向"+dapp.getName()+"发起的举报信息在公示期内被证实不真实，请在发起举报时确保信息的真实性")
                    .build());
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("DappReportModify")//每5分钟一次
    public ReturnT<String> modify(String s) throws Exception {
        List<DappReport> list = dappReportMapper.modifyList(new Date());
        if (CollectionUtils.isEmpty(list))
            return ReturnT.SUCCESS;
        for (DappReport dappReport : list) {
            dappReport.setUpdateTime(new Date());
            dappReport.setStatus(DappReportEnum.OVER_MODIFY_TIME.getStatus());
            dappReportMapper.updateByPrimaryKeySelective(dappReport);
            Dapp dapp = dappMapper.selectByPrimaryKey(dappReport.getDappId());
            dapp.setUpdateTime(new Date());
            dapp.setStatus(DappStatusEnum.HIDE.getStatus());
            dappMapper.updateByPrimaryKeySelective(dapp);
            pushService.send(PushMessageDetail.builder()
                    .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.REPORT_NOT_TRUE.name())
                    .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(dapp.getAddress()))
                    .title("Dapp被隐藏提醒")
                    .content("您举报过的"+dapp.getName()+"因未在整改时效内完成整改，已被隐藏")
                    .data("您举报过的"+dapp.getName()+"因未在整改时效内完成整改，已被隐藏，其他用户在波霎App中无法看到其访问入口。")
                    .build());
        }
        return ReturnT.SUCCESS;
    }
}
