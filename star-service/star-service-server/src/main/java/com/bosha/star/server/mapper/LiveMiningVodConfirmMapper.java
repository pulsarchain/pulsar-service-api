package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.entity.LiveMiningVodConfirm;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningVodConfirmMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMiningVodConfirm record);

    LiveMiningVodConfirm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningVodConfirm record);

    int batchInsert(@Param("list") List<LiveMiningVodConfirm> list);

    int insertOrUpdate(LiveMiningVodConfirm record);

    int insertOrUpdateSelective(LiveMiningVodConfirm record);

    LiveMiningVodConfirm getByTaskId(@Param("taskId") String taskId);
}