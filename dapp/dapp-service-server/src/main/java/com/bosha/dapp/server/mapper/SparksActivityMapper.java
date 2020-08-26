package com.bosha.dapp.server.mapper;

import java.util.Date;
import java.util.List;


import com.bosha.dapp.api.dto.ActivityCalendarDto;
import com.bosha.dapp.api.dto.ActivityListDto;
import com.bosha.dapp.api.entity.SparksActivity;
import org.apache.ibatis.annotations.Param;

public interface SparksActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksActivity record);

    SparksActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksActivity record);

    int updateBatchSelective(List<SparksActivity> list);

    int batchInsert(@Param("list") List<SparksActivity> list);

    ActivityListDto getOne(@Param("address") String address, @Param("currentUserAddress") String currentUserAddress);

    List<ActivityListDto> list(@Param("address") String address, @Param("name") String name, @Param("hot") Integer hot);

    List<ActivityListDto> my(@Param("address") String address);

    List<ActivityListDto> myFavoriteAll(@Param("type") Integer type, @Param("now") Date now);

    List<ActivityListDto> myFavorite(@Param("type") Integer type, @Param("now") Date now);

    List<ActivityListDto> calendarListExpire(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<ActivityListDto> calendarList(@Param("address") String address, @Param("type") Integer type,
                                       @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<ActivityCalendarDto> calendar(@Param("address") String address, @Param("startTime") String startTime, @Param("endTime") String endTime);

}