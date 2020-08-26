package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.entity.LiveMiningStream;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningStreamMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMiningStream record);

    LiveMiningStream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningStream record);

    int batchInsert(@Param("list") List<LiveMiningStream> list);

    int insertOrUpdate(LiveMiningStream record);

    int insertOrUpdateSelective(LiveMiningStream record);

    LiveMiningStream getByLiveMiningId(@Param("liveMiningId") Long liveMiningId);
}