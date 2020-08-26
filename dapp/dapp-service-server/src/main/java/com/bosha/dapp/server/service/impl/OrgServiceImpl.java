package com.bosha.dapp.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.dapp.api.dto.ActivityListDto;
import com.bosha.dapp.api.dto.OrgDetailDto;
import com.bosha.dapp.api.dto.OrgListDto;
import com.bosha.dapp.api.dto.OrgQuery;
import com.bosha.dapp.api.dto.SparksStarListDto;
import com.bosha.dapp.api.entity.SparksOrg;
import com.bosha.dapp.api.entity.SparksOrgFollow;
import com.bosha.dapp.api.entity.SparksOrgImg;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.service.OrgService;
import com.bosha.dapp.server.mapper.SparksActivityMapper;
import com.bosha.dapp.server.mapper.SparksOrgFollowMapper;
import com.bosha.dapp.server.mapper.SparksOrgImgMapper;
import com.bosha.dapp.server.mapper.SparksOrgMapper;
import com.bosha.dapp.server.mapper.SparksStarMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: OrgServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 11:54
 */
@Service
@Slf4j
public class OrgServiceImpl implements OrgService {

    @Autowired
    private SparksOrgMapper orgMapper;
    @Autowired
    private SparksOrgImgMapper orgImgMapper;
    @Autowired
    private SparksOrgFollowMapper followMapper;
    @Autowired
    private SparksStarMapper starMapper;
    @Autowired
    private SparksActivityMapper activityMapper;

    @Override
    @RedissonDistributedLock(key = "@address")
    @Transactional(rollbackFor = Exception.class)
    public Long add(SparksOrg org) {
        SparksOrg select = orgMapper.getByAddress(org.getAddress());
        if (select != null)
            throw new BaseException(DappErrorMsgEnum.ALREADY_ORG);
        org.setCreateTime(new Date());
        org.setUpdateTime(new Date());
        org.setStatus(SparksOrg.STATUS_UN_INVITE);
        orgMapper.insertSelective(org);
        List<SparksOrgImg> list = new ArrayList<>();
        org.getCertificates().forEach(e -> list.add(SparksOrgImg.builder().orgId(org.getId()).url(e).build()));
        orgImgMapper.batchInsert(list);
        log.info("【机构申请】新增：{}", org);
        return org.getId();
    }

    @Override
    public PageInfo<OrgListDto> list(OrgQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        return PageInfo.of(orgMapper.list(RequestContextUtils.getAddress(), query.getName(), query.getCategory()));
    }

    @Override
    @RedissonDistributedLock(key = "@address", waitTime = 0, failStrategy = RedissonDistributedLock.FailStrategy.RETURN_NULL)
    public boolean follow(String address, Long orgId) {
        SparksOrgFollow follow = followMapper.getByAddressAndOrgId(address, orgId);
        if (follow != null)
            return true;
        followMapper.insertSelective(SparksOrgFollow.builder().address(address).orgId(orgId).createTime(new Date()).build());
        return true;
    }

    @Override
    @RedissonDistributedLock(key = "@address", waitTime = 0, failStrategy = RedissonDistributedLock.FailStrategy.RETURN_NULL)
    public boolean unFollow(String address, Long orgId) {
        followMapper.deleteByAddressAndOrgId(address, orgId);
        return true;
    }

    @Override
    public OrgDetailDto detail(Long id) {
        SparksOrg select = orgMapper.selectByPrimaryKey(id);
        SparksStarListDto light = starMapper.getByAddressAndType(select.getAddress(), 1, Arrays.asList(3, 4));
        SparksStarListDto wipe = starMapper.getByAddressAndType(select.getAddress(), 2, Arrays.asList(3, 4));
        SparksStarListDto make = starMapper.getByAddressAndType(select.getAddress(), 3, Arrays.asList(3, 4));
        ActivityListDto activity = activityMapper.getOne(select.getAddress(), RequestContextUtils.getAddress());
        return OrgDetailDto.builder().org(select).light(light).wipe(wipe).make(make).activity(activity).build();
    }

    @Override
    public PageInfo<OrgListDto> my(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(orgMapper.my(RequestContextUtils.getAddress()));
    }

    @Override
    public PageInfo<OrgListDto> myFollow(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(orgMapper.myFollow(RequestContextUtils.getAddress()));
    }
}
