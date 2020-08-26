package com.bosha.star.server.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import com.bosha.star.api.entity.LiveMiningRecord;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMiningRecord record);

    LiveMiningRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMiningRecord record);

    int batchInsert(@Param("list") List<LiveMiningRecord> list);

    int insertOrUpdate(LiveMiningRecord record);

    int insertOrUpdateSelective(LiveMiningRecord record);

    int updateAmountBatch(@Param("ids") List<Long> ids, @Param("date") Date date);

    int countByAddressAndTimeAndLiveMiningId(@Param("address") String address,
                                             @Param("id") Long id,
                                             @Param("type") Integer type,
                                             @Param("startTime") Date startTime,
                                             @Param("endTime") Date endTime);

    int countMinerNum(@Param("id") Long id);

    List<LiveMiningRecord> calcPerMinute(@Param("liveMiningId") Long liveMiningId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    BigDecimal giveOut(@Param("liveMiningId") Long liveMiningId, @Param("types") List<Integer> types);

    int giveOutUpdate(@Param("liveMiningId") Long liveMiningId, @Param("types") List<Integer> types);

    List<LiveMiningRecord> lastTimeLogInfo(@Param("liveMiningId") Long liveMiningId);
}