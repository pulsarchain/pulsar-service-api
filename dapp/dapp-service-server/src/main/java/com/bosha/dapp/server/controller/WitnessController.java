package com.bosha.dapp.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import com.alibaba.fastjson.JSON;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.AddressValidator;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.InviteWitnessDto;
import com.bosha.dapp.api.dto.WitnessRelationDto;
import com.bosha.dapp.api.dto.WitnessTransferDto;
import com.bosha.dapp.api.dto.WitnessTransferSparksDto;
import com.bosha.dapp.api.entity.SparksWitness;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.enums.WitnessEnum;
import com.bosha.dapp.api.service.WitnessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WitnessController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 13:32
 */
@RestController
@Slf4j
@Api(tags = "邀请见证相关的")
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/inviteWitness")
public class WitnessController extends BaseController {

    @Autowired
    private WitnessService witnessService;

    @ApiOperation("其他类型（基金、机构）的邀请见证新增")
    @PostMapping
    void inviteWitness(@RequestBody @Validated InviteWitnessDto witnessDto) {
        witnessDto.getAddresses().forEach(s -> {
            if (!AddressValidator.isValid(s))
                throw new BaseException(DappErrorMsgEnum.ADDRESS_ERROR);
        });
        String address = getAddress();
        Set<String> collect = witnessDto.getAddresses().stream().map(String::toUpperCase).collect(Collectors.toSet());
        if (collect.size() != witnessDto.getAddresses().size()) {
            throw new BaseException(DappErrorMsgEnum.ADDRESS_DUPLICATED);
        }
        if (collect.contains(getAddress().toUpperCase()))
            throw new BaseException(DappErrorMsgEnum.SELF_ADDRESS);
        List<SparksWitness> witnesses = new ArrayList<>(collect.size());
        for (String s : witnessDto.getAddresses()) {
            witnesses.add(SparksWitness.builder()
                    .address(address).witnessAddress(s).createTime(new Date()).releatedId(witnessDto.getId()).type(WitnessEnum.getInstance(witnessDto.getType()).getType())
                    .build());
        }
        witnessService.insertBatch(witnesses);
    }

    @ApiOperation("星星之火邀请见证新增")
    @PostMapping("/sparks")
    void inviteWitness(@RequestBody @Validated WitnessRelationDto wd) {
        if (CollectionUtils.isEmpty(wd.getRelations()))
            throw new BaseException(DappErrorMsgEnum.WITNESS_LIST_CAN_NOT_BE_NULL);
        List<WitnessRelationDto.Relation> relations = wd.getRelations();
        for (WitnessRelationDto.Relation relation : relations) {
            if (!AddressValidator.isValid(relation.getAddress()))
                throw new BaseException(DappErrorMsgEnum.ADDRESS_ERROR);
        }
        String address = getAddress();
        Set<String> collect = relations.stream().map(WitnessRelationDto.Relation::getAddress).map(String::toUpperCase).collect(Collectors.toSet());
        Set<String> collect2 = relations.stream().map(WitnessRelationDto.Relation::getRelation).collect(Collectors.toSet());
        if (collect.size() != relations.size()) {
            throw new BaseException(DappErrorMsgEnum.ADDRESS_DUPLICATED);
        }
        if (collect2.size() != relations.size()) {
            throw new BaseException(DappErrorMsgEnum.WITNESS_ADDRESS_REPETITION);
        }
        if (collect.contains(getAddress().toUpperCase()))
            throw new BaseException(DappErrorMsgEnum.SELF_ADDRESS);
        List<SparksWitness> witnesses = new ArrayList<>(collect.size());
        for (WitnessRelationDto.Relation relation : relations) {
            Map<String, Object> map = new HashMap<>();
            map.put("relation", relation.getRelation());
            witnesses.add(SparksWitness.builder()
                    .address(address).witnessAddress(relation.getAddress()).createTime(new Date()).releatedId(wd.getRelatedId()).type(WitnessEnum.LIGHT.getType())
                    .extra(JSON.toJSONString(map))
                    .build());
        }
        witnessService.insertBatch(witnesses);
    }

    @ApiOperation("星星之火（点亮、擦亮、造星）邀请见证打币成功后")
    @PostMapping("/sparks/transfer")
    public void transfer(@RequestBody @Validated WitnessTransferSparksDto witnessTransferDto) {
        witnessService.update(witnessTransferDto.getId(), getAddress(), witnessTransferDto.getHash(), witnessTransferDto.getStory());
    }

    @ApiOperation("剩余其他类型邀请见证打币成功后")
    @PostMapping("/transfer")
    public void transfer(@RequestBody WitnessTransferDto witnessTransferDto) {
        witnessService.update(witnessTransferDto.getId(), getAddress(), witnessTransferDto.getHash(), null);
    }
}
