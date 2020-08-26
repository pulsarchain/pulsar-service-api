package com.bosha.user.server.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.user.api.dto.BlockDto;
import com.bosha.user.api.entity.Authentication;
import com.bosha.user.api.entity.AuthorizedCreditScore;
import com.bosha.user.api.entity.AuxiliaryAuthentication;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.service.AccountArrivalNoticeService;
import com.bosha.user.api.service.UserService;
import com.bosha.user.server.config.UserServiceConfig;
import com.bosha.user.server.mapper.AuthenticationMapper;
import com.bosha.user.server.mapper.AuthorizedCreditScoreMapper;
import com.bosha.user.server.mapper.AuxiliaryAuthenticationMapper;
import com.bosha.user.server.mapper.UserInviteRecordMapper;
import com.bosha.user.server.redis.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AccountArrivalNoticeService
 * @Author liqingping
 * @Date 2020-02-11 10:54
 */
@Service
@Slf4j
public class AccountArrivalNoticeServiceImpl implements AccountArrivalNoticeService {

    public static final BigDecimal VALUE = new BigDecimal("1000000000000000000");

    @Autowired
    private AuthenticationMapper authenticationMapper;
    @Autowired
    private AuxiliaryAuthenticationMapper auxiliaryAuthenticationMapper;
    @Autowired
    private AuthorizedCreditScoreMapper authorizedCreditScoreMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PushService pushService;
    @Autowired
    private UserServiceConfig userServiceConfig;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private UserInviteRecordMapper userInviteRecordMapper;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void confirm(BlockDto block) {
        RScoredSortedSet<Long> scoredSortedSet = redis.getScoredSortedSet(CacheKey.User.BLOCK_NEW_ARRIVAL.getKey());
        if (scoredSortedSet.isExists()) {
            if (scoredSortedSet.contains(block.getBlockNumber())) {
                return;
            }
            Long first = scoredSortedSet.first();
            if (first != null && block.getBlockNumber() <= first)
                return;
        }
        scoredSortedSet.add(block.getBlockNumber(), block.getBlockNumber());
        List<BlockDto.TransactionDto> transactions = block.getTransactions();
        for (BlockDto.TransactionDto transaction : transactions) {
            String to = transaction.getTo();
            String from = transaction.getFrom();
            String hash = transaction.getHash();
            if (StringUtils.isAnyBlank(to, from))
                continue;
            BigDecimal money = transaction.getValue().divide(VALUE);
            if (money.compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("【到账通知】金额为 0， hash={}", hash);
                continue;
            }
            Date time = new Date(Long.parseLong(transaction.getTimestamp() + "000"));
            transaction.setTransactionTime(time);
            transaction.setAmount(money);
            log.debug("【到账通知】to={}，from={}\nmoney={}，time={}，hash={}", to, from, money, time, hash);
            GlobalExecutorService.executorService.execute(() -> {
                User user = userService.getByAddress(to);
                if (user == null)
                    return;
                SendResult sendResult = rocketMQTemplate.syncSend(BlockDto.TransactionDto.TOPIC, transaction);
                if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
                    throw new RuntimeException("发送到账通知到 MQ 失败：" + sendResult + "\n" + transaction);
                }
                log.info("【到账通知】发送消息到 mq：result={}，{}", sendResult.getMsgId(), transaction);
            });
            User user = userService.getByAddress(to);
            User fromUser = userService.getByAddress(from);
            if (fromUser == null && user == null)
                return;
            GlobalExecutorService.executorService.execute(() -> {
                //③：到账通知
                if (user != null) {
                    //    String pul = money.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
                    pushService.send(PushMessageDetail.builder()
                            .type(PushMessageTypeEnum.CHAIN).subType(PushMessageSubTypeEnum.Chain.PUL_ARRIVAL_NOTICE.name())
                            .pushEnum(AliyunPushEnum.NOTICE)
                            .addresses(Collections.singletonList(to))
                            .pushTitle("数字身份到账通知")
                            .title("您的数字身份到账金额：" + money + "PUL，点击查看")
                            .content("您的数字身份到账金额：" + money + "PUL，点击查看")
                            .data("")
                            .build());
                }
            });

            GlobalExecutorService.executorService.execute(() -> {
                //0：认证 给系统地址打币
                out:
                if (to.equalsIgnoreCase(userServiceConfig.getAuthentication().getSystemAddress())) {
                    Authentication authentication = authenticationMapper.getByAddress(from);
                    if (authentication == null)
                        break out;
                    if (authentication.getStatus() == Authentication.STATUS_SELF_CONFIRMING
                            || authentication.getStatus() == Authentication.STATUS_TO_BE_TRANSFER) {
                        authentication.setStatus(Authentication.STATUS_SELF_SUCCESS);
                        authentication.setUpdateTime(new Date());
                        authenticationMapper.updateByPrimaryKey(authentication);
                        log.info("【自我认证】成功，address={}，to={}，金额={}，hash={}", from, to, money.toPlainString(), transaction.getHash());
                    }
                    //  return;
                }
                if (fromUser == null || user == null)
                    return;
                //①：辅助认证
                AuxiliaryAuthentication auxiliaryAuthentication = auxiliaryAuthenticationMapper.getByAddressAndAuxiliary(to, from);
                if (auxiliaryAuthentication != null) {
                    auxiliaryAuthentication.setMoney(money);
                    auxiliaryAuthentication.setUpdateTime(time);
                    auxiliaryAuthentication.setHash(hash);
                    auxiliaryAuthenticationMapper.updateByPrimaryKey(auxiliaryAuthentication);
                    log.info("【辅助认证】to={}，from={}，到账金额{}，hash={}", to, from, money.toPlainString(), transaction.getHash());
                    Authentication authentication = authenticationMapper.selectByPrimaryKey(auxiliaryAuthentication.getAuId());
                    // 处于待辅助认证的状态
                    if (authentication != null && authentication.getStatus() == Authentication.STATUS_AUXILIARY_AUTHENTICATION_ING) {
                        int count = auxiliaryAuthenticationMapper.countSuccessAuxiliary(to);
                        // 统计是否达到5个
                        if (count >= 5 && authentication.getAddress().equalsIgnoreCase(to) && userInviteRecordMapper.count(to) >= 5) {
                            authentication.setStatus(Authentication.STATUS_SUCCESS);
                            authentication.setUpdateTime(new Date());
                            authenticationMapper.updateByPrimaryKey(authentication);
                            user.setType(authentication.getType());
                            cacheService.updateUserByPrimaryKeySelective(user);
                            log.info("【完全认证】成功，address={}", to);
                        }
                    }
                }
                // ②：授权信用分
                AuthorizedCreditScore acs = authorizedCreditScoreMapper.getByFromAndTo(from, to);
                if (acs != null && acs.getStatus() == AuthorizedCreditScore.STATUS_CONFIRMING) {
                    acs.setStatus(AuthorizedCreditScore.STATUS_ARRIVAL);
                    acs.setUpdateTime(time);
                    acs.setHash(hash);
                    DateTime offsetDay = DateUtil.offsetDay(time, 7);
                    acs.setExpireTime(offsetDay);
                    authorizedCreditScoreMapper.updateByPrimaryKey(acs);
                    log.info("【授权信用分】to={}，from={}，到账金额{}，hash={}，过期时间={}", to, from, money.toPlainString(), transaction.getHash(), offsetDay);
                }
            });
        }

        if (scoredSortedSet.size() > 10) {
            scoredSortedSet.removeRangeByRank(0, scoredSortedSet.size() - 11);
        }
    }

}
