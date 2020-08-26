package com.bosha.star.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.BeanUtils;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.commons.utils.StrUtils;
import com.bosha.contract.api.entity.ContractExplorerConfirmTask;
import com.bosha.contract.api.entity.ContractTransferTask;
import com.bosha.contract.api.enums.ContractType;
import com.bosha.contract.api.service.ContractAddressPoolService;
import com.bosha.contract.api.service.ContractExplorerService;
import com.bosha.contract.api.service.ContractTransferTaskService;
import com.bosha.finance.api.entity.ContractMiningDetail;
import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import com.bosha.finance.api.service.ContractMiningDetailService;
import com.bosha.star.api.dto.server.InsertStarRewardCharityDto;
import com.bosha.star.api.dto.server.UpdateStarRewardDto;
import com.bosha.star.api.dto.web.CreateStarDto;
import com.bosha.star.api.dto.web.JoinStarDto;
import com.bosha.star.api.dto.web.JoinStarResultDto;
import com.bosha.star.api.dto.web.MyRecommendDto;
import com.bosha.star.api.dto.web.QueryStarListDto;
import com.bosha.star.api.dto.web.StarDetailDto;
import com.bosha.star.api.dto.web.StarListDto;
import com.bosha.star.api.entity.Star;
import com.bosha.star.api.entity.StarMember;
import com.bosha.star.api.entity.StarReward;
import com.bosha.star.api.enums.LevelEnum;
import com.bosha.star.api.enums.StarErrorMessageEnum;
import com.bosha.star.api.service.StarService;
import com.bosha.star.server.config.StarServiceConfig;
import com.bosha.star.server.mapper.StarMapper;
import com.bosha.star.server.mapper.StarMemberMapper;
import com.bosha.star.server.mapper.StarRewardMapper;
import com.bosha.star.server.redis.CacheKey;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: StarServiceImpl
 * @Author liqingping
 * @Date 2020-03-05 15:47
 */
@RestController
@Slf4j
public class StarServiceImpl implements StarService {

    public static final BigDecimal SCALE_CREATE_STAR_CHARITY_ADDRESS_60 = BigDecimal.valueOf(0.6);

    public static final BigDecimal SCALE_JOIN_RECOMMEND_40 = BigDecimal.valueOf(0.4);

    public static final BigDecimal SCALE_JOIN_EQUAL_DIVIDE_40 = BigDecimal.valueOf(0.4);

    public static final BigDecimal SCALE_JOIN_CHARITY_ADDRESS_12 = BigDecimal.valueOf(0.12);

    public static final BigDecimal SCALE_JOIN_PLATFORM_ADDRESS_8 = BigDecimal.valueOf(0.08);

    public static final BigDecimal SCALE_JOIN_CHARITY_ADDRESS_52 = BigDecimal.valueOf(0.52);

    @Autowired
    private StarMapper starMapper;
    @Autowired
    private StarMemberMapper starMemberMapper;
    @Autowired
    private ContractExplorerService contractExplorerService;
    @Autowired
    private StarServiceConfig starServiceConfig;
    @Autowired
    private PushService pushService;
    @Autowired
    private StarRewardMapper starRewardMapper;
    @Autowired
    private ContractTransferTaskService contractTransferTaskService;
    @Autowired
    private ContractMiningDetailService contractMiningDetailService;
    @Autowired
    private ContractAddressPoolService contractAddressPoolService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedissonClient redis;

    @Override
    @RedissonDistributedLock(key = "@address")
    @Transactional(rollbackFor = Exception.class)
    public Long create(@RequestBody CreateStarDto create) {
        String address = RequestContextUtils.getAddress();
        log.info("【创建星系】address={}，create={}", address, create);
        if (exist(address) != null)
            throw new BaseException(StarErrorMessageEnum.ONE_ADDRESS_ONE_STAR);
        Star star = new Star();
        Date date = new Date();
        LevelEnum level = getLevel(create.getValue());
        star.setAddress(address);
        star.setCharityAddress(starServiceConfig.getCharityAddress());
        star.setContractAddress(contractAddressPoolService.get(ContractType.STAR));
        star.setCreateTime(date);
        star.setName(create.getName());
        star.setLogo(create.getLogo());
        star.setSlogan(create.getSlogan());
        star.setHash(create.getHash());
        star.setSystemAddress(create.getSystemAddress());
        star.setTotalHz(hz(level, create.getValue()));
        star.setStatus(Star.STATUS_CONFIRMING);
        star.setCreateFee(create.getValue());
        starMapper.insertSelective(star);

        StarMember starMember = new StarMember();
        starMember.setStarId(star.getId());
        starMember.setAddress(address);
        starMember.setInitLevel(level.getLevel());
        starMember.setCurrentLevel(level.getLevel());
        starMember.setCreateTime(date);
        starMember.setInitFee(create.getValue());
        starMember.setCurrentHz(hz(level, create.getValue()));
        starMember.setInitHz(hz(level, create.getValue()));
        starMember.setSystemAddress(create.getSystemAddress());
        starMember.setHash(create.getHash());
        starMember.setStatus(StarMember.STATUS_CONFIRMING);
        starMemberMapper.insertSelective(starMember);

        contractExplorerService.add(ContractExplorerConfirmTask.builder()
                .address(address).contractAddress(star.getContractAddress()).systemAddress(create.getSystemAddress()).amount(create.getValue())
                .contractType(ContractType.STAR.getType()).hash(create.getHash()).relatedId(star.getId())
                .build());
        GlobalExecutorService.executorService.execute(() -> pushService.send(PushMessageDetail.builder()
                .type(PushMessageTypeEnum.STAR).subType(PushMessageSubTypeEnum.Star.STAR_CREATE_CONFIRMING.name())
                .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(address))
                .title("您创建的星系当前区块确认中")
                .content(StrUtils.cutStr(create.getName(), 10) + "，当前区块确认中...请稍后查看")
                .data(star.getId())
                .build()));
        log.info("【创建星系】成功，star={}", star);
        return star.getId();
    }

    @Override
    @RedissonDistributedLock(key = "@address")
    @Transactional(rollbackFor = Exception.class)
    public JoinStarResultDto join(@RequestBody JoinStarDto join) {
        String address = RequestContextUtils.getAddress();
        log.info("【加入星系】address={},join={}", address, join);
        if (exist(address) != null)
            throw new BaseException(StarErrorMessageEnum.ONE_ADDRESS_ONE_STAR);
        User user = userService.getByAddress(address);
        if (user == null)
            throw new BaseException(UserErrorMessageEnum.USER_NOT_FOUND);
        Star star = starMapper.selectByPrimaryKey(join.getId());
        if (star == null)
            throw new BaseException(StarErrorMessageEnum.STAR_NOT_FOUND);
        LevelEnum level = getLevel(join.getValue());

        if (StringUtils.isNotBlank(join.getRecommendAddress())) {
            StarMember recommend = starMemberMapper.getByAddress(join.getRecommendAddress());
            if (recommend == null || !Objects.equals(recommend.getStarId(), join.getId())) {
                log.error("【加入星系】推荐地址对应的星系不存在或者不在该星系，join={}", join);
                join.setRecommendAddress(null);
            }
        }
        if ("".equals(join.getRecommendAddress())) {
            join.setRecommendAddress(null);
        }
        StarMember starMember = new StarMember();
        starMember.setStarId(star.getId());
        starMember.setAddress(address);
        starMember.setRecommendAddress(join.getRecommendAddress());
        starMember.setInitLevel(level.getLevel());
        starMember.setCurrentLevel(level.getLevel());
        starMember.setCreateTime(new Date());
        starMember.setCurrentHz(hz(level, join.getValue()));
        starMember.setInitFee(join.getValue());
        starMember.setInitHz(hz(level, join.getValue()));
        starMember.setSystemAddress(star.getSystemAddress());
        starMember.setHash(join.getHash());
        starMember.setStatus(StarMember.STATUS_CONFIRMING);
        starMemberMapper.insertSelective(starMember);

        contractExplorerService.add(ContractExplorerConfirmTask.builder()
                .address(address).contractAddress(star.getContractAddress()).systemAddress(star.getSystemAddress())
                .contractType(ContractType.STAR.getType()).hash(join.getHash()).relatedId(star.getId()).amount(join.getValue())
                .build());
        GlobalExecutorService.executorService.execute(() -> pushService.send(PushMessageDetail.builder()
                .type(PushMessageTypeEnum.STAR).subType(PushMessageSubTypeEnum.Star.STAR_JOIN_CONFIRMING.name())
                .pushEnum(AliyunPushEnum.NOTICE).addresses(Collections.singletonList(address))
                .title("您已加入" + StrUtils.cutStr(star.getName(), 10) /*+ "星系"*/)
                .content("当前区块确认中...请稍后查看")
                .data(star.getId())
                .build()));
        log.info("【加入星系】成功，starMember={}", starMember);
        return JoinStarResultDto.builder()
                .address(address).headImg(user.getHeadImg()).nickName(user.getNickName()).level(level.getLevel()).starName(star.getName())
                .build();
    }

    @Override
    public PageInfo<StarListDto> list(@ModelAttribute QueryStarListDto query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        return PageInfo.of(starMapper.list(query.getName(), RequestContextUtils.getAddress(), query.getField(), query.getOrderBy()));
    }

    @Override
    public StarDetailDto detail(@PathVariable("id") Long id, @RequestParam(value = "address", required = false, defaultValue = "") String address) {
        return starMapper.detail(id, address);
    }

    @Override
    public StarDetailDto my(@RequestParam(value = "address") String address) {
        return starMapper.my(address);
    }

    @Override
    public PageInfo<MyRecommendDto> myRecommend(@ModelAttribute Page page) {
        String address = RequestContextUtils.getAddress();
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(starMapper.myRecommend(address));
    }

    @Override
    public Integer exist(@RequestParam("address") String address) {
        StarMember member = starMemberMapper.getByAddress(address);
        if (member == null)
            return null;
        return member.getStatus();
    }

    @Override
    public Star getById(@RequestParam("id") Long id) {
        return starMapper.selectByPrimaryKey(id);
    }

    @Override
    public Star getStar(@RequestParam("address") String address) {
        return starMapper.getByAddress(address);
    }

    @Override
    public Star updateStar(@RequestBody Star star) {
        starMapper.updateByPrimaryKeySelective(star);
        return starMapper.selectByPrimaryKey(star.getId());
    }

    @Override
    public StarMember getStarMember(@RequestParam("address") String address) {
        return starMemberMapper.getByAddress(address);
    }

    @Override
    public StarMember getStarMemberFromCache(@RequestParam("address") String address) {
        RBucket<StarMember> bucket = redis.getBucket(CacheKey.Star.STAR_MEMBER.getKey() + address);
        if (bucket.isExists() && bucket.get() != null)
            return bucket.get();
        StarMember starMember = getStarMember(address);
        if (starMember == null)
            return null;
        bucket.set(starMember, CacheKey.Star.STAR_MEMBER.getExpireTime(), CacheKey.Star.STAR_MEMBER.getTimeUnit());
        return starMember;
    }

    @Override
    public BigDecimal getStarHz(@RequestParam("address") String address) {
        StarMember starMember = getStarMember(address);
        if (starMember == null)
            return BigDecimal.ZERO;
        return starMember.getCurrentHz();
    }

    @Override
    public StarMember updateStarMember(@RequestBody StarMember starMember) {
        starMemberMapper.updateByPrimaryKeySelective(starMember);
        StarMember select = starMemberMapper.selectByPrimaryKey(starMember.getId());
        redis.getBucket(CacheKey.Star.STAR_MEMBER.getKey() + select.getAddress()).delete();
        return select;
    }

    /*
     1000、3000、5000 三个档次
 *   1000 = 1000 x 1    = 1000 算力值
 *   3000 = 3000 x 1.2 = 3600 算力值
 *   5000 = 5000 x 1.5 = 7500 算力值

 * 创建星系：60% 慈善地址，40% 系统
 * 加入星系：A加入，
 *                   有推荐人：给推荐人40%，所有人平均分配40%，慈善地址12%，平台收益8%
 *                   无推荐人：所有人平均40%，慈善地址52%，平台收益8%
 *
 * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonDistributedLock(key = "#starMemberId",
            waitTime = 60,
            leaseTime = 60,
            failStrategy = RedissonDistributedLock.FailStrategy.RETURN_NULL)
    public List<StarReward> insertStarRewardBatch(@RequestParam("starMemberId") Long starMemberId) {
        long start = System.currentTimeMillis();
        StarMember starMember = starMemberMapper.selectByPrimaryKey(starMemberId);
        Star star = starMapper.selectByPrimaryKey(starMember.getStarId());
        BigDecimal initFee = starMember.getInitFee();
        BigDecimal system = initFee.multiply(SCALE_JOIN_PLATFORM_ADDRESS_8);
        StarReward starReward = StarReward.builder()
                .contractAddress(star.getContractAddress()).createTime(new Date()).starMemberId(starMemberId).status(StarReward.STATUS_CONFIRMING)
                .build();
        boolean create = starMember.getAddress().equalsIgnoreCase(star.getAddress());
        if (StringUtils.isNotBlank(starMember.getRecommendAddress())) {
            List<StarReward> list = new ArrayList<>();
            StarMember recommendStarMember = starMemberMapper.getByAddress(starMember.getRecommendAddress());
            BigDecimal recommend = initFee.multiply(SCALE_JOIN_RECOMMEND_40);
            StarReward recommendStarReward = BeanUtils.copyProperties(starReward, StarReward.class);
            recommendStarReward.setAddress(starMember.getRecommendAddress());
            recommendStarReward.setAddressType(StarReward.ADDRESS_TYPE_USER);
            recommendStarReward.setHz(recommend);
            recommendStarReward.setLevel(recommendStarMember.getCurrentLevel());
            recommendStarReward.setCurrentHz(recommendStarMember.getCurrentHz());
            list.add(recommendStarReward);

            StarReward charityStarReward = BeanUtils.copyProperties(starReward, StarReward.class);
            BigDecimal charity = initFee.multiply(SCALE_JOIN_CHARITY_ADDRESS_12);
            charityStarReward.setAddress(star.getCharityAddress());
            charityStarReward.setAddressType(StarReward.ADDRESS_TYPE_CHARITY);
            charityStarReward.setHz(charity);
            list.add(charityStarReward);

            StarReward systemStarReward = BeanUtils.copyProperties(starReward, StarReward.class);
            systemStarReward.setAddress(star.getSystemAddress());
            systemStarReward.setAddressType(StarReward.ADDRESS_TYPE_SYSTEM);
            systemStarReward.setHz(system);
            list.add(systemStarReward);

            starRewardMapper.batchInsert(list);
            log.info("【starReward】starMemberId={}，推荐人=【{}-{}】\n慈善地址=【{}-{}】\n系统=【{}-{}】", starMemberId,
                    recommendStarReward.getAddress(), recommendStarReward.getHz(),
                    charityStarReward.getAddress(), charityStarReward.getHz(),
                    systemStarReward.getAddress(), systemStarReward.getHz());
            insertFinanceAndTransfer(starMember, star, create, list, false);
        } else {
            List<StarReward> list = new ArrayList<>();
            StarReward charityStarReward = BeanUtils.copyProperties(starReward, StarReward.class);
            BigDecimal charity = initFee.multiply(SCALE_JOIN_CHARITY_ADDRESS_52);
            charityStarReward.setAddress(star.getCharityAddress());
            charityStarReward.setAddressType(StarReward.ADDRESS_TYPE_CHARITY);
            charityStarReward.setHz(charity);
            list.add(charityStarReward);

            StarReward systemStarReward = BeanUtils.copyProperties(starReward, StarReward.class);
            systemStarReward.setAddress(star.getSystemAddress());
            systemStarReward.setAddressType(StarReward.ADDRESS_TYPE_SYSTEM);
            systemStarReward.setHz(system);
            list.add(systemStarReward);

            starRewardMapper.batchInsert(list);
            log.info("【starReward】starMemberId={}，慈善地址=【{}-{}】\n系统=【{}-{}】", starMemberId,
                    charityStarReward.getAddress(), charityStarReward.getHz(),
                    systemStarReward.getAddress(), systemStarReward.getHz());
            insertFinanceAndTransfer(starMember, star, create, list, false);
        }
        BigDecimal equalTotal = initFee.multiply(SCALE_JOIN_EQUAL_DIVIDE_40);

        List<StarMember> starMembers = starMemberMapper.listDistribute(starMemberId, star.getId());
        if (CollectionUtils.isEmpty(starMembers)) {
            return new ArrayList<>();
        }
        BigDecimal avg = equalTotal.divide(BigDecimal.valueOf(starMembers.size()), 2, BigDecimal.ROUND_DOWN);
        List<StarReward> list = new ArrayList<>();
        for (StarMember member : starMembers) {
            StarReward sr = BeanUtils.copyProperties(starReward, StarReward.class);
            sr.setAddress(member.getAddress());
            sr.setAddressType(StarReward.ADDRESS_TYPE_USER);
            sr.setHz(avg);
            sr.setLevel(member.getCurrentLevel());
            sr.setCurrentHz(member.getCurrentHz());
            list.add(sr);
        }
        starRewardMapper.batchInsert(list);
        log.info("【starReward】所有人平均分配，avg={}，size={}", avg, list.size());
        insertFinanceAndTransfer(starMember, star, create, list, true);
        log.info("【starReward】批量插入执行完毕，starMemberId={}，耗时= {} ms", starMemberId, System.currentTimeMillis() - start);
        return new ArrayList<>();
    }

    private void insertFinanceAndTransfer(StarMember starMember, Star star, boolean create, List<StarReward> list, boolean r) {
        List<ContractMiningDetail> contractMiningDetailList = new ArrayList<>();
        for (StarReward reward : list) {
            FinanceServiceTypeEnum typeEnum = serviceTypeEnum(reward.getAddressType(), create, r);
            contractMiningDetailList.add(
                    ContractMiningDetail.builder()
                            .status(0).address(reward.getAddress()).amount(reward.getHz()).contractAddress(reward.getContractAddress())
                            .createTime(reward.getCreateTime()).relatedId(reward.getId()).serviceType(typeEnum.getType())
                            .remark(StrUtils.cutStr(star.getName(), 5) /*+ typeEnum.getRemark()*/)
                            .build());
        }
        Map<String, Long> financeMap = contractMiningDetailService.batchInsert(contractMiningDetailList);
        List<ContractTransferTask> taskList = new ArrayList<>();
        for (StarReward reward : list) {
            Long financeId = financeMap.get(reward.getAddress());
            taskList.add(ContractTransferTask.builder()
                    .contractAddress(reward.getContractAddress()).amount(reward.getHz()).createTime(reward.getCreateTime()).extraId(reward.getId())
                    .financeId(financeId).serviceType(serviceTypeEnum(reward.getAddressType(), create, r).getType()).systemAddress(star.getSystemAddress())
                    .status(2).toAddress(reward.getAddress())
                    .build());
        }
        contractTransferTaskService.insertBatch(taskList);
        log.info("【starReward】添加转币记录成功，starMember={}，size={}", starMember.getAddress(), taskList.size());
    }


    public FinanceServiceTypeEnum serviceTypeEnum(Integer addressType, boolean type, boolean r) {
        if (r)
            return FinanceServiceTypeEnum.STAR_INCOME_;
        switch (addressType) {
            case StarReward.ADDRESS_TYPE_USER:
                return FinanceServiceTypeEnum.STAR_INCOME_RECOMMEND;
            case StarReward.ADDRESS_TYPE_CHARITY:
                return type ? FinanceServiceTypeEnum.STAR_INCOME_CREATE_CHARITY : FinanceServiceTypeEnum.STAR_INCOME_JOIN_CHARITY;
            case StarReward.ADDRESS_TYPE_SYSTEM:
                return FinanceServiceTypeEnum.STAR_INCOME_SYSTEM;
        }
        return FinanceServiceTypeEnum.STAR_INCOME_;
    }

    @Override
    public StarReward insertStarRewardCharity(@RequestBody InsertStarRewardCharityDto insert) {
        StarMember starMember = starMemberMapper.selectByPrimaryKey(insert.getStarMemberId());
        Star star = starMapper.selectByPrimaryKey(starMember.getStarId());
        StarReward starReward = new StarReward();
        starReward.setCreateTime(new Date());
        starReward.setStatus(StarReward.STATUS_CONFIRMING);
        starReward.setContractAddress(insert.getContractAddress());
        starReward.setAddressType(StarMember.ADDRESS_TYPE_CHARITY);
        starReward.setStarMemberId(insert.getStarMemberId());
        starReward.setAddress(star.getCharityAddress());
        starReward.setHz(starMember.getInitFee().multiply(insert.getScale()));
        starRewardMapper.insertSelective(starReward);
        insertFinanceAndTransfer(starMember, star, true, Collections.singletonList(starReward), false);
        return starReward;
    }

    @Override
    public boolean updateStarRewardBatch(@RequestBody List<UpdateStarRewardDto> list) {
        if (CollectionUtils.isEmpty(list))
            return false;
        List<Long> ids = list.stream().map(UpdateStarRewardDto::getSrId).collect(Collectors.toList());
        Date date = new Date();
        starRewardMapper.updateStatusAndHashBatch(ids, list.get(0).getHash(), date);
        for (UpdateStarRewardDto dto : list) {
            StarMember sm = starMemberMapper.getByAddress(dto.getAddress());
            if (sm == null)
                continue;
            sm.setUpdateTime(date);
            sm.setCurrentHz(sm.getCurrentHz().add(dto.getAmount()));
            if (sm.getCurrentHz().compareTo(starServiceConfig.getNum().getDoubleStar()) >= 0)
                sm.setCurrentLevel(LevelEnum.DOUBLE_STAR.getLevel());
            if (sm.getCurrentHz().compareTo(starServiceConfig.getNum().getGreenMan()) >= 0)
                sm.setCurrentLevel(LevelEnum.GREEN_MAN.getLevel());
            starMemberMapper.updateByPrimaryKeySelective(sm);
            redis.getBucket(CacheKey.Star.STAR_MEMBER.getKey() + sm.getAddress()).delete();
        }
        log.info("【星系分成到账确认】增加对应的算力值，size={}", list.size());
        return true;
    }

    public LevelEnum getLevel(BigDecimal value) {
        if (value.compareTo(starServiceConfig.getNum().getStar()) == 0)
            return LevelEnum.STAR;
        if (value.compareTo(starServiceConfig.getNum().getDoubleStar()) == 0)
            return LevelEnum.DOUBLE_STAR;
        if (value.compareTo(starServiceConfig.getNum().getGreenMan()) == 0)
            return LevelEnum.GREEN_MAN;
        throw new BaseException("金额错误 " + value);
    }

    public static BigDecimal hz(LevelEnum levelEnum, BigDecimal value) {
        switch (levelEnum) {
            case STAR:
                return value;
            case DOUBLE_STAR:
                return BigDecimal.valueOf(1.2).multiply(value);
            case GREEN_MAN:
                return BigDecimal.valueOf(1.5).multiply(value);
        }
        return value;
    }
}
