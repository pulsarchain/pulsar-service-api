package com.bosha.user.server.mapper;

import com.bosha.user.api.entity.AuxiliaryAuthentication;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuxiliaryAuthenticationMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AuxiliaryAuthentication record);

    AuxiliaryAuthentication selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuxiliaryAuthentication record);

    int updateByPrimaryKey(AuxiliaryAuthentication record);

    int updateBatch(List<AuxiliaryAuthentication> list);

    int batchInsert(@Param("list") List<AuxiliaryAuthentication> list);

    int insertOrUpdate(AuxiliaryAuthentication record);

    int insertOrUpdateSelective(AuxiliaryAuthentication record);

    List<AuxiliaryAuthentication> list(@Param("auId") Long auId);

    AuxiliaryAuthentication getByAddressAndAuxiliary(@Param("address") String address,
                                                     @Param("auxiliary") String auxiliary);

    int countIsAuxiliary(@Param("address") String address,
                         @Param("auxiliary") String auxiliary);

    int countSuccessAuxiliary(@Param("address") String address);
}