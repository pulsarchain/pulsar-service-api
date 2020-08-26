package com.bosha.common.server.mapper;


import java.util.List;


import com.bosha.common.api.entity.Area;
import org.apache.ibatis.annotations.Param;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);

    List<Area> listAllProvince();

    List<Area> listAllCity(@Param("provinceId") Integer provinceId);

    List<Area> listAllArea(@Param("cityId") Integer cityId);

}