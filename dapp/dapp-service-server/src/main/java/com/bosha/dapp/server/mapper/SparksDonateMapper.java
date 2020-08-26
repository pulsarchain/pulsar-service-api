package com.bosha.dapp.server.mapper;

import com.bosha.dapp.api.dto.DonateListDto;import com.bosha.dapp.api.dto.DonateQuery;import com.bosha.dapp.api.entity.SparksDonate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SparksDonateMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksDonate record);

    SparksDonate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksDonate record);

    int updateBatchSelective(List<SparksDonate> list);

    int batchInsert(@Param("list") List<SparksDonate> list);

    List<DonateListDto> list(DonateQuery query);

    List<DonateListDto> my(@Param("address") String address);

    List<DonateListDto> myFavorite(@Param("address") String address);
}