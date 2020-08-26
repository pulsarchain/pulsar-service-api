package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksWishImg;
import org.apache.ibatis.annotations.Param;

public interface SparksWishImgMapper {
    int insertSelective(SparksWishImg record);

    int batchInsert(@Param("list") List<SparksWishImg> list);

    List<String> list(@Param("id") Long id);
}