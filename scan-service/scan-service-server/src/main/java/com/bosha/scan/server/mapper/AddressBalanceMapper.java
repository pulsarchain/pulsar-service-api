package com.bosha.scan.server.mapper;

import java.util.List;


import com.bosha.scan.api.dto.AddressBalanceUserInfo;
import com.bosha.scan.api.dto.TotalInfo;
import com.bosha.scan.api.entity.AddressBalance;
import org.apache.ibatis.annotations.Param;

public interface AddressBalanceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddressBalance record);

    int insertOrUpdate(AddressBalance record);

    int insertOrUpdateSelective(AddressBalance record);

    int insertSelective(AddressBalance record);

    AddressBalance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AddressBalance record);

    int updateByPrimaryKey(AddressBalance record);

    int updateBatch(List<AddressBalance> list);

    int updateBatchSelective(List<AddressBalance> list);

    int batchInsert(@Param("list") List<AddressBalance> list);

    int count(@Param("total") int total, @Param("index") int index);

    List<AddressBalance> list(@Param("total") int total, @Param("index") int index, @Param("page") int page, @Param("size") int size);

    List<AddressBalance> listAddressBalance(@Param("size") Integer size);

    TotalInfo totalInfo();

    int hideBatch(@Param("list") List<String> list);

    List<AddressBalanceUserInfo> monitor(@Param("address") List<String> address);

    AddressBalance getByAddress(@Param("address") String address);

    int isExist(@Param("address") String address);
}