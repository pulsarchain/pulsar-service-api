package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksDonateImg;
import org.apache.ibatis.annotations.Param;

public interface SparksDonateImgMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksDonateImg record);

    SparksDonateImg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksDonateImg record);

    int updateBatchSelective(List<SparksDonateImg> list);

    int batchInsert(@Param("list") List<SparksDonateImg> list);

    List<String> list(@Param("id") Long id);
}