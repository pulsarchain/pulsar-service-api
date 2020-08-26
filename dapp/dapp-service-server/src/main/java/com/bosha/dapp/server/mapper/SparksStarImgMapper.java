package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.SparksStarImg;
import org.apache.ibatis.annotations.Param;

public interface SparksStarImgMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SparksStarImg record);

    SparksStarImg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SparksStarImg record);

    int updateBatchSelective(List<SparksStarImg> list);

    int batchInsert(@Param("list") List<SparksStarImg> list);

    int deleteBySparksId(@Param("id") Long id);

    List<String> imgs(@Param("id") Long id);
}