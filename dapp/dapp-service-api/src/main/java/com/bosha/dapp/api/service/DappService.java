package com.bosha.dapp.api.service;

import java.util.List;


import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.DappCategoriesDto;
import com.bosha.dapp.api.dto.DappDetailDto;
import com.bosha.dapp.api.dto.DappListDto;
import com.bosha.dapp.api.dto.DappListQuery;
import com.bosha.dapp.api.dto.DappSlideshowDto;
import com.bosha.dapp.api.dto.DappWitnessDto;
import com.bosha.dapp.api.entity.Dapp;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappService
 * @Author liqingping
 * @Date 2020-05-09 18:51
 */
@FeignClient(DappServiceConstants.SERVER_NAME)//服务名
@RequestMapping(DappServiceConstants.SERVER_PRIFEX)//内部服务前缀
public interface DappService {

    @ApiOperation("获取dapp分类列表")
    @GetMapping("/categories")
    List<DappCategoriesDto> categories();

    @ApiOperation("发布dapp")
    @PostMapping("/add")
    Long add(@RequestBody Dapp dapp);

    @ApiOperation("修改dapp")
    @PostMapping("/update")
    boolean update(@RequestBody Dapp dapp);

    @ApiOperation("修改dapp的状态")
    @PostMapping("/updateStatus")
    boolean updateStatus(@RequestBody Dapp dapp);

    @ApiOperation("我发布的记录")
    @GetMapping("/released")
    PageInfo<Dapp> released(@ModelAttribute Page page);

    @ApiOperation("dapp列表")
    @GetMapping("/list")
    PageInfo<DappListDto> list(@ModelAttribute DappListQuery query);

    @ApiOperation("dapp使用记录")
    @PostMapping("/useRecord")
    void useRecord(@RequestParam("dappId") Long dappId);

    @ApiOperation("添加见证地址")
    @PostMapping("/witness")
    boolean witness(@RequestBody DappWitnessDto dappWitnessDto);

    @ApiOperation("dapp详情")
    @GetMapping("/detail")
    DappDetailDto detail(@RequestParam("dappId") Long dappId);

    @ApiOperation("dapp首页轮播图")
    @GetMapping("/slideshow")
    List<DappSlideshowDto> slideshow();

    @ApiOperation("收藏dapp")
    @PostMapping("/favorite/add")
    void addFavorite(@RequestParam("dappId") Long dappId);

    @ApiOperation("dapp取消收藏")
    @PostMapping("/favorite/cancel")
    void cancelFavorite(@RequestParam("dappId") Long dappId);

    @ApiOperation("我的dapp收藏")
    @GetMapping("/favorite/my")
    PageInfo<DappListDto> myFavorite(@RequestBody Page page);
}
