package com.bosha.star.server.service.impl;

import static com.bosha.star.api.enums.StarErrorMessageEnum.GIFT_NOT_EXIST;
import static com.bosha.star.api.enums.StarErrorMessageEnum.LIVE_NOT_EXIST;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.contract.api.entity.ContractExplorerConfirmTask;
import com.bosha.contract.api.enums.ContractType;
import com.bosha.contract.api.service.ContractExplorerService;
import com.bosha.star.api.dto.web.BuyGiftDto;
import com.bosha.star.api.dto.web.GiftAssetDto;
import com.bosha.star.api.dto.web.SendGiftDto;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.api.entity.LiveMiningGift;
import com.bosha.star.api.entity.LiveMiningGiftAsset;
import com.bosha.star.api.entity.LiveMiningGiftRecord;
import com.bosha.star.api.enums.StarErrorMessageEnum;
import com.bosha.star.api.service.LiveGiftService;
import com.bosha.star.server.config.StarServiceConfig;
import com.bosha.star.server.mapper.LiveMiningGiftAssetMapper;
import com.bosha.star.server.mapper.LiveMiningGiftMapper;
import com.bosha.star.server.mapper.LiveMiningGiftRecordMapper;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.bosha.star.server.redis.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonShutdownException;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveGiftServiceImpl
 * @Author liqingping
 * @Date 2020-03-25 19:18
 */
@RestController
@Slf4j
public class LiveGiftServiceImpl implements LiveGiftService {

    @Autowired
    private LiveMiningGiftAssetMapper giftAssetMapper;
    @Autowired
    private LiveMiningGiftMapper giftMapper;
    @Autowired
    private PushService pushService;
    @Autowired
    private LiveMiningGiftRecordMapper liveMiningGiftRecordMapper;
    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ContractExplorerService contractExplorerService;
    @Autowired
    private StarServiceConfig starServiceConfig;


    @PostConstruct
    public void pollGift() {
        GlobalExecutorService.executorService.submit(() -> {
            while (!redis.isShutdown()) {
                try {
                    RBlockingQueue<SendGiftDto> blockingQueue = redis.getBlockingQueue(CacheKey.Live.LIVE_GIFT_SEND.getKey());
                    SendGiftDto poll = blockingQueue.poll(10, TimeUnit.SECONDS);
                    if (poll != null) {
                        try {
                            commonService.doSend(poll);
                        } catch (Exception e) {
                            blockingQueue.offer(poll);
                        }
                    } else {
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (RedissonShutdownException e) {
                    break;
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        });
    }

    @Override
    public List<GiftAssetDto> asset(@RequestParam("address") String address) {
        return giftAssetMapper.assetDto(address);
    }

    @Override
    public List<LiveMiningGift> giftList() {
        return giftMapper.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buy(@RequestBody BuyGiftDto buy) {
        String address = RequestContextUtils.getAddress();
        log.info("【礼物购买】addr={}，buy={}", address, buy);
        LiveMiningGiftRecord record = LiveMiningGiftRecord.builder()
                .address(address).systemAddress(buy.getSystemAddress()).amount(buy.getAmount()).giftId(buy.getGiftId()).num(buy.getNum())
                .hash(buy.getHash()).createTime(new Date()).type(1).status(0)
                .build();
        liveMiningGiftRecordMapper.insertSelective(record);
        contractExplorerService.add(ContractExplorerConfirmTask.builder()
                .address(address).contractAddress(starServiceConfig.getLiveConfig().getContractAddress()).systemAddress(starServiceConfig.getLiveConfig().getGiftAddress()).amount(buy.getAmount())
                .contractType(ContractType.LIVE.getType()).hash(buy.getHash()).relatedId(record.getId())
                .build());
        pushService.send(PushMessageDetail.builder()
                .type(PushMessageTypeEnum.SYSTEM).subType(PushMessageSubTypeEnum.System.LIVE_GIFT_CONFIRMING.name())
                .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(address))
                .title("您购买直播礼物当前区块确认中")
                .content("当前区块确认中，请稍后查看")
                .data("")
                .build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonDistributedLock(prefix = "live_gift", key = {"@address"},
            waitTime = 30, leaseTime = 30, /*automaticRelease = true, */failStrategy = RedissonDistributedLock.FailStrategy.WAIT_AND_RETURN_NULL)
    public void send(@RequestBody SendGiftDto sendGiftDto) {
        String address = sendGiftDto.getAddress();
        LiveMiningGift gift = giftMapper.selectByPrimaryKey(sendGiftDto.getGiftId());
        if (gift == null)
            throw new BaseException(GIFT_NOT_EXIST);
        LiveMining liveMining = liveMiningMapper.selectByPrimaryKey(sendGiftDto.getLiveMiningId());
        if (liveMining == null)
            throw new BaseException(LIVE_NOT_EXIST);
        log.info("【打赏主播】addr={}，gift={}\nsend={}\nlive={}",
                address, gift.getAmount().multiply(BigDecimal.valueOf(sendGiftDto.getNum())), sendGiftDto, liveMining);
        LiveMiningGiftAsset giftAsset = giftAssetMapper.getByAddressAndGiftId(address, sendGiftDto.getGiftId());
        if (giftAsset == null)
            throw new BaseException(GIFT_NOT_EXIST);
        if (giftAsset.getNum() < sendGiftDto.getNum())
            throw new BaseException(StarErrorMessageEnum.GIFT_BALANCE_NOT_ENOUGH);
        RBlockingQueue<SendGiftDto> blockingQueue = redis.getBlockingQueue(CacheKey.Live.LIVE_GIFT_SEND.getKey());
        blockingQueue.offer(sendGiftDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonDistributedLock(prefix = "live_gift", key = "#liveMiningGiftRecord.address", waitTime = 10, leaseTime = 10)
    public void update(@RequestBody LiveMiningGiftRecord liveMiningGiftRecord) {
        liveMiningGiftRecordMapper.updateByPrimaryKeySelective(liveMiningGiftRecord);
        LiveMiningGiftRecord select = liveMiningGiftRecordMapper.selectByPrimaryKey(liveMiningGiftRecord.getId());
        if (liveMiningGiftRecord.getStatus() != null && liveMiningGiftRecord.getStatus() == 1) {
            LiveMiningGiftAsset giftAsset = giftAssetMapper.getByAddressAndGiftId(select.getAddress(), select.getGiftId());
            if (giftAsset == null) {
                giftAsset = LiveMiningGiftAsset.builder()
                        .address(select.getAddress()).giftId(select.getGiftId()).num(select.getNum()).createTime(new Date()).updateTime(new Date())
                        .build();
                giftAssetMapper.insertSelective(giftAsset);
                log.info("【礼物新增】gift={}", giftAsset);
            } else {
                giftAsset.setUpdateTime(new Date());
                giftAsset.setNum(giftAsset.getNum() + select.getNum());
                giftAssetMapper.updateByPrimaryKeySelective(giftAsset);
                log.info("【礼物到账】gift={}", giftAsset);
            }
            pushService.send(PushMessageDetail.builder()
                    .type(PushMessageTypeEnum.SYSTEM).subType(PushMessageSubTypeEnum.System.LIVE_GIFT_ARRIVAL.name())
                    .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(select.getAddress()))
                    .title("您购买的直播礼物已到账")
                    .content("直播礼物已到账，快去给喜欢的主播打赏吧")
                    .data("")
                    .build());
            pushService.send(PushMessageDetail.builder()
                    .type(PushMessageTypeEnum.SYSTEM).subType(PushMessageSubTypeEnum.System.LIVE_GIFT_ARRIVAL.name())
                    .pushEnum(AliyunPushEnum.MESSAGE).addresses(Collections.singletonList(select.getAddress()))
                    .title("您购买的直播礼物已到账")
                    .content("直播礼物已到账，快去给喜欢的主播打赏吧")
                    .data("")
                    .db(false)
                    .build());
        }
    }
}
