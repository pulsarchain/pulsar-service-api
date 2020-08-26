package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.entity.LiveMiningGift;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningGiftMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMiningGift record);

    LiveMiningGift selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningGift record);

    int batchInsert(@Param("list") List<LiveMiningGift> list);

    int insertOrUpdate(LiveMiningGift record);

    int insertOrUpdateSelective(LiveMiningGift record);

    List<LiveMiningGift> list();
}