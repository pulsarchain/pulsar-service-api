package com.bosha.star.server.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.entity.PushMessage;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.config.EnvConfig;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.third.weixinpush.WeixinPush;
import com.bosha.commons.third.weixinpush.autoconfiguration.WeixinPushProperties;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.commons.utils.StrUtils;
import com.bosha.contract.api.entity.ContractExplorerConfirmTask;
import com.bosha.contract.api.enums.ContractType;
import com.bosha.contract.api.service.ContractAddressPoolService;
import com.bosha.contract.api.service.ContractExplorerService;
import com.bosha.contract.api.service.ContractService;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.web.ImGroupPushMessage;
import com.bosha.star.api.dto.web.LiveAcceptDto;
import com.bosha.star.api.dto.web.LiveConfirmDto;
import com.bosha.star.api.dto.web.LiveInfoDto;
import com.bosha.star.api.dto.web.LiveMiningDto;
import com.bosha.star.api.dto.web.LiveMiningListQueryDto;
import com.bosha.star.api.dto.web.LiveMiningMineDto;
import com.bosha.star.api.dto.web.LiveMiningMinerDto;
import com.bosha.star.api.dto.web.LivePushInviteDto;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.api.entity.LiveMiningInvite;
import com.bosha.star.api.entity.LiveMiningStream;
import com.bosha.star.api.entity.Star;
import com.bosha.star.api.entity.StarMember;
import com.bosha.star.api.enums.LiveMiningChatRoomPushEnum;
import com.bosha.star.api.service.LiveMiningService;
import com.bosha.star.api.service.StarService;
import com.bosha.star.server.config.LiveConfig;
import com.bosha.star.server.config.StarServiceConfig;
import com.bosha.star.server.mapper.LiveMiningInviteMapper;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.bosha.star.server.mapper.LiveMiningStreamMapper;
import com.bosha.star.server.mapper.StarMapper;
import com.bosha.star.server.mapper.StarMemberMapper;
import com.bosha.star.server.redis.CacheKey;
import com.bosha.star.server.utils.GenerateLiveStreamUrl;
import com.bosha.star.server.utils.GenerateUserSig;
import com.bosha.star.server.utils.ImUtils;
import com.bosha.user.api.dto.QueryCreditScoreRangeDto;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.service.CreditScoreService;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningServiceImpl
 * @Author liqingping
 * @Date 2020-03-24 16:54
 */
@RestController
@Slf4j
public class LiveMiningServiceImpl implements LiveMiningService {

    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private StarServiceConfig starServiceConfig;
    @Autowired
    private StarMemberMapper starMemberMapper;
    @Autowired
    private ContractService contractService;
    @Autowired
    private ContractExplorerService contractExplorerService;
    @Autowired
    private ContractAddressPoolService contractAddressPoolService;
    @Autowired
    private PushService pushService;
    @Autowired
    private CreditScoreService creditScoreService;
    @Autowired
    private UserService userService;
    @Autowired
    private LiveMiningInviteMapper liveMiningInviteMapper;
    @Autowired
    private StarMapper starMapper;
    @Autowired
    private StarService starService;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private LiveMiningStreamMapper liveMiningStreamMapper;
    @Autowired
    private ImUtils imUtils;
    @Autowired
    private WeixinPushProperties weixinPushProperties;

    @Override
    public LiveInfoDto info(@RequestParam("address") String address) {
        StarMember starMember = starService.getStarMemberFromCache(address);
        String systemAddress = contractService.getSystemAddress(ContractType.LIVE);
        LiveMining liveMining = liveMiningMapper.getLastestByAddress(address);
        LiveInfoDto build = LiveInfoDto.builder()
                .systemAddress(systemAddress)
                .giftAddress(starServiceConfig.getLiveConfig().getGiftAddress())
                .liveFeePerMin(starServiceConfig.getLiveConfig().getLiveFeePerMin())
                .createStatus(liveMining == null ? LiveMining.STATUS_FINISHED : liveMining.getStatus())
                .liveSdkAppid(starServiceConfig.getLiveConfig().getSdkAppid())
                .build();
        if (starMember != null) {
            build.setStarStatus(starMember.getStatus());
            build.setLevel(starMember.getCurrentLevel());
            Star star = starMapper.selectByPrimaryKey(starMember.getStarId());
            build.setStarName(star.getName());
        }
        User user = userService.getByAddress(address);
        if (user != null) {
            build.setNickName(user.getNickName());
            build.setHeadImg(user.getHeadImg());
        }
        build.setLiveUserSig(GenerateUserSig.generate(starServiceConfig.getLiveConfig().getSdkAppid(),
                starServiceConfig.getLiveConfig().getSecretKey(),
                address, TimeUnit.DAYS.toSeconds(180)));
        return build;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonDistributedLock(key = "@address", waitTime = 10, leaseTime = 10, automaticRelease = false)
    public Long create(@RequestBody @Validated LiveMining liveMining) {
        String address = RequestContextUtils.getAddress();
        LiveMining db = liveMiningMapper.getLastestByAddress(address);
        if (db != null && db.getStatus() != LiveMining.STATUS_FINISHED) {
            throw new BaseException("上一次创建的直播结束后才可再次创建");
        }
        if (lessThan(liveMining.getSharePul()) || lessThan(liveMining.getCommentPul()) || lessThan(liveMining.getView()))
            throw new BaseException("转发/留言/观看 的最小值为0.01PUL");
        liveMining.setAddress(address);
        liveMining.setCreateTime(new Date());
        liveMining.setStatus(LiveMining.STATUS_CONFIRMING);
        liveMining.setContractAddress(contractAddressPoolService.get(ContractType.LIVE));
        liveMining.setCharityAddress(starServiceConfig.getCharityAddress());
        liveMining.setOnlineNum(0);
        liveMining.setMinerNum(0);
        liveMining.setLeftAmount(liveMining.getAmount());
        log.info("【创建直播 】{}", liveMining);
        liveMining.setLiveStartTime(DateUtil.parseDateTime(DateUtil.format(liveMining.getLiveStartTime(), StarServiceConstants.SECOND_FORMAT)));
        liveMining.setLiveEndTime(DateUtil.parseDateTime(DateUtil.format(liveMining.getLiveEndTime(), StarServiceConstants.SECOND_FORMAT)));
        liveMiningMapper.insertSelective(liveMining);

        contractExplorerService.add(ContractExplorerConfirmTask.builder()
                .address(address).contractAddress(liveMining.getContractAddress()).systemAddress(liveMining.getSystemAddress()).amount(liveMining.getAmount())
                .contractType(ContractType.LIVE.getType()).hash(liveMining.getHash()).relatedId(liveMining.getId())
                .build());
        GlobalExecutorService.executorService.execute(() -> pushService.send(PushMessageDetail.builder()
                .type(PushMessageTypeEnum.SYSTEM).subType(PushMessageSubTypeEnum.System.LIVE_CREATE_CONFIRMING.name())
                .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(address))
                .title("您创建的直播当前区块确认中")
                .content(StrUtils.cutStr(liveMining.getTitle(), 10) + "，当前区块确认中...请稍后查看")
                .data(liveMining.getId())
                .build()));
        log.info("【创建直播 】create-成功，{}", liveMining);
        GlobalExecutorService.executorService.execute(() -> {
            try {
                imUtils.createGroup(liveMining.getId(), DateUtil.between(new Date(), DateUtil.offsetMinute(liveMining.getLiveEndTime(), 60), DateUnit.MINUTE));
            } catch (Exception e) {
                if (EnvConfig.isProd())
                    WeixinPush.push(weixinPushProperties, "创建直播，创建聊天室失败，id=" + liveMining.getId() + "\n" + e.getMessage());
                imUtils.createGroup(liveMining.getId(), DateUtil.between(new Date(), DateUtil.offsetMinute(liveMining.getLiveEndTime(), 60), DateUnit.MINUTE));
            }
        });
        return liveMining.getId();
    }

    @Override
    public LiveMining update(@RequestBody LiveMining liveMining) {
        liveMiningMapper.updateByPrimaryKeySelective(liveMining);
        LiveMining select = liveMiningMapper.selectByPrimaryKey(liveMining.getId());
        if (select == null)
            throw new RuntimeException("直播挖矿不存在 id=" + liveMining.getId());
        if (liveMining.getStatus() == LiveMining.STATUS_TO_BE_LIVE) {
            RAtomicLong shareNum = redis.getAtomicLong(CacheKey.Live.LIVE_MINING_SHARE_NUM.getKey() + select.getId());
            shareNum.set(select.getShareNum());
            shareNum.expireAt(select.getLiveEndTime().getTime() + 100);
            RAtomicLong commentNum = redis.getAtomicLong(CacheKey.Live.LIVE_MINING_COMMENT_NUM.getKey() + select.getId());
            commentNum.set(select.getCommentNum());
            commentNum.expireAt(select.getLiveEndTime().getTime() + 100);
            pushService.send(PushMessageDetail.builder()
                    .type(PushMessageTypeEnum.SYSTEM).subType(PushMessageSubTypeEnum.System.LIVE_CREATE_SUCCESS.name())
                    .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(select.getAddress()))
                    .title("您已成功创建直播")
                    .content(StrUtils.cutStr(select.getTitle(), 10) + "直播创建成功...快去通知好友观看吧")
                    .data(select.getId())
                    .build());
            generateStreamUrl(select);
            GlobalExecutorService.executorService.execute(() -> {
                QueryCreditScoreRangeDto queryCreditScoreRangeDto = new QueryCreditScoreRangeDto();
                queryCreditScoreRangeDto.setMin(select.getInviteStart());
                queryCreditScoreRangeDto.setMax(select.getInviteEnd());
                queryCreditScoreRangeDto.setPage(1);
                queryCreditScoreRangeDto.setSize(100);
                User user = userService.getByAddress(select.getAddress());
                String nickName = StringUtils.isBlank(user.getNickName()) ? StrUtils.addr(select.getAddress()) : StrUtils.cutStr(user.getNickName(), 10);
                PageInfo<String> pageInfo = creditScoreService.listByScore(queryCreditScoreRangeDto);
                push(pageInfo.getList(), nickName, select, 1);
                for (int i = 2; i <= pageInfo.getPages(); i++) {
                    queryCreditScoreRangeDto.setPage(i);
                    PageInfo<String> tmp = creditScoreService.listByScore(queryCreditScoreRangeDto);
                    push(tmp.getList(), nickName, select, i);
                }
            });
            log.info("【直播挖矿】update-到账成功，状态修改成功，live={}", select);
        }
        return select;
    }

    private void generateStreamUrl(LiveMining select) {
        LiveConfig liveConfig = starServiceConfig.getLiveConfig();
        String pushDomain = liveConfig.getPushDomain();
        String streamId = select.getAddress() + "_" + select.getId();
        String pushUrl = GenerateLiveStreamUrl.sign(liveConfig.getSecretKey(), streamId, (select.getLiveEndTime().getTime() / 1000) + 5);
        pushUrl = pushDomain + streamId + "?" + pushUrl;
        String pullDomain = String.format(liveConfig.getPullDomain(), streamId);
        String sign = GenerateLiveStreamUrl.sign(liveConfig.getSecretKey(), streamId, (select.getLiveEndTime().getTime() / 1000) + 60);
        String pullUrl = pullDomain + "?" + sign;
        log.info("【直播挖矿】 生成推流地址和拉流地址 ↓ ↓ ↓\npushUrl={}，pullUrl={}\nlive={}", pushUrl, pullUrl, select);
        liveMiningStreamMapper.insertOrUpdateSelective(
                LiveMiningStream.builder().liveMiningId(select.getId()).pushUrl(pushUrl).pullUrl(pullUrl).streamId(streamId).createTime(new Date()).build());
    }

    @Override
    public void accept(@RequestBody LiveAcceptDto accept) {
        String address = RequestContextUtils.getAddress();
        StarMember starMember = starMemberMapper.getByAddress(address);
        LiveMiningInvite invite = LiveMiningInvite.builder()
                .address(address).createTime(new Date()).liveMiningId(accept.getLiveMiningId()).starId(starMember == null ? null : starMember.getStarId()).build();
        liveMiningInviteMapper.insertOrUpdateSelective(invite);
        List<PushMessage> list = pushService.listByType(address, PushMessageTypeEnum.SYSTEM, PushMessageSubTypeEnum.System.LIVE_INVITE.name());
        if (CollectionUtils.isNotEmpty(list)) {
            for (PushMessage pushMessage : list) {
                LivePushInviteDto inviteDto = JSON.parseObject(pushMessage.getExtParameters(), LivePushInviteDto.class);
                if (accept.getLiveMiningId().equals(inviteDto.getLiveMiningId())) {
                    inviteDto.setInvite(true);
                    pushMessage.setExtParameters(JSON.toJSONString(inviteDto, SerializerFeature.WriteMapNullValue));
                    pushService.update(pushMessage);
                    log.info("【直播接受邀请】修改通知消息为已接受 {}", inviteDto);
                }
            }
        }
        log.info("【直播接受邀请】liveId={}，address={}", accept.getLiveMiningId(), address);
    }

    @Override
    public PageInfo<LiveMiningDto> list(@RequestBody LiveMiningListQueryDto dto) {
        PageHelper.startPage(dto.getPage().getPage(), dto.getPage().getSize());
        return PageInfo.of(liveMiningMapper.list(dto.getList()));
    }

    @Override
    public PageInfo<LiveMiningDto> search(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageHelper.startPage(page, size);
        return PageInfo.of(liveMiningMapper.search(keyword));
    }

    @Override
    public PageInfo<LiveMiningDto> historyList(@RequestBody LiveMiningListQueryDto dto) {
        PageHelper.startPage(dto.getPage().getPage(), dto.getPage().getSize());
        return PageInfo.of(liveMiningMapper.histories());
    }

    @Override
    public LiveMiningMineDto mine(@PathVariable("id") Long id) {
        String address = RequestContextUtils.getAddress();
        return liveMiningMapper.mine(address, id);
    }

    @Override
    public PageInfo<LiveMiningMineDto> mineList(@ModelAttribute Page page) {
        String address = RequestContextUtils.getAddress();
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(liveMiningMapper.mineList(address));
    }

    @Override
    public LiveMiningMinerDto miner(@PathVariable("id") Long id) {
        String address = RequestContextUtils.getAddress();
        return liveMiningMapper.miner(address, id);
    }

    @Override
    public PageInfo<LiveMiningMinerDto> minerList(@ModelAttribute Page page) {
        String address = RequestContextUtils.getAddress();
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(liveMiningMapper.minerList(address));
    }

    @Override
    public LiveConfirmDto confirm(@RequestParam("address") String address, @RequestParam("id") Long id) {
        Date date = new Date();
        LiveConfirmDto build = new LiveConfirmDto();
        LiveMining liveMining = liveMiningMapper.selectByPrimaryKey(id);
        if (liveMining == null)
            // throw new BaseException("当前直播不存在 id=" + id);
            build.setStatus(1);
        else if (liveMining.getStatus() == LiveMining.STATUS_CONFIRMING)
            //throw new BaseException("当前直播区块确认中，请稍后再试");
            build.setStatus(2);
        else if (liveMining.getStatus() == LiveMining.STATUS_FINISHED || date.after(liveMining.getLiveEndTime()))
            // throw new BaseException("当前直播时间已过，请重新创建直播");
            build.setStatus(3);
        else if (DateUtil.offsetSecond(date, 5).before(liveMining.getLiveStartTime())) {
            //throw new BaseException("请在 " + DateUtil.formatDateTime(liveMining.getLiveStartTime()) + " 开播");
            build.setStatus(4);
        } else if (!address.equalsIgnoreCase(liveMining.getAddress()))
            //throw new BaseException("您不是主播，无法开播");
            build.setStatus(5);
        else {
            build.setStatus(0);
            RBucket<String> bucket = redis.getBucket(CacheKey.Live.LIVE_PUSH_URL.getKey() + id);
            if (bucket.isExists()) {
                build.setPushUrl(bucket.get());
                return build;
            }
            LiveMiningStream stream = liveMiningStreamMapper.getByLiveMiningId(liveMining.getId());
            if (stream == null) {
                generateStreamUrl(liveMining);
                stream = liveMiningStreamMapper.getByLiveMiningId(liveMining.getId());
            }
            bucket.set(stream.getPushUrl(), DateUtil.between(date, liveMining.getLiveEndTime(), DateUnit.SECOND), TimeUnit.SECONDS);
            build.setPushUrl(stream.getPushUrl());
        }
        return build;
    }

    @Override
    public void importToIm(@RequestParam("address") String address) {
    }

    @Override
    public void createAvChatRoom(@RequestParam("id") Long id) {
    }

    @Override
    public void left(@RequestParam("address") String address, @RequestParam("liveMiningId") Long liveMiningId) {
        RBucket<LiveMining> open = redis.getBucket(CacheKey.Live.LIVE_MINING.getKey() + liveMiningId);
        if (open.isExists()) {
            LiveMining liveMining = open.get();
            if (liveMining.getAddress().equalsIgnoreCase(address)) {
                imUtils.pushNotice(liveMiningId, ImGroupPushMessage.builder().type(LiveMiningChatRoomPushEnum.LIVE_MINING_DISCONNECT).data("主播离开了直播间").build());
            }
        }
    }

    @Override
    public LiveMining getById(@RequestParam("liveMiningId") Long liveMiningId) {
        return liveMiningMapper.selectByPrimaryKey(liveMiningId);
    }

    public void push(List<String> addresses, String nickName, LiveMining lm, int batch) {
        if (CollectionUtils.isEmpty(addresses))
            return;
        User user = userService.getByAddress(lm.getAddress());
        pushService.send(PushMessageDetail.builder()
                .type(PushMessageTypeEnum.SYSTEM).subType(PushMessageSubTypeEnum.System.LIVE_INVITE.name())
                .pushEnum(AliyunPushEnum.NOTICE).addresses(addresses)
                .title(nickName + "邀请您观看直播")
                .content(lm.getTitle() + "...点击查看")
                .data(LivePushInviteDto.builder()
                        .address(lm.getAddress()).amount(lm.getAmount()).invite(false).title(lm.getTitle()).headImg(user.getHeadImg()).nickName(user.getNickName())
                        .liveMiningId(lm.getId()).startTime(lm.getLiveStartTime()).endTime(lm.getLiveEndTime()).build())
                .build());
        log.info("【直播挖矿】发送邀请推送，batch={}，size={}，live={}", batch, addresses.size(), lm);
    }

    public static boolean lessThan(BigDecimal amount) {
        return amount.compareTo(BigDecimal.valueOf(0.01)) < 0;
    }
}
