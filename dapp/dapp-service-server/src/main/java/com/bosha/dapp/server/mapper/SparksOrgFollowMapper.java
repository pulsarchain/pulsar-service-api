package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksOrgFollow;
import org.apache.ibatis.annotations.Param;

public interface SparksOrgFollowMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksOrgFollow record);

    SparksOrgFollow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksOrgFollow record);

    int updateBatchSelective(List<SparksOrgFollow> list);

    int batchInsert(@Param("list") List<SparksOrgFollow> list);

    SparksOrgFollow getByAddressAndOrgId(@Param("address") String address, @Param("orgId") Long orgId);

    int deleteByAddressAndOrgId(@Param("address") String address, @Param("orgId") Long orgId);
}