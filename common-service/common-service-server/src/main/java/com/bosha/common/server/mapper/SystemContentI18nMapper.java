package com.bosha.common.server.mapper;

import java.util.List;


import com.bosha.common.api.entity.SystemContentI18n;
import org.apache.ibatis.annotations.Param;

public interface SystemContentI18nMapper {
    int insertSelective(SystemContentI18n record);

    int batchInsert(@Param("list") List<SystemContentI18n> list);

    List<SystemContentI18n> list(@Param("id") Long id);

    SystemContentI18n getByScIdAndLanguage(@Param("id") Long id, @Param("language") String language);

    int update(SystemContentI18n record);
}