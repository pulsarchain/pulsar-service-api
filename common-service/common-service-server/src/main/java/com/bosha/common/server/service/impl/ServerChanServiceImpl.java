package com.bosha.common.server.service.impl;

import com.bosha.common.api.service.ServerChanService;
import com.bosha.commons.third.serverChan.ServerChanPush;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ServerChanServiceImpl
 * @Author liqingping
 * @Date 2019-12-27 16:33
 */
@RestController
@Slf4j
public class ServerChanServiceImpl implements ServerChanService {

    @Autowired
    private RedissonClient redis;

    @Override
    public void push(@RequestBody ServerChanPush push) {

    }
}
