package com.bosha.dapp.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.dapp.api.dto.DonateBuyDto;
import com.bosha.dapp.api.dto.DonateDetailDto;
import com.bosha.dapp.api.dto.DonateListDto;
import com.bosha.dapp.api.dto.DonateQuery;
import com.bosha.dapp.api.entity.SparksDonate;
import com.bosha.dapp.api.entity.SparksDonateBuy;
import com.bosha.dapp.api.entity.SparksDonateImg;
import com.bosha.dapp.api.entity.SparksFavorite;
import com.bosha.dapp.api.entity.SparksReceiveAccount;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.service.DonateService;
import com.bosha.dapp.server.mapper.SparksDonateBuyMapper;
import com.bosha.dapp.server.mapper.SparksDonateImgMapper;
import com.bosha.dapp.server.mapper.SparksDonateMapper;
import com.bosha.dapp.server.mapper.SparksFavoriteMapper;
import com.bosha.dapp.server.mapper.SparksReceiveAccountMapper;
import com.bosha.dapp.server.mapper.SparksStarMapper;
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
 * @DESCRIPTION: DonateServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 17:49
 */
@Service
@Slf4j
public class DonateServiceImpl implements DonateService {

    @Autowired
    private SparksDonateMapper donateMapper;
    @Autowired
    private SparksDonateImgMapper donateImgMapper;
    @Autowired
    private SparksFavoriteMapper favoriteMapper;
    @Autowired
    private SparksDonateBuyMapper donateBuyMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private SparksStarMapper sparksStarMapper;
    @Autowired
    private PushService pushService;
    @Autowired
    private SparksReceiveAccountMapper accountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(SparksDonate donate) {
        BigDecimal cost = donate.getCost();
        if (cost.compareTo(BigDecimal.ZERO) <= 0) {
            donate.setCost(BigDecimal.ZERO);
        }
        donate.setSku(donate.getNum());
        donate.setAddress(RequestContextUtils.getAddress());
        donate.setCreateTime(new Date());
        donate.setUpdateTime(new Date());
        donateMapper.insertSelective(donate);
        List<SparksDonateImg> list = new ArrayList<>();
        donate.getImgs().forEach(s -> list.add(SparksDonateImg.builder().donateId(donate.getId()).url(s).build()));
        donateImgMapper.batchInsert(list);
        log.info("【爱心捐赠】新增：{}", donate);
        push(donate);
        return donate.getId();
    }

    @Override
    public boolean updateHash(SparksDonate donate) {
        SparksDonate sparksDonate = donateMapper.selectByPrimaryKey(donate.getId());
        if (sparksDonate == null || StringUtils.isNotBlank(sparksDonate.getHash()))
            return false;
        log.info("【爱心捐赠】推送打币，donate={}", donate);
        sparksDonate.setHash(donate.getHash());
        push(sparksDonate);
        return donateMapper.updateByPrimaryKeySelective(donate) > 0;
    }

    @Override
    @RedissonDistributedLock(key = "#donateBuyDto.id")
    public void success(String address, DonateBuyDto donateBuyDto) {
        SparksDonate select = donateMapper.selectByPrimaryKey(donateBuyDto.getId());
        if (select == null)
            return;
        if (select.getSku() == 0)
            throw new BaseException(DappErrorMsgEnum.DONATE_STUFF_OUT);
        select.setSku(select.getSku() - donateBuyDto.getNum());
        if (select.getSku() < 0)
            select.setSku(0);
        select.setUpdateTime(new Date());
        donateMapper.updateByPrimaryKeySelective(select);
        log.info("【爱心捐赠】购买成功，address={}，{}", address, select);
        donateBuyMapper.insertSelective(SparksDonateBuy.builder().createTime(new Date())
                .address(address).donateId(donateBuyDto.getId()).notice(0).num(donateBuyDto.getNum())
                .name(donateBuyDto.getName()).phone(donateBuyDto.getPhone()).receiveAddress(donateBuyDto.getReceiveAddress())
                .build());
        notice(address, donateBuyDto.getId());
    }

    @Override
    public PageInfo<DonateListDto> list(DonateQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        return PageInfo.of(donateMapper.list(query));
    }

    @Override
    public DonateDetailDto detail(Long id) {
        String address = RequestContextUtils.getAddress();
        SparksFavorite count = favoriteMapper.count(address, id, 1);
        SparksDonate select = donateMapper.selectByPrimaryKey(id);
        int notice = donateBuyMapper.count(address, id);
        List<Integer> joinTypes = sparksStarMapper.joinTypes(address);
        DonateDetailDto build = DonateDetailDto.builder().donate(select).collect(count == null ? 0 : 1).notice(notice == 0 ? 0 : 1).joinTypes(joinTypes).build();
        List<SparksReceiveAccount> accountList = accountMapper.list(select.getAddress());
        List<SparksDonateBuy> donateBuys = donateBuyMapper.listByAddress(address);
        build.setReceiveAccounts(accountList);
        build.setDonateBuyList(donateBuys);
        return build;
    }

    @Override
    public void notice(String address, Long id) {
        boolean notice = donateBuyMapper.updateNotice(address, id) > 0;
        if (notice) {
            SparksDonate select = donateMapper.selectByPrimaryKey(id);
            if (select==null)
                return;
            User user = userService.getByAddress(address);
            PushMessageDetail detail = PushMessageDetail.builder().addresses(Collections.singletonList(select.getAddress()))
                    .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.DONATE_SUCCESS.name())
                    .pushEnum(AliyunPushEnum.NOTICE)
                    .title("爱心捐赠领取成功")
                    .content(user.getNickName() + "领取了您的爱心捐赠，快来看看吧")
                    .data(id)
                    .build();
            pushService.send(detail);
        }
    }

    @Override
    public PageInfo<DonateListDto> my(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(donateMapper.my(RequestContextUtils.getAddress()));
    }

    @Override
    public PageInfo<DonateListDto> myBuy(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(donateBuyMapper.myBuy(RequestContextUtils.getAddress()));
    }

    @Override
    public PageInfo<DonateListDto> myFavorite(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(donateMapper.myFavorite(RequestContextUtils.getAddress()));
    }

    private void push(SparksDonate donate) {
        if (donate.getCreditScoreMin() != null && donate.getCreditScoreMax() != null && StringUtils.isNotBlank(donate.getHash())) {
            GlobalExecutorService.executorService.execute(() -> {
                User user = userService.getByAddress(donate.getAddress());
                PushMessageDetail detail = PushMessageDetail.builder()
                        .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.DONATE_RELEASE.name())
                        .pushEnum(AliyunPushEnum.NOTICE)
                        .title(user.getNickName() + "发布了爱心捐赠")
                        .content(user.getNickName() + "发布了爱心捐赠，快来看看吧")
                        .data(donate.getId())
                        .build();
                commonService.pushByScoreRange(donate.getCreditScoreMin(), donate.getCreditScoreMax(), detail);
            });
        }
    }
}
