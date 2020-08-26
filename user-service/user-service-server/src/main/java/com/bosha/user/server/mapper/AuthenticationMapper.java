package com.bosha.user.server.mapper;

import java.util.List;


import com.bosha.user.api.entity.Authentication;
import org.apache.ibatis.annotations.Param;

public interface AuthenticationMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Authentication record);

    Authentication selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Authentication record);

    int updateByPrimaryKey(Authentication record);

    int updateBatch(List<Authentication> list);

    int batchInsert(@Param("list") List<Authentication> list);

    int insertOrUpdate(Authentication record);

    int insertOrUpdateSelective(Authentication record);

    Authentication getByAddress(@Param("address") String address);

    List<Authentication> list(@Param("address") String address,
                              @Param("type") Integer type,
                              @Param("status") Integer status);
}