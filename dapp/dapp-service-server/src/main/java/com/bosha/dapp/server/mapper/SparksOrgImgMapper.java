package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksOrgImg;
import org.apache.ibatis.annotations.Param;

public interface SparksOrgImgMapper {
    int insertSelective(SparksOrgImg record);

    int batchInsert(@Param("list") List<SparksOrgImg> list);

    List<String> list(@Param("id") Long id);
}