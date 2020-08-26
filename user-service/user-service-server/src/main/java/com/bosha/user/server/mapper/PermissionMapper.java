package com.bosha.user.server.mapper;

import java.util.List;
import java.util.Set;


import com.bosha.user.api.entity.Permission;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    List<Permission> listAll(@Param("tagList") Set<String> tagList);


}