package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksWitness;
import org.apache.ibatis.annotations.Param;

public interface SparksWitnessMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksWitness record);

    SparksWitness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksWitness record);

    int updateBatchSelective(List<SparksWitness> list);

    int batchInsert(@Param("list") List<SparksWitness> list);

    List<SparksWitness> list(@Param("relatedId") Long relatedId);

    SparksWitness get(@Param("address") String address, @Param("witnessAddress") String witnessAddress, @Param("id") Long id);

    SparksWitness getByAddressAndWitnessAndId(@Param("address") String address, @Param("witnessAddress") String witnessAddress, @Param("id") Long id);

    SparksWitness getByHash(@Param("hash") String hash);

    int countSuccess(@Param("id") Long id);
}