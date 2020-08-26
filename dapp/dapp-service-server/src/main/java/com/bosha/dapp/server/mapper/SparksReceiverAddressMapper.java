package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksReceiverAddress;
import org.apache.ibatis.annotations.Param;

public interface SparksReceiverAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksReceiverAddress record);

    SparksReceiverAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksReceiverAddress record);

    int updateBatchSelective(List<SparksReceiverAddress> list);

    int batchInsert(@Param("list") List<SparksReceiverAddress> list);

    List<SparksReceiverAddress> list(@Param("address") String address);
}