package com.bosha.dapp.server.controller;

import java.util.List;


import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.DappReportAddDto;
import com.bosha.dapp.api.dto.DappReportDetailDto;
import com.bosha.dapp.api.dto.DappReportPublicListDto;
import com.bosha.dapp.api.dto.MyDappReportListDto;
import com.bosha.dapp.api.entity.DappReportCategory;
import com.bosha.dapp.api.entity.DappReportVote;
import com.bosha.dapp.api.service.DappReportService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappReportController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-13 12:06
 */
@RestController
@Slf4j
@Api(tags = "dapp举报")
@RequestMapping(DappServiceConstants.WEB_PRIFEX+"/report")
public class DappReportController extends BaseController {

    @Autowired
    private DappReportService dappReportService;

    @ApiOperation("举报分类")
    @GetMapping("/categories")
    List<DappReportCategory> categories() {
        return dappReportService.categories();
    }

    @ApiOperation("新增举报内容")
    @PostMapping("/add")
    Long add(@RequestBody @Validated DappReportAddDto dappReportAddDto) {
        return dappReportService.add(dappReportAddDto);
    }

    @ApiOperation("举报内容详情")
    @GetMapping("/detail")
    DappReportDetailDto detail(@ApiParam(value = "举报的id") @RequestParam("id") Long id) {
        return dappReportService.detail(id);
    }

    @ApiOperation("我的举报记录")
    @GetMapping("/list")
    PageInfo<MyDappReportListDto> list(@ModelAttribute Page page) {
        return dappReportService.list(page);
    }

    @ApiOperation("举报公示区列表")
    @GetMapping("/publicList")
    PageInfo<DappReportPublicListDto> publicList(@ModelAttribute Page page) {
        return dappReportService.publicList(page);
    }

    @ApiOperation("公示详情")
    @GetMapping("/publicDetail")
    DappReportPublicListDto publicDetail(@ApiParam(value = "举报的id") @RequestParam("id") Long id) {
        return dappReportService.publicDetail(id);
    }

    @ApiOperation("投票")
    @PostMapping("/vote")
    void vote(@RequestBody @Validated DappReportVote dappReportVote) {
        dappReportVote.setAddress(getAddress());
        dappReportService.vote(dappReportVote);
    }
}
