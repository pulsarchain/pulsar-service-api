package com.bosha.dapp.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import cn.hutool.core.date.DateUtil;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.config.EnvConfig;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.dapp.api.dto.DappReportAddDto;
import com.bosha.dapp.api.dto.DappReportDetailDto;
import com.bosha.dapp.api.dto.DappReportNoticeDto;
import com.bosha.dapp.api.dto.DappReportPublicListDto;
import com.bosha.dapp.api.dto.MyDappReportListDto;
import com.bosha.dapp.api.entity.Dapp;
import com.bosha.dapp.api.entity.DappReport;
import com.bosha.dapp.api.entity.DappReportCategory;
import com.bosha.dapp.api.entity.DappReportPic;
import com.bosha.dapp.api.entity.DappReportVote;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.enums.DappReportEnum;
import com.bosha.dapp.api.service.DappReportService;
import com.bosha.dapp.server.mapper.DappMapper;
import com.bosha.dapp.server.mapper.DappReportCategoryMapper;
import com.bosha.dapp.server.mapper.DappReportMapper;
import com.bosha.dapp.server.mapper.DappReportPicMapper;
import com.bosha.dapp.server.mapper.DappReportVoteMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappReportServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 19:37
 */
@RestController
@Slf4j
public class DappReportServiceImpl implements DappReportService {

    @Autowired
    private DappMapper dappMapper;
    @Autowired
    private DappReportMapper dappReportMapper;
    @Autowired
    private DappReportCategoryMapper dappReportCategoryMapper;
    @Autowired
    private DappReportPicMapper dappReportPicMapper;
    @Autowired
    private DappReportVoteMapper dappReportVoteMapper;
    @Autowired
    private PushService pushService;

    private static final String REPORT_NOTICE_MSG = "您的Dapp中有%s信息";

    @Override
    public List<DappReportCategory> categories() {
        return dappReportCategoryMapper.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonDistributedLock(key = "@address", waitTime = 0L, failStrategy = RedissonDistributedLock.FailStrategy.RETURN_NULL)
    public Long add(@RequestBody DappReportAddDto report) {
        Dapp dapp = dappMapper.selectByPrimaryKey(report.getDappId());
        if (dapp == null)
            throw new BaseException("该Dapp不存在");
        DappReportCategory category = dappReportCategoryMapper.selectByPrimaryKey(report.getReportCategroyId());
        if (category == null)
            throw new BaseException("该分类不存在");
        boolean b = dappReportMapper.countExist(RequestContextUtils.getAddress(), report.getDappId()) > 0;
        if (b)
            throw new BaseException(DappErrorMsgEnum.ALREADY_REPORTED_DAPP);
        DappReport build = DappReport.builder()
                .dappId(report.getDappId())
                .address(RequestContextUtils.getAddress())
                .content(report.getContent())
                .createTime(new Date())
                .publicEndTime(DateUtil.offsetDay(new Date(), 30))
                .reportCategroyId(report.getReportCategroyId())
                .status(DappReportEnum.VOTING.getStatus())
                .build();
        dappReportMapper.insertSelective(build);
        List<DappReportPic> list = new ArrayList<>();
        report.getPicList().forEach(s -> list.add(DappReportPic.builder().picUrl(s).dappReportId(build.getId()).build()));
        dappReportPicMapper.batchInsert(list);
        log.info("【dapp举报】用户 {} 举报了dapp {}", RequestContextUtils.getAddress(), dapp.getName());
        return build.getId();
    }

    @Override
    public DappReportDetailDto detail(@RequestParam("id") Long id) {
        return dappReportMapper.detail(id);
    }

    @Override
    public PageInfo<MyDappReportListDto> list(@ModelAttribute Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(dappReportMapper.listMyRecord(RequestContextUtils.getAddress()));
    }

    @Override
    public PageInfo<DappReportPublicListDto> publicList(@ModelAttribute Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(dappReportMapper.publicList(RequestContextUtils.getAddress()));
    }

    @Override
    public DappReportPublicListDto publicDetail(@RequestParam("id") Long id) {
        return dappReportMapper.publicDetail(RequestContextUtils.getAddress(), id);
    }

    @Override
    @RedissonDistributedLock(key = "#dappReportVote.dappReportId")
    public void vote(@RequestBody DappReportVote dappReportVote) {
        int count = dappReportVoteMapper.count(RequestContextUtils.getAddress(), dappReportVote.getDappReportId());
        if (count > 0)
            return;
        DappReport dappReport = dappReportMapper.selectByPrimaryKey(dappReportVote.getDappReportId());
        if (dappReport == null)
            throw new BaseException("id错误，该举报不存在");
        if (dappReport.getStatus() != DappReportEnum.VOTING.getStatus()) {
            return;
        }
        dappReportVote.setCreateTime(new Date());
        dappReportVoteMapper.insertSelective(dappReportVote);
        log.info("【dapp举报投票】用户投票了 dapp {}", dappReportVote);
        int countVote = dappReportVoteMapper.countVote(dappReportVote.getDappReportId());
        int num = 100;
       if (!EnvConfig.isProd()){
           num = 5;
       }
        if (countVote != num)
            return;
        dappReport.setStatus(DappReportEnum.MODIFYING.getStatus());
        dappReport.setDappModifyEndTime(DateUtil.offsetHour(new Date(), 48));
        dappReport.setUpdateTime(new Date());
        dappReportMapper.updateByPrimaryKeySelective(dappReport);

        Dapp dapp = dappMapper.selectByPrimaryKey(dappReport.getDappId());
        DappReportCategory dappReportCategory = dappReportCategoryMapper.selectByPrimaryKey(dappReport.getReportCategroyId());
        pushService.send(PushMessageDetail.builder()
                .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.REPORT_NOTICE.name())
                .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(dappReport.getAddress()))
                .title("您的Dapp被举报，请及时修改")
                .content(String.format(REPORT_NOTICE_MSG, dappReportCategory.getName()))
                .data(DappReportNoticeDto.builder()
                        .content(dappReport.getContent()).dappId(dapp.getId()).modifyEndTime(dappReport.getDappModifyEndTime()).picList(dappReport.getPicList())
                        .build())
                .build());
    }
}
