package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.DonateListDto;
import com.bosha.dapp.api.entity.SparksDonateBuy;
import org.apache.ibatis.annotations.Param;

public interface SparksDonateBuyMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksDonateBuy record);

    SparksDonateBuy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksDonateBuy record);

    int updateBatchSelective(List<SparksDonateBuy> list);

    int batchInsert(@Param("list") List<SparksDonateBuy> list);

    int count(@Param("address") String address, @Param("id") Long id);

    int updateNotice(@Param("address") String address, @Param("id") Long id);

    List<DonateListDto> myBuy(@Param("address") String address);

    List<SparksDonateBuy> listByAddress(@Param("address") String address);
}