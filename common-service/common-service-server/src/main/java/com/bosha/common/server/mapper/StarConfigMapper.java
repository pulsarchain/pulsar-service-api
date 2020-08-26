package com.bosha.common.server.mapper;

import com.bosha.common.api.entity.StarConfig;
import org.apache.ibatis.annotations.Param;

public interface StarConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StarConfig record);

    int insertSelective(StarConfig record);

    StarConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StarConfig record);

    int updateByPrimaryKey(StarConfig record);

    StarConfig getByCoinId(@Param("coinId") Long coinId);
}