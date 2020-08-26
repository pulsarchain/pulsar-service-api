package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksReceiveAccount;
import org.apache.ibatis.annotations.Param;

public interface SparksReceiveAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksReceiveAccount record);

    SparksReceiveAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksReceiveAccount record);

    int updateBatchSelective(List<SparksReceiveAccount> list);

    int batchInsert(@Param("list") List<SparksReceiveAccount> list);

    SparksReceiveAccount getByAddressAndType(@Param("address") String address, @Param("type") Integer type);

    List<SparksReceiveAccount> list(@Param("address") String address);
}