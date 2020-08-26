package com.bosha.dapp.server.mapper;

import com.bosha.dapp.api.dto.FundationListDto;import com.bosha.dapp.api.entity.SparksFundation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SparksFundationMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksFundation record);

    SparksFundation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksFundation record);

    int updateBatchSelective(List<SparksFundation> list);

    int batchInsert(@Param("list") List<SparksFundation> list);

    List<FundationListDto> list();

    List<FundationListDto> my(@Param("address") String address);
}