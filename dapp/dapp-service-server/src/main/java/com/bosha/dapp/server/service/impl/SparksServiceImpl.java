package com.bosha.dapp.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.dto.SparksStarDetailDto;
import com.bosha.dapp.api.dto.SparksStarIndexDto;
import com.bosha.dapp.api.dto.SparksStarListDto;
import com.bosha.dapp.api.dto.SparksStarListQuery;
import com.bosha.dapp.api.entity.SparksReceiveAccount;
import com.bosha.dapp.api.entity.SparksStar;
import com.bosha.dapp.api.entity.SparksStarImg;
import com.bosha.dapp.api.entity.SparksWitness;
import com.bosha.dapp.api.service.SparksService;
import com.bosha.dapp.server.mapper.SparksReceiveAccountMapper;
import com.bosha.dapp.server.mapper.SparksStarImgMapper;
import com.bosha.dapp.server.mapper.SparksStarMapper;
import com.bosha.dapp.server.mapper.SparksWitnessMapper;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: SparksServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-25 19:12
 */
@Service
@Slf4j
public class SparksServiceImpl implements SparksService {

    @Autowired
    private SparksStarMapper sparksStarMapper;
    @Autowired
    private SparksStarImgMapper sparksStarImgMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SparksReceiveAccountMapper accountMapper;
    @Autowired
    private SparksWitnessMapper witnessMapper;

    @Override
    public Long add(SparksStar star) {
        star.setStatus(1);
        star.setCreateTime(new Date());
        star.setUpdateTime(new Date());
        sparksStarMapper.insertSelective(star);
        log.info("【新增星星之火】{}", star);
        List<SparksStarImg> list = new ArrayList<>();
        for (String img : star.getImgs()) {
            list.add(SparksStarImg.builder().sparksStarId(star.getId()).url(img).build());
        }
        sparksStarImgMapper.batchInsert(list);
        return star.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SparksStar star) {
        star.setUpdateTime(new Date());
        if (CollectionUtils.isNotEmpty(star.getImgs())) {
            sparksStarImgMapper.deleteBySparksId(star.getId());
            List<SparksStarImg> list = new ArrayList<>();
            for (String img : star.getImgs()) {
                list.add(SparksStarImg.builder().sparksStarId(star.getId()).url(img).build());
            }
            sparksStarImgMapper.batchInsert(list);
        }
        return sparksStarMapper.updateByPrimaryKeySelective(star) > 0;
    }

    @Override
    public PageInfo<SparksStarListDto> list(SparksStarListQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        return PageInfo.of(sparksStarMapper.list(query));
    }

    @Override
    public PageInfo<SparksStarListDto> myList(Page page,String address) {
        PageHelper.startPage(page.getPage(),page.getSize());
        return PageInfo.of(sparksStarMapper.myList(address));
    }

    @Override
    public SparksStarDetailDto detail(Long id) {
        SparksStar select = sparksStarMapper.selectByPrimaryKey(id);
        if (select == null)
            throw new BaseException("该记录不存在");
        User user = userService.getByAddress(select.getAddress());
        List<SparksReceiveAccount> list = accountMapper.list(select.getAddress());
        SparksStarDetailDto build = SparksStarDetailDto.builder().headImg(user.getHeadImg()).nickName(user.getNickName()).sparksStar(select).receiveAccounts(list).build();
        List<SparksWitness> witnessList = witnessMapper.list(select.getId());
        build.setWitnesses(witnessList);
        return build;
    }

    @Override
    public SparksStarIndexDto index() {
        int success = sparksStarMapper.countSuccess();
        SparksStarListDto light = sparksStarMapper.getByType(1);
        SparksStarListDto wipe = sparksStarMapper.getByType(2);
        SparksStarListDto make = sparksStarMapper.getByType(3);
        return SparksStarIndexDto.builder().helpNum(success).light(light).wipe(wipe).make(make).build();
    }

}
