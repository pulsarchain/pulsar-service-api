package com.bosha.dapp.server.controller;

import java.math.BigDecimal;


import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.SparksStarDetailDto;
import com.bosha.dapp.api.dto.SparksStarIndexDto;
import com.bosha.dapp.api.dto.SparksStarListDto;
import com.bosha.dapp.api.dto.SparksStarListQuery;
import com.bosha.dapp.api.entity.SparksStar;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.service.SparksService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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
 * @DESCRIPTION: SparksStarController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-25 19:20
 */
@RestController
@Slf4j
@Api(tags = "星星之火（点亮、擦亮、造星）")
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/sparks")
public class SparksStarController extends BaseController {

    @Autowired
    private SparksService sparksService;

    @ApiOperation("首页")
    @GetMapping("/index")
    public SparksStarIndexDto index(){
        return sparksService.index();
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    Long add(@RequestBody @Validated SparksStar star) {
        if (star.getTotal().compareTo(BigDecimal.ZERO) <= 0)
            throw new BaseException(DappErrorMsgEnum.AMOUNT_MUST_BE_POSITIVE);
        if (star.getType() == 1 || star.getType() == 2) {
            if (star.getPerMonth() == null || star.getYear() == null)
                throw new BaseException(DappErrorMsgEnum.MONTH_AND_YEAR_REQUIRED);
            if (star.getPerMonth().compareTo(BigDecimal.valueOf(20000)) > 0)
                throw new BaseException(DappErrorMsgEnum.MONTHLY_MAXIMUM);
        }
        star.setAddress(getAddress());
        return sparksService.add(star);
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    boolean update(@RequestBody SparksStar star) {
        return sparksService.update(star);
    }

    @ApiOperation("列表")
    @GetMapping("/list")
    PageInfo<SparksStarListDto> list(@ApiParam(value = "查询") SparksStarListQuery query) {
        return sparksService.list(query);
    }

    @ApiOperation("我的发布")
    @GetMapping("/my")
    PageInfo<SparksStarListDto> myList(Page page) {
        return sparksService.myList(page, getAddress());
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    SparksStarDetailDto detail(@RequestParam("id") Long id) {
        return sparksService.detail(id);
    }

}
