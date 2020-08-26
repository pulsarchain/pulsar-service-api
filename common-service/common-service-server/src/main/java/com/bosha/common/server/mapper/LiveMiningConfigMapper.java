package com.bosha.common.server.mapper;

import com.bosha.common.api.entity.LiveMiningConfig;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LiveMiningConfig record);

    int insertSelective(LiveMiningConfig record);

    LiveMiningConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningConfig record);

    int updateByPrimaryKey(LiveMiningConfig record);

    LiveMiningConfig getByCoinId(@Param("coinId") Long coinId);
}