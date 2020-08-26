package com.bosha.common.server.mapper;

import java.util.Date;
import java.util.List;


import com.bosha.common.api.dto.MessageRequestDto;
import com.bosha.common.api.entity.PushMessage;
import org.apache.ibatis.annotations.Param;

public interface PushMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PushMessage record);

    int insertOrUpdate(PushMessage record);

    int insertOrUpdateSelective(PushMessage record);

    int insertSelective(PushMessage record);

    PushMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PushMessage record);

    int updateByPrimaryKey(PushMessage record);

    int updateBatch(List<PushMessage> list);

    int updateBatchSelective(List<PushMessage> list);

    int batchInsert(@Param("record") PushMessage record, @Param("ids") List<String> ids);

    List<PushMessage> list(@Param("address") String address, @Param("req") MessageRequestDto requestDto);

    int updateStatus(@Param("address") String address, @Param("id") Long id, @Param("time") Date time);

    int unreadCount(@Param("address") String address);

    int deleteBatch();

    List<PushMessage> listByType(@Param("address") String address, @Param("type") String type, @Param("subType") String subType);
}