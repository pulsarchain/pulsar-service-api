package com.bosha.star.server.mapper;

import java.util.Date;
import java.util.List;


import com.bosha.star.api.dto.web.LiveMiningDto;
import com.bosha.star.api.dto.web.LiveMiningMineDto;
import com.bosha.star.api.dto.web.LiveMiningMinerDto;
import com.bosha.star.api.dto.web.LiveRoomInfoDto;
import com.bosha.star.api.entity.LiveMining;
import org.apache.ibatis.annotations.Param;

public interface LiveMiningMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LiveMining record);

    LiveMining selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveMining record);

    int batchInsert(@Param("list") List<LiveMining> list);

    int insertOrUpdate(LiveMining record);

    int insertOrUpdateSelective(LiveMining record);

    int countByAddress(@Param("address") String address, @Param("status") List<Integer> status);

    LiveMining getLastestByAddress(@Param("address") String address);

    List<LiveMiningDto> list(@Param("list") List<Integer> list);

    List<LiveMiningDto> search(@Param("keyword") String keyword);

    List<LiveMiningDto> histories();

    LiveMiningMineDto mine(@Param("address") String address, @Param("id") Long id);

    List<LiveMiningMineDto> mineList(@Param("address") String address);

    LiveMiningMinerDto miner(@Param("address") String address, @Param("id") Long id);

    List<LiveMiningMinerDto> minerList(@Param("address") String address);

    List<LiveMining> pushStartNoticeList(@Param("time") Date time);

    List<LiveMining> pushEndNoticeList(@Param("time") Date time);

    List<LiveMining> closeLive(@Param("now") Date now);

    LiveRoomInfoDto liveRoomInfo(@Param("id") Long id);

    List<String> headImgs(@Param("addresses") List<String> addresses);

    List<LiveMining> calcMinerNum(@Param("time") Date time);

    List<LiveMining> calcOnlineNum();

    List<LiveMining> createChatroom(@Param("time") Date time);

    List<LiveMining> calcPerMinute(@Param("endTime") Date endTime);

    List<Long> unEditVideoIds();

    List<String> addressList();

    int count();
}