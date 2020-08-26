package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.entity.LiveMiningGiftRecord;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningGiftRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMiningGiftRecord record);

    LiveMiningGiftRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningGiftRecord record);

    int batchInsert(@Param("list") List<LiveMiningGiftRecord> list);

    int insertOrUpdate(LiveMiningGiftRecord record);

    int insertOrUpdateSelective(LiveMiningGiftRecord record);
}