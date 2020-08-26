package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.dto.web.MyRecommendDto;
import com.bosha.star.api.dto.web.StarDetailDto;
import com.bosha.star.api.dto.web.StarListDto;
import com.bosha.star.api.entity.Star;
import org.apache.ibatis.annotations.Param;

public interface StarMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Star record);

    Star selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Star record);

    int batchInsert(@Param("list") List<Star> list);

    List<StarListDto> list(@Param("name") String name,
                           @Param("address") String address,
                           @Param("field") String field,
                           @Param("orderBy") String orderBy);

    StarDetailDto detail(@Param("id") Long id, @Param("address") String address);

    StarDetailDto my(@Param("address") String address);

    Star getByAddress(@Param("address") String address);

    List<MyRecommendDto> myRecommend(@Param("address") String address);

    String getStarName(@Param("id") Long id);
}