package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.OrgListDto;
import com.bosha.dapp.api.entity.SparksOrg;
import org.apache.ibatis.annotations.Param;

public interface SparksOrgMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksOrg record);

    SparksOrg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksOrg record);

    int updateBatchSelective(List<SparksOrg> list);

    int batchInsert(@Param("list") List<SparksOrg> list);

    SparksOrg getByAddress(@Param("address") String address);

    List<OrgListDto> list(@Param("address") String address, @Param("name") String name, @Param("category") String category);

    List<OrgListDto> my(@Param("address") String address);

    List<OrgListDto> myFollow(@Param("address") String address);
}