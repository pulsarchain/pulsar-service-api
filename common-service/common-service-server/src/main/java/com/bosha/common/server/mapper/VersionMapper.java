package com.bosha.common.server.mapper;

import java.util.List;import com.bosha.common.api.entity.Version;import org.apache.ibatis.annotations.Param;

public interface VersionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKey(Version record);

    List<Version> list();

    Version getMax(@Param("os") int os);

    Version getByVersionAndOs(@Param("version") String version, @Param("os") int os);
}