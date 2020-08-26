package com.bosha.dapp.server.mapper;

import com.bosha.dapp.api.entity.DappUseRecord;
import org.apache.ibatis.annotations.Param;

public interface DappUseRecordMapper {
    int deleteByPrimaryKey(@Param("address") String address, @Param("dappId") Long dappId);

    int insertSelective(DappUseRecord record);

    DappUseRecord selectByPrimaryKey(@Param("address") String address, @Param("dappId") Long dappId);

    int updateByPrimaryKeySelective(DappUseRecord record);
}