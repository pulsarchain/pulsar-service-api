package com.bosha.common.server.mapper;

import java.util.List;


import com.bosha.common.api.dto.ChainCategoryDto;
import com.bosha.common.api.dto.HelpCenterCategoryDto;
import com.bosha.common.api.dto.InstructionsInfoDto;
import com.bosha.common.api.dto.SystemContentManagerListDto;
import com.bosha.common.api.dto.SystemContentManagerQueryDto;
import com.bosha.common.api.entity.SystemContent;
import org.apache.ibatis.annotations.Param;

public interface SystemContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemContent record);

    int insertSelective(SystemContent record);

    SystemContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemContent record);

    int updateByPrimaryKey(SystemContent record);

    List<SystemContentManagerListDto> list(SystemContentManagerQueryDto query);

    List<HelpCenterCategoryDto> helpCenter(@Param("dictId") Long dictId, @Param("language") String language);

    List<InstructionsInfoDto> instructions(@Param("ids") List<Long> ids, @Param("language") String language);

    List<ChainCategoryDto> chain(@Param("dictId") Long dictId, @Param("name") String name, @Param("language") String language);

    int countByDictId(@Param("dictId") Long dictId);

    Long getMiningRuleId(@Param("dictId") Long dictId);
}