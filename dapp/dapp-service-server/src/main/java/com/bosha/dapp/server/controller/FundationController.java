package com.bosha.dapp.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.FundationDetailDto;
import com.bosha.dapp.api.dto.FundationListDto;
import com.bosha.dapp.api.entity.SparksFundation;
import com.bosha.dapp.api.service.FundationService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: FundationController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 11:56
 */
@RestController
@Slf4j
@Api(tags = "基金")
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/fundation")
public class FundationController extends BaseController {

    @Autowired
    private FundationService fundationService;

    @ApiOperation("新增")
    @PostMapping("/add")
    Long add(@RequestBody @Validated SparksFundation fundation) {
        fundation.setAddress(getAddress());
        return fundationService.add(fundation);
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    PageInfo<FundationListDto> list(Page page) {
        return fundationService.list(page);
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    FundationDetailDto detail(@ApiParam(value = "基金的id") @RequestParam("id") Long id) {
        return fundationService.detail(id);
    }

    @ApiOperation("我创建的基金")
    @GetMapping("/my")
    PageInfo<FundationListDto> my(Page page) {
        return fundationService.my(page, getAddress());
    }

    @ApiOperation("推送打币更新hash")
    @PostMapping("/updateHash")
    boolean updateHash(@RequestBody SparksFundation fundation) {
        if (StringUtils.isBlank(fundation.getHash()))
            throw new BaseException("hash不可为空");
        return fundationService.updateHash(SparksFundation.builder().id(fundation.getId()).hash(fundation.getHash()).build());
    }
}
