package com.bosha.common.server.controller;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.ChainCategoryDto;
import com.bosha.common.api.entity.SystemContent;
import com.bosha.common.api.service.SystemContentService;
import com.bosha.commons.annotation.NoLog;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + "/chain")
@Slf4j
@Api(tags = "公链动态")
public class ChainController extends BaseController {

    @Autowired
    private SystemContentService systemContentService;

    @ApiOperation("列表")
    @GetMapping
    @NoLog
    PageInfo<ChainCategoryDto> list(@ModelAttribute @Validated Page page, @RequestParam(value = "name", required = false,defaultValue = "") String name) {
        return systemContentService.chainList(page,name);
    }

    @ApiOperation("详情")
    @GetMapping("/{id}")
    SystemContent detail(@PathVariable Long id) {
        return systemContentService.getById(id);
    }
}
