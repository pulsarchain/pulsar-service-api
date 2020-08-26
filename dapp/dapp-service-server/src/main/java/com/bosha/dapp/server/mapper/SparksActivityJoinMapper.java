package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.ActivityDetailDto;
import com.bosha.dapp.api.entity.SparksActivityJoin;
import org.apache.ibatis.annotations.Param;

public interface SparksActivityJoinMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksActivityJoin record);

    SparksActivityJoin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksActivityJoin record);

    int updateBatchSelective(List<SparksActivityJoin> list);

    int batchInsert(@Param("list") List<SparksActivityJoin> list);

    List<String> joinAddress(@Param("id") Long id);

    ActivityDetailDto detail(@Param("address") String address, @Param("id") Long id);

    int deleteByAddressAndId(@Param("address") String address, @Param("id") Long id, @Param("type") Integer type);

}