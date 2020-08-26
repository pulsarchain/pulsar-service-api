package com.bosha.user.server.mapper;

import cn.hutool.db.DaoTemplate;
import com.bosha.user.api.dto.*;
import com.bosha.user.api.entity.ImRedReceive;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImRedReceiveMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImRedReceive record);

    int insertSelective(ImRedReceive record);

    ImRedReceive selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImRedReceive record);

    int updateByPrimaryKey(ImRedReceive record);

    int batchInsert(@Param("list") List<ImRedReceive> list);

    Integer selectCountByRedId(Long id);

    ImRedReceive selectByRedIdAndAddress(@Param("redId") Long id, @Param("address")String address);

    ImRedResDto selectPeopleAndNumber(Long id);

    List<ImRedSendMessage> selectByReadId(@Param("redId") Long id);

    List<ImRedDetailReceiveDto> selectRedReceiveByReadId(@Param("redId") Long id);

    ImRedMyDetailDto findMyReceiveRed(@Param("userAddress") String userAddress,@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    List<ImMyReceiveRedDto> findMyReceiveRedList(@Param("userAddress")String userAddress,@Param("startTime")  Date startTime, @Param("endTime")  Date endTime);

    void updateByRedId(ImRedReceive imRedReceive);

}