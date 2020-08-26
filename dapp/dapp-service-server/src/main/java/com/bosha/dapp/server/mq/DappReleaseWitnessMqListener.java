package com.bosha.dapp.server.mq;

import java.util.Collections;
import java.util.Date;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.dapp.api.entity.Dapp;
import com.bosha.dapp.api.entity.DappWitness;
import com.bosha.dapp.api.enums.DappStatusEnum;
import com.bosha.dapp.server.mapper.DappMapper;
import com.bosha.dapp.server.mapper.DappWitnessMapper;
import com.bosha.user.api.dto.BlockDto;
import com.bosha.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: PulArrivalMqListener
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-14 15:11
 */
@Slf4j
@RocketMQMessageListener(consumerGroup = "${spring.application.name}" + "_dapp_release_witness", topic = BlockDto.TransactionDto.TOPIC)
@Component
public class DappReleaseWitnessMqListener implements RocketMQListener<BlockDto.TransactionDto> {

    @Autowired
    private DappWitnessMapper dappWitnessMapper;
    @Autowired
    private DappMapper dappMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PushService pushService;

    @Override
    public void onMessage(BlockDto.TransactionDto message) {
        DappWitness dappWitness = dappWitnessMapper.getByAddressAndWitness(message.getTo(), message.getFrom());
        if (dappWitness == null)
            return;
        log.debug("【MQ：PUL到账通知】发布dapp见证：{}", message);
        Dapp dapp = dappMapper.selectByPrimaryKey(dappWitness.getDappId());
        if (dapp.getStatus() != DappStatusEnum.IN_WITNESS.getStatus()) {
            log.debug("【发布dapp见证】dapp {} {} 状态不是见证中", dapp.getId(), dapp.getName());
            return;
        }
        int inviteCount = userService.inviteCount(message.getTo());
        dappWitness.setUpdateTime(message.getTransactionTime());
        dappWitness.setHash(message.getHash());
        dappWitness.setMoney(message.getAmount());
        dappWitnessMapper.updateByPrimaryKeySelective(dappWitness);

        int witnessCount = dappWitnessMapper.countWitnessSuccess(dapp.getId());
        if (inviteCount >= 5 && witnessCount >= 5) {
            dapp.setStatus(DappStatusEnum.RELEASE_SUCCESS.getStatus());
            dapp.setUpdateTime(new Date());
            dappMapper.updateByPrimaryKeySelective(dapp);
            log.info("【发布dapp见证】dapp {} {} 见证成功，发布成功！",dapp.getId(),dapp.getName());
            try {
                pushService.send(PushMessageDetail.builder()
                        .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.RELEASE_SUCCESS.name())
                        .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(dapp.getAddress()))
                        .title("Dapp发布成功")
                        .content("您的Dapp"+dapp.getName()+"已发布成功")
                        .data("")
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
