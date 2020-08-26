package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.DappListDto;
import com.bosha.dapp.api.entity.DappFavorite;
import org.apache.ibatis.annotations.Param;

public interface DappFavoriteMapper {
    int insertSelective(DappFavorite record);

    int batchInsert(@Param("list") List<DappFavorite> list);

    DappFavorite getByAddressAndDappId(@Param("address") String address, @Param("dappId") Long dappId);

    int deleteByAddressAndDappId(@Param("address") String address, @Param("dappId") Long dappId);

    List<DappListDto> list(@Param("address") String address);
}