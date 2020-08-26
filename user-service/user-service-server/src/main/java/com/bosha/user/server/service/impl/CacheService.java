package com.bosha.user.server.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import com.bosha.user.api.entity.User;
import com.bosha.user.server.mapper.UserMapper;
import com.bosha.user.server.redis.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CacheService
 * @Author liqingping
 * @Date 2020-03-26 17:32
 */
@Service
@Slf4j
public class CacheService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedissonClient redis;

    public int updateUserByPrimaryKeySelective(User user) {
        int i = userMapper.updateByPrimaryKeySelective(user);
        User select = userMapper.selectByPrimaryKey(user.getId());
        RBucket<User> bucket = redis.getBucket(CacheKey.User.USER.getKey() + select.getAddress());
        bucket.set(select);
        return i;
    }
}
