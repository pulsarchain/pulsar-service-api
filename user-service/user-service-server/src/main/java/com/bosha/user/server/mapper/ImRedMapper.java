package com.bosha.user.server.mapper;

import com.bosha.user.api.dto.ImMySendRedDto;import com.bosha.user.api.dto.ImRedDetailDto;import com.bosha.user.api.dto.ImRedList;import com.bosha.user.api.dto.ImRedMyDetailDto;import com.bosha.user.api.entity.ImRed;
import java.util.Date;import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImRedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImRed record);

    int insertSelective(ImRed record);

    ImRed selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImRed record);

    int updateByPrimaryKey(ImRed record);

    int batchInsert(@Param("list") List<ImRed> list);

    ImRedDetailDto selectById(@Param("id") Long id, @Param("userAddress") String userAddress);

    Integer selectByStatus(String userAddress);

    ImRedMyDetailDto findMySendRed(@Param("userAddress") String userAddress, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<ImMySendRedDto> findMySendRedList(@Param("userAddress") String userAddress, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<ImRedList> findMyPaidRed(String userAddress);

    List<ImRed> selectByNotPayStatus();
}