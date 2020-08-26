package com.bosha.dapp.server.mq;

import java.util.Date;


import com.bosha.dapp.api.entity.SparksFundation;
import com.bosha.dapp.api.entity.SparksOrg;
import com.bosha.dapp.api.entity.SparksStar;
import com.bosha.dapp.api.entity.SparksWitness;
import com.bosha.dapp.api.enums.SparksStarStatusEnum;
import com.bosha.dapp.api.enums.WitnessEnum;
import com.bosha.dapp.server.mapper.SparksFundationMapper;
import com.bosha.dapp.server.mapper.SparksOrgMapper;
import com.bosha.dapp.server.mapper.SparksStarMapper;
import com.bosha.dapp.server.mapper.SparksWitnessMapper;
import com.bosha.user.api.dto.BlockDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WitnessMqListener
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 14:16
 */
@Slf4j
@RocketMQMessageListener(consumerGroup = "${spring.application.name}" + "_witness", topic = BlockDto.TransactionDto.TOPIC)
@Component
public class WitnessMqListener implements RocketMQListener<BlockDto.TransactionDto> {

    @Autowired
    private SparksWitnessMapper witnessMapper;
    @Autowired
    private SparksStarMapper sparksStarMapper;
    @Autowired
    private SparksOrgMapper orgMapper;
    @Autowired
    private SparksFundationMapper fundationMapper;

    @Override
    public void onMessage(BlockDto.TransactionDto message) {
        String hash = message.getHash();
        SparksWitness witness = witnessMapper.getByHash(hash);
        if (witness == null)
            return;
        log.info("【邀请见证】witness={}\nmsg={}", witness, message);
        witness.setUpdateTime(message.getTransactionTime());
        witness.setAmount(message.getAmount());
        witnessMapper.updateByPrimaryKeySelective(witness);
        WitnessEnum instance = WitnessEnum.getInstance(witness.getType());
        switch (instance) {
            case MAKE:
            case WIPE:
            case LIGHT:
                SparksStar sparksStar = sparksStarMapper.selectByPrimaryKey(witness.getReleatedId());
                if (sparksStar == null) {
                    log.warn("【星星之火见证】未查询到相关记录：{}", witness);
                    return;
                }
                if (sparksStar.getStatus() != SparksStarStatusEnum.WITNESSING.getStatus())
                    return;
                int count = witnessMapper.countSuccess(sparksStar.getId());
                if (count == 8) {
                    sparksStar.setStatus(SparksStarStatusEnum.SUCCESS.getStatus());
                    sparksStar.setUpdateTime(new Date());
                    sparksStarMapper.updateByPrimaryKeySelective(sparksStar);
                    log.info("【星星之火见证】成功：{}", sparksStar);
                }
                break;
            case ORG:
                SparksOrg sparksOrg = orgMapper.selectByPrimaryKey(witness.getReleatedId());
                if (sparksOrg == null) {
                    log.warn("【机构见证】未查询到相关记录：{}", witness);
                    return;
                }
                if (sparksOrg.getStatus() != SparksOrg.STATUS_WITNESSING)
                    return;
                int sparksOrgCount = witnessMapper.countSuccess(sparksOrg.getId());
                if (sparksOrgCount == 10) {
                    sparksOrg.setUpdateTime(new Date());
                    sparksOrg.setStatus(SparksOrg.STATUS_SUCCESS);
                    orgMapper.updateByPrimaryKeySelective(sparksOrg);
                    log.info("【机构见证】成功：{}", sparksOrg);
                }
                break;
            case FUNDATION:
                SparksFundation fundation = fundationMapper.selectByPrimaryKey(witness.getReleatedId());
                if (fundation == null) {
                    log.warn("【基金见证】为查询到相关记录：{}", witness);
                    return;
                }
                if (fundation.getStatus() != SparksFundation.STATUS_WITNESSING)
                    return;
                int fundationCount = witnessMapper.countSuccess(fundation.getId());
                if (fundationCount == 10) {
                    fundation.setUpdateTime(new Date());
                    fundation.setStatus(SparksFundation.STATUS_SUCCESS);
                    fundationMapper.updateByPrimaryKeySelective(fundation);
                    log.info("【基金见证】成功：{}", fundation);
                }
                break;
        }
    }
}
