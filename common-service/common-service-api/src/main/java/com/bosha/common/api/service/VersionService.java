package com.bosha.common.api.service;

import com.bosha.common.api.entity.Version;
import com.bosha.commons.dto.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;

public interface VersionService {

    @ApiOperation("检查更新")
    Version check();

    @ApiOperation("新增")
    Long add(Version version);

    @ApiOperation("根据id获取")
    Version getById(Long id);

    @ApiOperation("更新")
    boolean update(Version version);

    @ApiOperation("列表")
    PageInfo<Version> list(Page page);
}
