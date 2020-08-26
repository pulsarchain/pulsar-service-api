package com.bosha.common.server.mapper;

import java.util.List;


import com.bosha.common.api.entity.DictI18n;
import org.apache.ibatis.annotations.Param;

public interface DictI18nMapper {
    int insertSelective(DictI18n record);

    int batchInsert(@Param("list") List<DictI18n> list);

    DictI18n getByDictIdAndLanguage(@Param("dictId") Long dictId, @Param("language") String language);

    int update(DictI18n record);

    List<DictI18n> list(@Param("id") Long id);
}