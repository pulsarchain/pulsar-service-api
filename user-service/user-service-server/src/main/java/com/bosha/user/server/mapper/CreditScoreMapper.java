package com.bosha.user.server.mapper;

import java.util.Date;
import java.util.List;


import com.bosha.user.api.entity.CreditScore;
import org.apache.ibatis.annotations.Param;

public interface CreditScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CreditScore record);

    CreditScore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CreditScore record);

    /*int updateByPrimaryKey(CreditScore record);

    int updateBatch(List<CreditScore> list);

    int batchInsert(@Param("list") List<CreditScore> list);

    int insertOrUpdate(CreditScore record);

    int insertOrUpdateSelective(CreditScore record);*/

    CreditScore getByAddress(@Param("address") String address);

    List<String> getByRange(@Param("min") int min, @Param("max") int max, @Param("time") Date time);

    int countByRange(@Param("min") int min, @Param("max") int max);
}