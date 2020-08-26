package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksFundationExtra;
import org.apache.ibatis.annotations.Param;

public interface SparksFundationExtraMapper {
    int insertSelective(SparksFundationExtra record);

    int batchInsert(@Param("list") List<SparksFundationExtra> list);

    List<SparksFundationExtra> certificates(Long id);

    List<SparksFundationExtra> contracts(Long id);
}