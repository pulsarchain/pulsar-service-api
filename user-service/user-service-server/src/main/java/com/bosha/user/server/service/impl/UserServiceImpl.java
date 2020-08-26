package com.bosha.user.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.bosha.common.api.entity.SystemConfig;
import com.bosha.common.api.service.PushConfigService;
import com.bosha.common.api.service.SystemConfigService;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.config.EnvConfig;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.dto.Page;
import com.bosha.commons.dto.RequestInfo;
import com.bosha.commons.enums.UserClientTypeEnum;
import com.bosha.commons.enums.UserTypeEnum;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.third.weixinpush.WeixinPush;
import com.bosha.commons.third.weixinpush.autoconfiguration.WeixinPushProperties;
import com.bosha.commons.utils.AddressValidator;
import com.bosha.commons.utils.BeanUtils;
import com.bosha.commons.utils.GoogleGenerator;
import com.bosha.commons.utils.JWTUtil;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.contract.api.entity.TransferTask;
import com.bosha.contract.api.service.TransferService;
import com.bosha.finance.api.dto.request.MiningDetailDto;
import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import com.bosha.finance.api.service.MiningDetailService;
import com.bosha.star.api.service.LiveMiningService;
import com.bosha.user.api.dto.BindGoogleSecretDto;
import com.bosha.user.api.dto.UserBindDto;
import com.bosha.user.api.dto.UserBindResultDto;
import com.bosha.user.api.dto.UserDto;
import com.bosha.user.api.dto.UserInfoDto;
import com.bosha.user.api.dto.UserInviteConfigDto;
import com.bosha.user.api.dto.UserInviteDto;
import com.bosha.user.api.dto.UserInviteInfoDto;
import com.bosha.user.api.entity.Authentication;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.entity.UserInviteRecord;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.bosha.user.api.service.CreditScoreService;
import com.bosha.user.api.service.UserService;
import com.bosha.user.server.config.UserServiceConfig;
import com.bosha.user.server.mapper.AuthenticationMapper;
import com.bosha.user.server.mapper.AuxiliaryAuthenticationMapper;
import com.bosha.user.server.mapper.UserInviteRecordMapper;
import com.bosha.user.server.mapper.UserMapper;
import com.bosha.user.server.redis.CacheKey;
import com.bosha.user.server.utils.IPUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
 * @DESCRIPTION: UserServiceImpl
 * @Author liqingping
 * @Date 2019-11-28 15:14
 */
@Slf4j
@RestController
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private PushConfigService pushConfigService;
    @Autowired
    private UserServiceConfig userServiceConfig;
    @Autowired
    private UserInviteRecordMapper userInviteRecordMapper;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private MiningDetailService miningDetailService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private WeixinPushProperties weixinPushProperties;
    @Autowired
    private CreditScoreService creditScoreService;
    @Autowired
    private AuxiliaryAuthenticationMapper auxiliaryAuthenticationMapper;
    @Autowired
    private AuthenticationMapper authenticationMapper;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private LiveMiningService liveMiningService;

    public static final String HEAD_IMG = "http://bosha.oss-cn-hongkong.aliyuncs.com/pulsar/prod/8a864b688b3e46bfb559db6fcb98fb00";

    @Override
    public User getById(@RequestParam("id") Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User check(@RequestParam("id") Long userId) {
        User user = getById(userId);
        if (user == null)
            throw new BaseException(UserErrorMessageEnum.USER_NOT_FOUND);
        return user;
    }

    @Override
    public User check(@RequestParam("address") String address) {
        User user = getByAddress(address);
        if (user == null)
            throw new BaseException(UserErrorMessageEnum.USER_NOT_FOUND);
        return user;
    }

    @Override
    public User getByAddress(@PathVariable("address") String address) {
        RBucket<User> bucket = redis.getBucket(CacheKey.User.USER.getKey() + address);
        if (bucket.isExists())
            return bucket.get();
        User user = userMapper.getByAddress(address);
        if (user == null)
            return null;
        bucket.set(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindGoogleSecret(@RequestBody @Validated BindGoogleSecretDto google) {
        boolean check = GoogleGenerator.check_code(google.getGoogleSecret(), google.getCode());
        if (!check)
            throw new BaseException(UserErrorMessageEnum.GOOGLE_CODE_ERROR);
        Long userId = RequestContextUtils.getUserId();
        User user = check(userId);
        user.setGoogleSecret(google.getGoogleSecret());
        return cacheService.updateUserByPrimaryKeySelective(user) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonDistributedLock(key = "#bind.address", waitTime = 15, leaseTime = 15,
            automaticRelease = false, failStrategy = RedissonDistributedLock.FailStrategy.RETURN_NULL)
    public UserBindResultDto bind(@RequestBody @Validated UserBindDto bind) {
        RequestInfo requestInfo = RequestContextUtils.getRequestInfo();
        User user = userMapper.getByAddress(bind.getAddress());
        boolean isBind = false;
        if (user == null) {
            user = User.builder()
                    .address(bind.getAddress())
                    .registerIp(requestInfo.getIp())
                    .createTime(new Date())
                    .deviceId(requestInfo.getDeviceId())
                    .source(requestInfo.getClientType().getType())
                    .status(User.STATUS_AVAILABLE)
                    .headImg(HEAD_IMG)
                    .nickName(bind.getAddress().subSequence(0, 10).toString())
                    .userClientType(requestInfo.getClientType().getType())
                    .type(UserTypeEnum.UNVERIFIED.getType()).build();
            userMapper.insertSelective(user);
            pushConfigService.insert(user.getAddress());
            RBucket<User> bucket = redis.getBucket(CacheKey.User.USER.getKey() + bind.getAddress());
            bucket.set(user);
            GlobalExecutorService.executorService.execute(() -> {
                creditScoreService.add(bind.getAddress());
                if (EnvConfig.isProd() && new Date().before(DateUtil.parse("2020-10-01 00:00:00")))
                    WeixinPush.push(weixinPushProperties, "【新用户绑定】：\n" +
                            "address=" + bind.getAddress()
                            + "\nip=" + IPUtil.address(requestInfo.getIp()) +
                            "\nappType=" + requestInfo.getClientType());
            });
        } else {
            if (StringUtils.isBlank(user.getRegisterIp())) {
                user.setRegisterIp(requestInfo.getIp());
            }
            user.setSource(requestInfo.getClientType().getType());
            user.setUserClientType(requestInfo.getClientType().getType());
            user.setDeviceId(requestInfo.getDeviceId());
            cacheService.updateUserByPrimaryKeySelective(user);
            isBind = true;
        }
        UserDto result = UserDto.builder()
                .userId(user.getId()).googleSecret(user.getGoogleSecret()).headImg(user.getHeadImg())
                .nickName(user.getNickName()).type(user.getType()).bind(isBind).enableGoogleSecret(Boolean.TRUE.equals(user.getEnableGoogleSecret()))
                .build();
        String token = jwtUtil.sign(user.getId(), user.getAddress(), requestInfo.getClientType(), UserTypeEnum.userType(user.getType()),
                requestInfo.getVersionStr(), requestInfo.getDeviceId(), EnvConfig.ACTIVE);
        return UserBindResultDto.builder().token(token).user(result).build();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedissonDistributedLock(key = "@address")
    public boolean update(@RequestBody UserInfoDto info) {
        RequestInfo requestInfo = RequestContextUtils.getRequestInfo();
        User user = check(requestInfo.getUserId());
        if (requestInfo.getClientType() == UserClientTypeEnum.Android || requestInfo.getClientType() == UserClientTypeEnum.IOS)
            user.setUserClientType(requestInfo.getClientType().getType());
        user.setNickName(info.getNickName());
        user.setHeadImg(info.getHeadImg());
        user.setEnableGoogleSecret(info.getEnableGoogleSecret());
        boolean update = false;
        if (userInviteRecordMapper.beInviteCount(user.getAddress()) == 0 && StringUtils.isNotBlank(info.getInviteAddress())) {
            if (!AddressValidator.isValid(info.getInviteAddress()))
                throw new BaseException(UserErrorMessageEnum.ADDRESS_ERROR);
            if (user.getAddress().equalsIgnoreCase(info.getInviteAddress()))
                throw new BaseException(UserErrorMessageEnum.CAN_NOT_INVITE_YOUR_SELF);
            User address = userMapper.getByAddress(info.getInviteAddress());
            if (address == null)
                throw new BaseException(UserErrorMessageEnum.INVITE_CODE_ERROR);
            UserInviteDto inviteInfo = inviteInfo(address.getId());
            BigDecimal reward = null;
            boolean success = false;
            if (inviteInfo.getInviteInfo().getDayTotal().compareTo(inviteInfo.getInviteConfig().getRewardPerDay()) >= 0) {
                log.info("用户{} 每日邀请好友={} \n奖励={} 大于等于系统配置{}，不再继续发放奖励",
                        info.getInviteAddress(), user.getAddress(), inviteInfo.getInviteInfo().getDayTotal(), inviteInfo.getInviteConfig().getRewardPerDay());
            } else {
                if (inviteInfo.getInviteInfo().getMonthTotal().compareTo(inviteInfo.getInviteConfig().getRewardPerMonth()) >= 0) {
                    log.info("用户{} 每月邀请好友={} \n奖励={} 大于等于系统配置{}，不再继续发放奖励",
                            info.getInviteAddress(), user.getAddress(), inviteInfo.getInviteInfo().getMonthTotal(), inviteInfo.getInviteConfig().getRewardPerMonth());
                } else {
                    int countByIp = userInviteRecordMapper.countByIp(requestInfo.getIp(), DateUtil.offsetDay(new Date(), -3), new Date());
                    if (countByIp < 2) {
                        reward = inviteInfo.getInviteConfig().getReward();
                        log.info("给用户={} 发放邀请={} 奖励={}", info.getInviteAddress(), user.getAddress(), reward);
                        success = true;
                    }
                }
            }
            UserInviteRecord record = UserInviteRecord.builder().coinId(1L)
                    .contractAddress(userServiceConfig.getInviteFriendContractAddress()).beInviteUserIp(requestInfo.getIp())
                    .beInviteUserId(user.getId()).beInviteUserAddress(user.getAddress()).userAddress(info.getInviteAddress()).userId(address.getId()).createTime(new Date()).amount(reward)
                    .build();
            try {
                int inviteCount = userInviteRecordMapper.count(info.getInviteAddress());
                userInviteRecordMapper.insertSelective(record);
                if (address.getType() == 2) {
                    int count = auxiliaryAuthenticationMapper.countSuccessAuxiliary(info.getInviteAddress());
                    if ((inviteCount + 1) >= 5 && count >= 5) {
                        Authentication authentication = authenticationMapper.getByAddress(info.getInviteAddress());
                        if (authentication != null && authentication.getStatus() == Authentication.STATUS_AUXILIARY_AUTHENTICATION_ING) {
                            authentication.setStatus(Authentication.STATUS_SUCCESS);
                            authentication.setUpdateTime(new Date());
                            authenticationMapper.updateByPrimaryKey(authentication);
                            address.setType(authentication.getType());
                            cacheService.updateUserByPrimaryKeySelective(address);
                            log.info("【完全认证-邀请地址】成功，address={}", info.getInviteAddress());
                        }
                    }
                }
            } catch (DuplicateKeyException e) {
            }
            if (success) {
                Long detail = miningDetailService.insertMiningDetail(MiningDetailDto.builder()
                        .relatedId(record.getId())
                        .userId(record.getUserId())
                        .status(0)
                        .money(reward)
                        .contractAddress(userServiceConfig.getInviteFriendContractAddress())
                        .address(record.getUserAddress())
                        .serviceType(FinanceServiceTypeEnum.INVITATION)
                        .build());

                TransferTask transferTask = TransferTask.builder()
                        .amount(reward)
                        .relatedId(detail)
                        .serviceType(FinanceServiceTypeEnum.INVITATION.getType())
                        .toAddress(record.getUserAddress())
                        .build();
                Long transferId = transferService.transfer(transferTask);
                log.info("增加转币任务 id={}，task={}", transferId, JSON.toJSONString(transferTask));
            }
            update = true;
        }
        boolean b = cacheService.updateUserByPrimaryKeySelective(user) > 0;
        return update || b;
    }

    @Override
    public UserInviteDto inviteInfo(@RequestParam("userId") Long userId) {
        SystemConfig config = systemConfigService.getSystemConfig(null);
        UserInviteInfoDto inviteInfo = userInviteRecordMapper.inviteCount(userId, DateUtil.date());
        UserInviteConfigDto inviteConfig = BeanUtils.copyProperties(config.getInviteFriendConfig(), UserInviteConfigDto.class);
        return UserInviteDto.builder().inviteInfo(inviteInfo).inviteConfig(inviteConfig).build();
    }

    @Override
    public boolean verifyGoogleSecret(@RequestParam("userId") Long userId, @RequestParam("code") String code) {
        User check = check(userId);
        String googleSecret = check.getGoogleSecret();
        if (StringUtils.isBlank(googleSecret))
            return false;
        return GoogleGenerator.check_code(googleSecret, code);
    }

    @Override
    public PageInfo<UserInviteRecord> inviteRecord(@ModelAttribute Page page, @RequestParam("userId") Long userId) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(userInviteRecordMapper.list(userId));
    }

    @Override
    public boolean updateInviteRecord(@RequestBody UserInviteRecord userInviteRecord) {
        return userInviteRecordMapper.updateByPrimaryKeySelective(userInviteRecord) > 0;
    }

    @Override
    public Map<String, Long> addressToIds(@RequestBody List<String> address) {
        Map<String, Long> map = new HashMap<>();
        if (CollectionUtils.isEmpty(address))
            return map;
        userMapper.addressToIds(address).forEach(a -> map.put(a.getAddress(), a.getId()));
        return map;
    }

    @Override
    public Map<Long, String> idToAddresses(@RequestBody List<Long> uids) {
        Map<Long, String> map = new HashMap<>();
        if (CollectionUtils.isEmpty(uids))
            return map;
        userMapper.idToAddresses(uids).forEach(a -> map.put(a.getId(), a.getAddress()));
        return map;
    }

    @Override
    public int count() {
        return userMapper.count();
    }

    @Override
    public List<String> randomAddresses(@RequestParam(value = "num", required = false) Integer num) {
        if (num == null)
            return userMapper.addresses(null, null);
        return userMapper.randomAddresses(num);
    }

    @Override
    public PageInfo<String> accounts(@RequestBody Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(userMapper.addressList());
    }

    @Override
    public int inviteCount(@RequestParam("address") String address) {
        return userInviteRecordMapper.count(address);
    }
}
