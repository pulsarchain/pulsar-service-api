package com.bosha.common.server.controller;

import java.util.List;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.HelpCenterDto;
import com.bosha.common.api.dto.InstructionsInfoDto;
import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.entity.SystemContent;
import com.bosha.common.api.enums.CommonsErrorMessageEnum;
import com.bosha.common.api.enums.DictTypeEnum;
import com.bosha.common.api.service.DictService;
import com.bosha.common.api.service.SystemContentService;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MessageController
 * @Author liqingping
 * @Date 2019-12-12 13:31
 */
@RestController
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + "/helpCenter")
@Slf4j
@Api(tags = "帮助中心")
public class HelpCenterController extends BaseController {

    @Autowired
    private SystemContentService systemContentService;
    @Autowired
    private DictService dictService;

    @ApiOperation("帮助中心")
    @GetMapping
    List<HelpCenterDto> helpCenter() {
        return systemContentService.helpCenter();
    }

    @ApiOperation("帮助中心、使用说明的 详情")
    @GetMapping("/{id}")
    SystemContent detail(@PathVariable Long id) {
        return systemContentService.getById(id);
    }

    @ApiOperation("具体位置的使用说明")
    @GetMapping("/instructions/{type}")
    Long instructions(@PathVariable String type) {
        Dict dict = dictService.getByTypeAndValue(DictTypeEnum.instructions, type);
        if (dict == null)
            throw new BaseException(CommonsErrorMessageEnum.MINING_RULE_NOT_EXITS);
        return systemContentService.getMiningRuleId(dict.getId());
    }

    @ApiOperation("使用说明")
    @GetMapping("/instructions")
    List<InstructionsInfoDto> instructions2(@ApiParam(value = "类型",required = false)
                                            @RequestParam(value = "type",required = false,defaultValue = "") String type) {
        return systemContentService.instructions(type);
    }
}
