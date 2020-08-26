package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.DappListDto;
import com.bosha.dapp.api.dto.DappListQuery;
import com.bosha.dapp.api.entity.Dapp;
import org.apache.ibatis.annotations.Param;

public interface DappMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Dapp record);

    Dapp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dapp record);

    int updateBatchSelective(List<Dapp> list);

    int batchInsert(@Param("list") List<Dapp> list);

    List<Dapp> released(@Param("address") String address);

    List<DappListDto> list(@Param("query") DappListQuery query);
}