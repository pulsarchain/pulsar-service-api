package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.SparksStarListDto;
import com.bosha.dapp.api.dto.SparksStarListQuery;
import com.bosha.dapp.api.entity.SparksStar;
import org.apache.ibatis.annotations.Param;

public interface SparksStarMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksStar record);

    SparksStar selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksStar record);

    int updateBatchSelective(List<SparksStar> list);

    int batchInsert(@Param("list") List<SparksStar> list);

    List<SparksStarListDto> list(@Param("query") SparksStarListQuery query);

    List<SparksStarListDto> myList(@Param("address") String address);

    SparksStarListDto getByAddressAndType(@Param("address") String address, @Param("type") Integer type, @Param("status") List<Integer> status);

    SparksStarListDto getByType(@Param("type") Integer type);

    int countSuccess();

    List<Integer> joinTypes(@Param("address") String address);
}