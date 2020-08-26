package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.DappWitness;
import org.apache.ibatis.annotations.Param;

public interface DappWitnessMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DappWitness record);

    DappWitness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DappWitness record);

    int countDuplicate(@Param("address") String address, @Param("dappId") Long dappId, @Param("auxiliaryAddress") String auxiliaryAddress);

    List<DappWitness> list(@Param("dappId") Long dappId, @Param("address") String address);

    DappWitness getByAddressAndWitness(@Param("to") String to, @Param("from") String from);

    int countWitnessSuccess(@Param("dappId") Long dappId);
}