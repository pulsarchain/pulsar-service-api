package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.entity.LiveMiningVod;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningVodMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMiningVod record);

    LiveMiningVod selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningVod record);

    int batchInsert(@Param("list") List<LiveMiningVod> list);

    int insertOrUpdate(LiveMiningVod record);

    int insertOrUpdateSelective(LiveMiningVod record);

    List<LiveMiningVod> listFileId(@Param("liveMiningId") Long liveMiningId);
}