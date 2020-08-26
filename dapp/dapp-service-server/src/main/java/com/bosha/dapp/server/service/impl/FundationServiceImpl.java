package com.bosha.dapp.server.service.impl;

import java.util.Date;
import java.util.List;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.dto.FundationDetailDto;
import com.bosha.dapp.api.dto.FundationListDto;
import com.bosha.dapp.api.entity.SparksFundation;
import com.bosha.dapp.api.entity.SparksWitness;
import com.bosha.dapp.api.service.FundationService;
import com.bosha.dapp.server.mapper.SparksFundationExtraMapper;
import com.bosha.dapp.server.mapper.SparksFundationMapper;
import com.bosha.dapp.server.mapper.SparksWitnessMapper;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: FundationServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 10:38
 */
@Service
@Slf4j
public class FundationServiceImpl implements FundationService {

    @Autowired
    private SparksFundationMapper fundationMapper;
    @Autowired
    private SparksWitnessMapper witnessMapper;
    @Autowired
    private SparksFundationExtraMapper fundationExtraMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(SparksFundation fundation) {
        fundation.setCreateTime(new Date());
        fundation.setUpdateTime(new Date());
        fundation.setStatus(SparksFundation.STATUS_UN_INVITE);
        fundationMapper.insertSelective(fundation);
        fundation.getCertificates().forEach(c -> c.setFundationId(fundation.getId()));
        fundation.getContracts().forEach(c -> c.setFundationId(fundation.getId()));
        fundationExtraMapper.batchInsert(fundation.getCertificates());
        fundationExtraMapper.batchInsert(fundation.getContracts());
        log.info("【基金】新增：{}", fundation);
        push(fundation);
        return fundation.getId();
    }

    @Override
    public boolean updateHash(SparksFundation fundation) {
        SparksFundation select = fundationMapper.selectByPrimaryKey(fundation.getId());
        if (select == null || StringUtils.isNotBlank(select.getHash()))
            return false;
        log.info("【基金】推送打币，{}", fundation);
        select.setHash(fundation.getHash());
        push(select);
        return fundationMapper.updateByPrimaryKeySelective(fundation) > 0;
    }

    @Override
    public PageInfo<FundationListDto> list(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(fundationMapper.list());
    }

    @Override
    public FundationDetailDto detail(Long id) {
        SparksFundation fundation = fundationMapper.selectByPrimaryKey(id);
        List<SparksWitness> list = witnessMapper.list(id);
        return FundationDetailDto.builder().fundation(fundation).witness(list).build();
    }

    @Override
    public PageInfo<FundationListDto> my(Page page, String address) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(fundationMapper.my(address));
    }

    private void push(SparksFundation fundation) {
        if (fundation.getCreditScoreMin() != null && fundation.getCreditScoreMax() != null && StringUtils.isNotBlank(fundation.getHash())) {
            GlobalExecutorService.executorService.execute(() -> {
                User user = userService.getByAddress(fundation.getAddress());
                PushMessageDetail detail = PushMessageDetail.builder()
                        .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.FUNDATION_RELEASE.name())
                        .pushEnum(AliyunPushEnum.NOTICE)
                        .title(user.getNickName() + "创建了基金")
                        .content(user.getNickName() + "创建了基金，快来看看吧")
                        .data(fundation.getId())
                        .build();
                commonService.pushByScoreRange(fundation.getCreditScoreMin(), fundation.getCreditScoreMax(), detail);
            });
        }
    }
}
