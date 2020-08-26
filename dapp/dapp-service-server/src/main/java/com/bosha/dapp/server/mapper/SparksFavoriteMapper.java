package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksFavorite;
import org.apache.ibatis.annotations.Param;

public interface SparksFavoriteMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksFavorite record);

    SparksFavorite selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksFavorite record);

    int updateBatchSelective(List<SparksFavorite> list);

    int batchInsert(@Param("list") List<SparksFavorite> list);

    SparksFavorite count(@Param("address") String address, @Param("id") Long id, @Param("type") Integer type);

    int delete(@Param("address") String address, @Param("id") Long id, @Param("type") Integer type);
}