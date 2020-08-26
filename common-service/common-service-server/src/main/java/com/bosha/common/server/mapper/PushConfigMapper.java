package com.bosha.common.server.mapper;

import java.util.List;import com.bosha.common.api.entity.PushConfig;import org.apache.ibatis.annotations.Param;

public interface PushConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PushConfig record);

    int insertSelective(PushConfig record);

    PushConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PushConfig record);

    int updateByAddressSelective(PushConfig record);

    int updateByPrimaryKey(PushConfig record);

    PushConfig getByAddress(@Param("address") String address);

    int countUser();

    List<PushConfig> listUserAddress(@Param("page") int page, @Param("size") int size);

    List<PushConfig> syncData(@Param("page") int page, @Param("size") int size);
}