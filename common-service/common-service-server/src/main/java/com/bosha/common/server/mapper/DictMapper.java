package com.bosha.common.server.mapper;

import java.util.List;


import com.bosha.common.api.entity.Dict;
import org.apache.ibatis.annotations.Param;

public interface DictMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dict record);

    List<Dict> list(@Param("type") String type);

    List<Dict> projectCategories(@Param("name") String name);

    List<Dict> getByParentId(@Param("id") Long id, @Param("enable") boolean enable);

    Dict getByTypeAndValue(@Param("name") String name, @Param("value") String value);

    List<Dict> listParent(@Param("name") String name, @Param("type") String type);

    Dict getByType(@Param("type") String type);

    List<Long> getIdsByValue(@Param("value") String value);
}