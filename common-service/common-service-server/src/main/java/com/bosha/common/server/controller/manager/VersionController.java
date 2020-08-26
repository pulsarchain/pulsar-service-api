package com.bosha.common.server.controller.manager;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.entity.Version;
import com.bosha.common.api.service.VersionService;
import com.bosha.commons.constants.CommonConstants;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: VersionController
 * @Author liqingping
 * @Date 2019-12-13 11:16
 */
@RestController
@Slf4j
@RequestMapping(CommonServiceConstants.WEB_PRIFEX)
@Api(tags = "版本管理")
public class VersionController extends BaseController {

    @Autowired
    private VersionService versionService;

    @ApiOperation("检查更新")
    @GetMapping("/version/check")
    Version check() {
        return versionService.check();
    }

    @ApiOperation("新增")
    @PostMapping(CommonConstants.Path.MANAGER + "/version/add")
    Long add(@RequestBody @Validated Version version) {
        return versionService.add(version);
    }

    @ApiOperation("根据id获取")
    @GetMapping(CommonConstants.Path.MANAGER + "/version/{id}")
    Version getById(@ApiParam(value = "id",required = true) @PathVariable("id") Long id) {
        return versionService.getById(id);
    }

    @ApiOperation("根据id更新")
    @PostMapping(CommonConstants.Path.MANAGER + "/version/update")
    boolean update(@RequestBody Version version) {
        return versionService.update(version);
    }

    @ApiOperation("列表")
    @GetMapping(CommonConstants.Path.MANAGER + "/version/list")
    PageInfo<Version> list(@ApiParam @ModelAttribute Page page) {
        return versionService.list(page);
    }
}
