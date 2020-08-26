package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksActivityImg;
import org.apache.ibatis.annotations.Param;

public interface SparksActivityImgMapper {
    int insertSelective(SparksActivityImg record);

    int batchInsert(@Param("list") List<SparksActivityImg> list);

    List<String> list(@Param("id") Long id);
}