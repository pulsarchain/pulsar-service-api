package com.bosha.dapp.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.DonateBuyDto;
import com.bosha.dapp.api.dto.DonateDetailDto;
import com.bosha.dapp.api.dto.DonateListDto;
import com.bosha.dapp.api.dto.DonateQuery;
import com.bosha.dapp.api.entity.SparksDonate;
import com.bosha.dapp.api.service.DonateService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @DESCRIPTION: DonateController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-27 17:35
 */
@Api(tags = "爱心捐赠")
@RestController
@Slf4j
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/donate")
public class DonateController extends BaseController {

    @Autowired
    private DonateService donateService;

    @ApiOperation("新增爱心捐赠")
    @PostMapping("/add")
    Long add(@RequestBody @Validated SparksDonate donate) {
        return donateService.add(donate);
    }

    @ApiOperation("成功购买/领取（自动发送通知提醒捐赠人）")
    @PostMapping("/buy")
    void success(@RequestBody @Validated DonateBuyDto donateBuyDto) {
        donateService.success(getAddress(), donateBuyDto);
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    PageInfo<DonateListDto> list(@ApiParam(value = "") @ModelAttribute DonateQuery query) {
        return donateService.list(query);
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    DonateDetailDto detail(@ApiParam(value = "捐赠的id") @RequestParam("id") Long id) {
        return donateService.detail(id);
    }


    @ApiOperation("我的捐赠")
    @GetMapping("/my")
    PageInfo<DonateListDto> my(Page page) {
        return donateService.my(page);
    }

    @ApiOperation("我的购买/领取")
    @GetMapping("/myBuy")
    PageInfo<DonateListDto> myBuy(Page page) {
        return donateService.myBuy(page);
    }

    @ApiOperation("我的收藏")
    @GetMapping("/myFavorite")
    PageInfo<DonateListDto> myFavorite(Page page) {
        return donateService.myFavorite(page);
    }

    @ApiOperation("推送打币更新hash")
    @PostMapping("/updateHash")
    boolean updateHash(@RequestBody SparksDonate donate) {
        if (StringUtils.isBlank(donate.getHash()))
            throw new BaseException("hash不可为空");
        return donateService.updateHash(SparksDonate.builder().id(donate.getId()).hash(donate.getHash()).build());
    }
}
