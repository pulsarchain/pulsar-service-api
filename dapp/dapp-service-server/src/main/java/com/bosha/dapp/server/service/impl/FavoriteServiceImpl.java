package com.bosha.dapp.server.service.impl;

import java.util.Date;


import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.dapp.api.entity.SparksFavorite;
import com.bosha.dapp.api.service.FavoriteService;
import com.bosha.dapp.server.mapper.SparksFavoriteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: FavoriteServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 9:20
 */
@Service
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private SparksFavoriteMapper favoriteMapper;

    @Override
    @RedissonDistributedLock(key = "@address")
    public Long add(SparksFavorite favorite) {
        SparksFavorite count = favoriteMapper.count(favorite.getAddress(), favorite.getRelatedId(), favorite.getType());
        if (count != null)
            return count.getId();
        favorite.setCreateTime(new Date());
        favoriteMapper.insertSelective(favorite);
        return favorite.getId();
    }

    @Override
    public boolean cancel(SparksFavorite favorite) {
        return favoriteMapper.delete(favorite.getAddress(), favorite.getRelatedId(), favorite.getType()) > 0;
    }
}
