package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.entity.LiveMiningInvite;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningInviteMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMiningInvite record);

    LiveMiningInvite selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningInvite record);

    int batchInsert(@Param("list") List<LiveMiningInvite> list);

    int insertOrUpdate(LiveMiningInvite record);

    int insertOrUpdateSelective(LiveMiningInvite record);

    int isAccept(@Param("address") String address, @Param("liveMiningId") Long liveMiningId);
}