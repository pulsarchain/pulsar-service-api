package com.bosha.star.server.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.bosha.contract.api.entity.ContractTransferTask;
import com.bosha.contract.api.service.ContractTransferTaskService;
import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import com.bosha.star.api.dto.web.ImGroupPushMessage;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.api.enums.LiveMiningChatRoomPushEnum;
import com.bosha.star.server.config.StarServiceConfig;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.bosha.star.server.redis.CacheKey;
import com.bosha.star.server.utils.ImUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveNoticePushJob
 * @Author liqingping
 * @Date 2020-03-25 15:38
 */
@Component
@Slf4j
public class LiveCloseJob {

    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private ContractTransferTaskService contractTransferTaskService;
    @Autowired
    private StarServiceConfig starServiceConfig;
    @Autowired
    private ImUtils imUtils;

    @XxlJob("LiveCloseJob")//每5秒运行一次
    public ReturnT<String> notice(String s) {
        try {
            List<LiveMining> list = liveMiningMapper.closeLive(new Date());
            for (LiveMining liveMining : list) {
                liveMiningMapper.updateByPrimaryKeySelective(LiveMining.builder()
                        .id(liveMining.getId()).status(LiveMining.STATUS_FINISHED).updateTime(new Date())
                        .build());
                redis.getBucket(CacheKey.Live.LIVE_MINING.getKey() + liveMining.getId()).delete();
                if (liveMining.getStatus() == LiveMining.STATUS_LIVING) {
                    imUtils.pushNotice(liveMining.getId(), ImGroupPushMessage.builder().data("").type(LiveMiningChatRoomPushEnum.LIVE_FINISHED).build());
                } else if (liveMining.getStatus() == LiveMining.STATUS_TO_BE_LIVE) {
                    log.info("【直播关闭】准备结算未开播的。。id={}，title={}", liveMining.getId(), liveMining.getTitle());
                    donate(liveMining, liveMining.getAmount());
                }
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ReturnT.FAIL;
        }
    }

    public void donate(LiveMining liveMining, BigDecimal amount) {
        //没有开播过 92%给慈善地址，8%给系统地址
        BigDecimal charity = amount.multiply(BigDecimal.valueOf(0.92));
        BigDecimal system = amount.subtract(charity);
        if (charity.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            return;
        }
        List<ContractTransferTask> taskList = new ArrayList<>();
        ContractTransferTask charityTask = ContractTransferTask.builder()
                .contractAddress(liveMining.getContractAddress()).amount(charity).createTime(new Date())
                .serviceType(FinanceServiceTypeEnum.LIVE_MINING_CHARITY.getType()).systemAddress(liveMining.getSystemAddress())
                .status(2).toAddress(starServiceConfig.getCharityAddress())
                .build();
        taskList.add(charityTask);
        ContractTransferTask systemTask = ContractTransferTask.builder()
                .contractAddress(liveMining.getContractAddress()).amount(system).createTime(new Date())
                .serviceType(FinanceServiceTypeEnum.LIVE_MINING_SYSTEM.getType()).systemAddress(liveMining.getSystemAddress())
                .status(2).toAddress(liveMining.getSystemAddress())
                .build();
        taskList.add(systemTask);
        contractTransferTaskService.insertBatch(taskList);
        liveMiningMapper.updateByPrimaryKeySelective(LiveMining.builder().id(liveMining.getId()).leftAmount(BigDecimal.ZERO).updateTime(new Date()).build());
        log.info("【直播挖矿】结算捐赠 添加转币任务，id={}，title={}，charity={}，system={}", liveMining.getId(), liveMining.getTitle(), charity, system);
    }


}
