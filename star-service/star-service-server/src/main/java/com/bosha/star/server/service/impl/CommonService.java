package com.bosha.star.server.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Map;


import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.utils.StrUtils;
import com.bosha.contract.api.entity.ContractTransferTask;
import com.bosha.contract.api.service.ContractTransferTaskService;
import com.bosha.finance.api.entity.ContractMiningDetail;
import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import com.bosha.finance.api.service.ContractMiningDetailService;
import com.bosha.star.api.dto.web.SendGiftDto;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.api.entity.LiveMiningGift;
import com.bosha.star.api.entity.LiveMiningGiftAsset;
import com.bosha.star.api.entity.LiveMiningGiftRecord;
import com.bosha.star.api.enums.LiveMiningTypeEnum;
import com.bosha.star.server.config.StarServiceConfig;
import com.bosha.star.server.mapper.LiveMiningGiftAssetMapper;
import com.bosha.star.server.mapper.LiveMiningGiftMapper;
import com.bosha.star.server.mapper.LiveMiningGiftRecordMapper;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.bosha.star.server.redis.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CommonService
 * @Author liqingping
 * @Date 2020-03-29 14:41
 */
@Component
@Slf4j
public class CommonService {

    @Autowired
    private RedissonClient redis;
    @Autowired
    private StarServiceConfig starServiceConfig;
    @Autowired
    private LiveMiningGiftRecordMapper liveMiningGiftRecordMapper;
    @Autowired
    private LiveMiningGiftMapper giftMapper;
    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private LiveMiningGiftAssetMapper giftAssetMapper;
    @Autowired
    private ContractMiningDetailService contractMiningDetailService;
    @Autowired
    private ContractTransferTaskService contractTransferTaskService;

    @RedissonDistributedLock(key = {"#liveMining.id", "#type"})
    public boolean hasLeft(LiveMining liveMining, Integer type) {
        if (type == LiveMiningTypeEnum.SHARE.getType()) {
            RAtomicLong shareNum = redis.getAtomicLong(CacheKey.Live.LIVE_MINING_SHARE_NUM.getKey() + liveMining.getId());
            if (!shareNum.isExists())
                return false;
            if (0 == shareNum.decrementAndGet()) {
                shareNum.delete();
                log.info("【直播挖矿-转发次数】id={}，title={}，当前转发次数已全部挖完！！！", liveMining.getId(), liveMining.getTitle());
            }
            return true;
        } else if (type == LiveMiningTypeEnum.COMMENT.getType()) {
            RAtomicLong commentNum = redis.getAtomicLong(CacheKey.Live.LIVE_MINING_COMMENT_NUM.getKey() + liveMining.getId());
            if (!commentNum.isExists())
                return false;
            if (0 == commentNum.decrementAndGet()) {
                commentNum.delete();
                log.info("【直播挖矿-评论次数】id={}，title={}，当前评论次数已全部挖完！！！", liveMining.getId(), liveMining.getTitle());
            }
            return true;
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void doSend(SendGiftDto sendGiftDto) {
        String address = sendGiftDto.getAddress();
        LiveMiningGift gift = giftMapper.selectByPrimaryKey(sendGiftDto.getGiftId());
        if (gift == null)
            return;
        LiveMining liveMining = liveMiningMapper.selectByPrimaryKey(sendGiftDto.getLiveMiningId());
        if (liveMining == null)
            return;
        LiveMiningGiftAsset giftAsset = giftAssetMapper.getByAddressAndGiftId(address, sendGiftDto.getGiftId());
        if (giftAsset == null)
            return;
        if (giftAsset.getNum() < sendGiftDto.getNum())
            return;
        boolean update = giftAssetMapper.updateGiftNum(giftAsset.getId(), sendGiftDto.getNum(), giftAsset.getNum()) > 0;
        if (!update) {
            log.info("【打赏主播】数据库扣减失败，addr={}，gift={}，id={}", address, gift.getAmount().multiply(BigDecimal.valueOf(sendGiftDto.getNum())), sendGiftDto.getGiftId());
            return;
        }
        BigDecimal amount = gift.getAmount().multiply(BigDecimal.valueOf(sendGiftDto.getNum()));
        LiveMiningGiftRecord record = LiveMiningGiftRecord.builder()
                .address(address).amount(amount)
                .giftId(sendGiftDto.getGiftId()).num(sendGiftDto.getNum())
                .createTime(new Date()).updateTime(new Date()).type(2).status(1)
                .toAddress(liveMining.getAddress()).relatedId(liveMining.getId())
                .build();
        BigDecimal actualAmount = amount.multiply(BigDecimal.ONE.subtract(starServiceConfig.getLiveConfig().getGiftScale()));
        if (amount.compareTo(starServiceConfig.getLiveConfig().getGiftMin()) < 0) {
            log.warn("【打赏主播】打赏金额={}，实际金额={}  小于最小值={}，低于手续费消耗，无法继续打赏 return。",
                    amount, actualAmount, starServiceConfig.getLiveConfig().getGiftMin());
            return;
        }
        liveMiningGiftRecordMapper.insertSelective(record);
        ContractMiningDetail miningDetail = ContractMiningDetail.builder()
                .status(0).address(liveMining.getAddress()).amount(actualAmount)
                .createTime(new Date()).relatedId(record.getId()).serviceType(FinanceServiceTypeEnum.LIVE_MINING_SEND_GIFT.getType())
                .remark(StrUtils.cutStr(liveMining.getTitle(), 8))
                .build();
        Map<String, Long> financeMap = contractMiningDetailService.batchInsert(Collections.singletonList(miningDetail));
        ContractTransferTask build = ContractTransferTask.builder().extraId(record.getId())
                .contractAddress(starServiceConfig.getLiveConfig().getContractAddress()).amount(actualAmount).createTime(new Date())
                .serviceType(FinanceServiceTypeEnum.LIVE_MINING_SEND_GIFT.getType()).systemAddress(starServiceConfig.getLiveConfig().getGiftAddress())
                .status(2).toAddress(miningDetail.getAddress())
                .build();
        build.setFinanceId(financeMap.get(build.getToAddress()));
        contractTransferTaskService.insert(build);
        log.info("【打赏主播】成功添加打币任务，金额={}，from={}，to={}", actualAmount, address, liveMining.getAddress());
    }
}
