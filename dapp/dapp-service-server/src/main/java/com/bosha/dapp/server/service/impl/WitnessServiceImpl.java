package com.bosha.dapp.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.dapp.api.dto.SparksStarNoticeDto;
import com.bosha.dapp.api.dto.WitnessNoticeDto;
import com.bosha.dapp.api.entity.SparksFundation;
import com.bosha.dapp.api.entity.SparksOrg;
import com.bosha.dapp.api.entity.SparksStar;
import com.bosha.dapp.api.entity.SparksWitness;
import com.bosha.dapp.api.enums.SparksStarStatusEnum;
import com.bosha.dapp.api.enums.WitnessEnum;
import com.bosha.dapp.api.service.WitnessService;
import com.bosha.dapp.server.mapper.SparksFundationMapper;
import com.bosha.dapp.server.mapper.SparksOrgMapper;
import com.bosha.dapp.server.mapper.SparksStarMapper;
import com.bosha.dapp.server.mapper.SparksWitnessMapper;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WitnessServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 11:05
 */
@Service
@Slf4j
public class WitnessServiceImpl implements WitnessService {

    private static final String MSG = "%s邀请您帮Ta见证";
    @Autowired
    private SparksWitnessMapper witnessMapper;
    @Autowired
    private SparksStarMapper sparksStarMapper;
    @Autowired
    private PushService pushService;
    @Autowired
    private UserService userService;
    @Autowired
    private SparksOrgMapper orgMapper;
    @Autowired
    private SparksFundationMapper fundationMapper;


    @Override
    public boolean update(Long id, String witnessAddress, String hash, String story) {
        SparksStar sparksStar = sparksStarMapper.selectByPrimaryKey(id);
        if (sparksStar == null)
            throw new BaseException("该记录不存在");
        SparksWitness witness = witnessMapper.getByAddressAndWitnessAndId(sparksStar.getAddress(), witnessAddress, id);
        if (witness == null)
            throw new BaseException("该记录不存在");
        witness.setUpdateTime(new Date());
        witness.setHash(hash);
        WitnessEnum instance = WitnessEnum.getInstance(witness.getType());
        switch (instance) {
            case LIGHT:
            case MAKE:
            case WIPE:
                JSONObject jsonObject = JSON.parseObject(witness.getExtra());
                jsonObject.put("story", story);
                witness.setExtra(jsonObject.toJSONString());
                break;
            case ORG:
            case FUNDATION:
        }
        return witnessMapper.updateByPrimaryKeySelective(witness) > 0;
    }

    @Override
    public Long add(SparksWitness witness) {
        SparksWitness w = witnessMapper.get(witness.getAddress(), witness.getWitnessAddress(), witness.getReleatedId());
        if (w != null)
            return w.getId();
        SparksStar sparksStar = sparksStarMapper.selectByPrimaryKey(witness.getReleatedId());
        if (sparksStar == null)
            throw new BaseException("该记录不存在");
        if (sparksStar.getStatus() == SparksStarStatusEnum.UN_INVITE_WITNESS.getStatus()) {
            sparksStarMapper.updateByPrimaryKeySelective(SparksStar.builder().status(SparksStarStatusEnum.WITNESSING.getStatus()).updateTime(new Date()).id(sparksStar.getId()).build());
        }
        witness.setCreateTime(new Date());
        witnessMapper.insertSelective(witness);
        return witness.getId();
    }

    @Override
    public void insertBatch(List<SparksWitness> witnesses) {
        SparksWitness sparksWitness = witnesses.get(0);
        String address = RequestContextUtils.getAddress();
        GlobalExecutorService.executorService.execute(() -> {
            Long releatedId = sparksWitness.getReleatedId();
            User user = userService.getByAddress(address);
            WitnessEnum instance = WitnessEnum.getInstance(sparksWitness.getType());
            switch (instance) {
                case LIGHT:
                case WIPE:
                case MAKE:
                    SparksStar sparksStar = sparksStarMapper.selectByPrimaryKey(releatedId);
                    pushService.send(PushMessageDetail.builder()
                            .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.SPARKS_INVITE_WITNESS.name())
                            .pushEnum(AliyunPushEnum.NOTICE).addresses(witnesses.stream().map(SparksWitness::getWitnessAddress).collect(Collectors.toList()))
                            .title(String.format(MSG, user.getNickName()))
                            .content(sparksStar.getName())
                            .data(SparksStarNoticeDto.builder().address(address).id(releatedId).name(sparksStar.getName()).nickName(user.getNickName()).type(sparksStar.getType()).build())
                            .build());
                    return;
                case ORG:
                    SparksOrg org = orgMapper.selectByPrimaryKey(releatedId);
                    pushService.send(PushMessageDetail.builder()
                            .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.ORG_INVITE_WITNESS.name())
                            .pushEnum(AliyunPushEnum.NOTICE).addresses(witnesses.stream().map(SparksWitness::getWitnessAddress).collect(Collectors.toList()))
                            .title(String.format(MSG, user.getNickName()))
                            .content(org.getName())
                            .data(WitnessNoticeDto.builder().address(address).id(org.getId()).name(org.getName()).nickName(user.getNickName()).type(1).build())
                            .build());
                    return;
                case FUNDATION:
                    SparksFundation fundation = fundationMapper.selectByPrimaryKey(releatedId);
                    pushService.send(PushMessageDetail.builder()
                            .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.FUNDATION_INVITE_WITNESS.name())
                            .pushEnum(AliyunPushEnum.NOTICE).addresses(witnesses.stream().map(SparksWitness::getWitnessAddress).collect(Collectors.toList()))
                            .title(String.format(MSG, user.getNickName()))
                            .content(fundation.getName())
                            .data(WitnessNoticeDto.builder().address(address).id(fundation.getId()).name(fundation.getName()).nickName(user.getNickName()).type(2).build())
                            .build());
                    return;
            }

        });
        witnesses.forEach(this::add);
    }

    @Override
    public List<SparksWitness> list(Long relatedId) {
        return witnessMapper.list(relatedId);
    }

}
