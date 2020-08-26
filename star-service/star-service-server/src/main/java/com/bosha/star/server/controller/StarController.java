package com.bosha.star.server.controller;

import com.bosha.common.api.service.PushService;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.contract.api.enums.ContractType;
import com.bosha.contract.api.service.ContractService;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.web.CreateStarDto;
import com.bosha.star.api.dto.web.ExtraInfoDto;
import com.bosha.star.api.dto.web.JoinStarDto;
import com.bosha.star.api.dto.web.JoinStarResultDto;
import com.bosha.star.api.dto.web.MyRecommendDto;
import com.bosha.star.api.dto.web.QueryStarListDto;
import com.bosha.star.api.dto.web.StarDetailDto;
import com.bosha.star.api.dto.web.StarListDto;
import com.bosha.star.api.service.StarService;
import com.bosha.star.server.config.StarServiceConfig;
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
 * @DESCRIPTION: StarController
 * @Author liqingping
 * @Date 2020-03-05 16:37
 */
@Api(tags = "星系")
@RestController
@RequestMapping(StarServiceConstants.WEB_PRIFEX)
@Slf4j
public class StarController extends BaseController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private StarService starService;
    @Autowired
    private PushService pushService;
    @Autowired
    private StarServiceConfig starServiceConfig;

    @ApiOperation("获取星系的系统打币地址和是否已创建或加入过星系")
    @GetMapping("/info")
    ExtraInfoDto info() {
        String systemAddress = contractService.getSystemAddress(ContractType.STAR);
        Integer exist = starService.exist(getAddress());
        return ExtraInfoDto.builder().join(exist).systemAddress(systemAddress).num(starServiceConfig.getNum()).build();
    }

    @ApiOperation("创建星系")
    @PostMapping("/create")
    Long create(@RequestBody @Validated CreateStarDto create) {
        return starService.create(create);
    }

    @ApiOperation("加入星系")
    @PostMapping("/join")
    JoinStarResultDto join(@RequestBody @Validated JoinStarDto join) {
        return starService.join(join);
    }

    @ApiOperation("星系列表")
    @GetMapping("/list")
    PageInfo<StarListDto> list(@ModelAttribute @ApiParam QueryStarListDto query) {
        return starService.list(query);
    }

    @ApiOperation("星系详情")
    @GetMapping("/detail/{id}")
    StarDetailDto detail(@ApiParam(value = "星系id") @PathVariable("id") Long id) {
        return starService.detail(id, getAddress());
    }

    @ApiOperation("我的星系")
    @GetMapping("/my")
    StarDetailDto detail() {
        return starService.my(getAddress());
    }

    @ApiOperation("我的推荐")
    @GetMapping("/myRecommend")
    PageInfo<MyRecommendDto> myRecommend(@ModelAttribute Page page) {
        return starService.myRecommend(page);
    }

}
