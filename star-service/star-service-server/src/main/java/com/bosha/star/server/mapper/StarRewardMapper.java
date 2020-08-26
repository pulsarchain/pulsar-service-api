package com.bosha.star.server.mapper;

import java.util.Date;
import java.util.List;


import com.bosha.star.api.entity.StarReward;
import org.apache.ibatis.annotations.Param;

public interface StarRewardMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(StarReward record);

    StarReward selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StarReward record);

    int batchInsert(@Param("list") List<StarReward> list);

    void updateStatusAndHashBatch(@Param("list") List<Long> list, @Param("hash") String hash, @Param("date") Date date);
}