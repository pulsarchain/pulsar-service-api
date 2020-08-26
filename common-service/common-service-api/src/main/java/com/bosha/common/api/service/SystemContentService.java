package com.bosha.common.api.service;

import java.util.List;


import com.bosha.common.api.dto.ChainCategoryDto;
import com.bosha.common.api.dto.HelpCenterDto;
import com.bosha.common.api.dto.InstructionsInfoDto;
import com.bosha.common.api.dto.SystemContentManagerListDto;
import com.bosha.common.api.dto.SystemContentManagerQueryDto;
import com.bosha.common.api.entity.SystemContent;
import com.bosha.commons.dto.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

public interface SystemContentService {

    @ApiOperation("发布")
        //@PostMapping("/add")
    Long add(@RequestBody SystemContent systemContent);

    @ApiOperation("更新")
        //@PostMapping("/update")
    boolean update(@RequestBody SystemContent systemContent);

    @ApiOperation("列表")
    PageInfo<SystemContentManagerListDto> list(SystemContentManagerQueryDto query);

    List<HelpCenterDto> helpCenter();

    List<InstructionsInfoDto> instructions(String type);

    SystemContent getById(Long id);

    PageInfo<ChainCategoryDto> chainList(Page page, String name);

    int countByDictId(Long dictId);

    Long getMiningRuleId(Long dictId);
}
