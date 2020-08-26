package com.bosha.user.server.service.impl;

import java.util.Date;


import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.exception.BaseException;
import com.bosha.user.api.dto.QueryCreditScoreRangeDto;
import com.bosha.user.api.entity.AuthorizedCreditScore;
import com.bosha.user.api.entity.CreditScore;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.bosha.user.api.service.CreditScoreService;
import com.bosha.user.api.service.UserService;
import com.bosha.user.server.mapper.AuthorizedCreditScoreMapper;
import com.bosha.user.server.mapper.CreditScoreMapper;
import com.bosha.user.server.redis.CacheKey;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CreditScoreServiceImpl
 * @Author liqingping
 * @Date 2020-02-09 16:53
 */
@Slf4j
@RestController
public class CreditScoreServiceImpl implements CreditScoreService {


    @Autowired
    private CreditScoreMapper creditScoreMapper;
    @Autowired
    private AuthorizedCreditScoreMapper authorizedCreditScoreMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private UserService userService;

    @Override
    public CreditScore getByAddress(@PathVariable("address") String address) {
        RMapCache<String, CreditScore> mapCache = redis.getMapCache(CacheKey.User.CREDIT_SCORE_MAP.getKey());
        CreditScore creditScore = mapCache.get(address);
        if (creditScore != null)
            return creditScore;
        creditScore = creditScoreMapper.getByAddress(address);
        if (creditScore == null) {
            add(address);
            creditScore = creditScoreMapper.getByAddress(address);
        }
        mapCache.put(address, creditScore, CacheKey.User.CREDIT_SCORE_MAP.getExpireTime(), CacheKey.User.CREDIT_SCORE_MAP.getTimeUnit());
        return creditScore;
    }

    @Override
    @RedissonDistributedLock(key = "#address")
    public Long add(@RequestParam("address") String address) {
        if (userService.getByAddress(address) == null)
            return null;
        CreditScore creditScore = creditScoreMapper.getByAddress(address);
        if (creditScore != null)
            return creditScore.getId();
        creditScore = CreditScore.builder().address(address).createTime(new Date()).updateTime(new Date()).score(0).build();
        creditScoreMapper.insertSelective(creditScore);
        return creditScore.getId();
    }

    @Override
    @RedissonDistributedLock(key = {"#authorizedCreditScore.from", "#authorizedCreditScore.to"})
    public Long addAuthorizedCreditScore(@RequestBody AuthorizedCreditScore authorizedCreditScore) {
        AuthorizedCreditScore ac = authorizedCreditScoreMapper.getByFromAndTo(authorizedCreditScore.getFrom(), authorizedCreditScore.getTo());
        if (ac == null) {
            authorizedCreditScoreMapper.insertSelective(authorizedCreditScore);
            return authorizedCreditScore.getId();
        }
        if (ac.getExpireTime() == null || ac.getStatus() == AuthorizedCreditScore.STATUS_CONFIRMING) {
            return ac.getId();
        }
        if (ac.getExpireTime().before(new Date())) {
            ac.setStatus(AuthorizedCreditScore.STATUS_EXPIRED);
            authorizedCreditScoreMapper.updateByPrimaryKey(ac);
        }
        return ac.getId();
    }

    @Override
    public CreditScore view(@PathVariable("address") String address, @RequestParam("userAddress") String userAddress) {
        AuthorizedCreditScore ac = authorizedCreditScoreMapper.getByFromAndTo(userAddress, address);
        if (ac == null)
            throw new BaseException(UserErrorMessageEnum.VIEW_CREDIT_SCORE_BEFORE_TRANSFER);
        if (ac.getExpireTime() == null || ac.getStatus() == AuthorizedCreditScore.STATUS_CONFIRMING)
            throw new BaseException(UserErrorMessageEnum.VIEW_CREDIT_SCORE_TRANSFER_CONFIRMING);
        if (ac.getExpireTime().before(new Date())) {
            ac.setStatus(AuthorizedCreditScore.STATUS_EXPIRED);
            authorizedCreditScoreMapper.updateByPrimaryKey(ac);
            throw new BaseException("授权有效期已过，请重新获取");
        }
        return getByAddress(address);
    }

    @Override
    public PageInfo<String> listByScore(@RequestBody QueryCreditScoreRangeDto query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        return PageInfo.of(creditScoreMapper.getByRange(query.getMin(), query.getMax(), query.getTime()));
    }

    @Override
    public int countByRange(@RequestParam("min") int min, @RequestParam("max") int max) {
        return creditScoreMapper.countByRange(min, max);
    }
}
