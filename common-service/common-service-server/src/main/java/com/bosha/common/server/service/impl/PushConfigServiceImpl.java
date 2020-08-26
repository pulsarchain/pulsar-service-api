package com.bosha.common.server.service.impl;

import com.bosha.common.api.entity.PushConfig;
import com.bosha.common.api.service.PushConfigService;
import com.bosha.common.server.mapper.PushConfigMapper;
import com.bosha.common.server.redis.CacheKey;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.user.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: PushConfigServiceImpl
 * @Author liqingping
 * @Date 2019-12-12 12:23
 */
@RestController
@Slf4j
public class PushConfigServiceImpl implements PushConfigService {

    @Autowired
    private PushConfigMapper pushConfigMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(@RequestParam("address") String address) {
        PushConfig pushConfig = PushConfig.builder().address(address)
                .all(true).system(true).price(true).chain(true).star(true).dapp(true).notice(true)
                .build();
        try {
            if (userService.getByAddress(address) == null)
                return false;
            pushConfigMapper.insertSelective(pushConfig);
            RMapCache<String, PushConfig> mapCache = redis.getMapCache(CacheKey.Common.PUSH_CONFIG_MAP.getKey());
            mapCache.put(RequestContextUtils.getAddress(), pushConfig, CacheKey.Common.PUSH_CONFIG_MAP.getExpireTime(),
                    CacheKey.Common.PUSH_CONFIG_MAP.getTimeUnit());
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean update(@RequestBody PushConfig pushConfig) {
        pushConfigMapper.updateByAddressSelective(pushConfig);
        PushConfig pc = pushConfigMapper.getByAddress(pushConfig.getAddress());
        if (pc == null)
            throw new BaseException("推送记录不存在");
        RMapCache<String, PushConfig> mapCache = redis.getMapCache(CacheKey.Common.PUSH_CONFIG_MAP.getKey());
        mapCache.put(pushConfig.getAddress(), pc, CacheKey.Common.PUSH_CONFIG_MAP.getExpireTime(), CacheKey.Common.PUSH_CONFIG_MAP.getTimeUnit());
        return true;
    }

    @Override
    public PushConfig getByAddress(@PathVariable("address") String address) {
        RMapCache<String, PushConfig> mapCache = redis.getMapCache(CacheKey.Common.PUSH_CONFIG_MAP.getKey());
        PushConfig pushConfig = mapCache.get(address);
        if (pushConfig == null) {
            pushConfig = pushConfigMapper.getByAddress(address);
            if (pushConfig == null) {
                insert(address);
                return pushConfigMapper.getByAddress(address);
            }
            mapCache.put(address, pushConfig, CacheKey.Common.PUSH_CONFIG_MAP.getExpireTime(), CacheKey.Common.PUSH_CONFIG_MAP.getTimeUnit());
        }
        return pushConfig;
    }
}
