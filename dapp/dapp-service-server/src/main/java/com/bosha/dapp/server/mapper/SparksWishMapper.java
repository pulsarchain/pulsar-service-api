package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.WishDetailDto;
import com.bosha.dapp.api.dto.WishListDto;
import com.bosha.dapp.api.entity.SparksWish;
import org.apache.ibatis.annotations.Param;

public interface SparksWishMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksWish record);

    SparksWish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksWish record);

    int updateBatchSelective(List<SparksWish> list);

    int batchInsert(@Param("list") List<SparksWish> list);

    List<WishListDto> list();

    List<WishListDto> my(@Param("address") String address);

    WishDetailDto detail(@Param("id") Long id);
}