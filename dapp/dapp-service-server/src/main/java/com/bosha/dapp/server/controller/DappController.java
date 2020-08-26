package com.bosha.dapp.server.controller;

import java.io.IOException;
import java.util.List;


import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.AddressValidator;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.DappCategoriesDto;
import com.bosha.dapp.api.dto.DappDetailDto;
import com.bosha.dapp.api.dto.DappListDto;
import com.bosha.dapp.api.dto.DappListQuery;
import com.bosha.dapp.api.dto.DappSlideshowDto;
import com.bosha.dapp.api.dto.DappSystemConfigInfo;
import com.bosha.dapp.api.dto.DappWitnessDto;
import com.bosha.dapp.api.entity.Dapp;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.service.DappService;
import com.bosha.dapp.server.config.DappServiceConfig;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.web3j.protocol.core.DefaultBlockParameterName;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 15:40
 */
@Api(tags = "dapp")
@RestController
@Slf4j
@RequestMapping(DappServiceConstants.WEB_PRIFEX)
public class DappController {

    @Autowired
    private DappService dappService;
    @Autowired
    private DappServiceConfig dappServiceConfig;

    @ApiOperation("系统配置信息")
    @GetMapping("/info")
    public DappSystemConfigInfo info() {
        return DappSystemConfigInfo.builder().systemAddress(dappServiceConfig.getSystemAddress()).build();
    }

    @ApiOperation("获取dapp分类列表")
    @GetMapping("/categories")
    List<DappCategoriesDto> categories() {
        return dappService.categories();
    }

    @ApiOperation("发布dapp")
    @PostMapping("/add")
    Long add(@RequestBody @Validated Dapp dapp) {
        if (!AddressValidator.isValid(dapp.getContractAddress())) {
            throw new BaseException(DappErrorMsgEnum.SMART_CONTRACT_ADDRESS_ERROR);
        }
        checkContract(dapp.getContractAddress());
        return dappService.add(dapp);
    }

    @ApiOperation("修改dapp")
    @PostMapping("/update")
    boolean update(@RequestBody Dapp dapp) {
        if (dapp.getId() == null)
            throw new BaseException("id不可为空");
        if (StringUtils.isNotBlank(dapp.getContractAddress())) {
            if (!AddressValidator.isValid(dapp.getContractAddress())) {
                throw new BaseException(DappErrorMsgEnum.SMART_CONTRACT_ADDRESS_ERROR);
            }
            checkContract(dapp.getContractAddress());
        }
        return dappService.update(dapp);
    }

    @ApiOperation("我发布的记录")
    @GetMapping("/released")
    PageInfo<Dapp> released(@ModelAttribute Page page) {
        return dappService.released(page);
    }

    @ApiOperation("dapp列表")
    @GetMapping("/list")
    PageInfo<DappListDto> list(@ModelAttribute DappListQuery query) {
        return dappService.list(query);
    }

    @ApiOperation("dapp使用记录")
    @PostMapping("/useRecord")
    void useRecord(@RequestParam("dappId") Long dappId) {
        dappService.useRecord(dappId);
    }

    @ApiOperation("添加见证地址")
    @PostMapping("/witness")
    boolean witness(@RequestBody @Validated DappWitnessDto dappWitnessDto) {
        for (String s : dappWitnessDto.getAddress()) {
            if (!AddressValidator.isValid(s))
                throw new BaseException(UserErrorMessageEnum.ADDRESS_ERROR);
        }
        return dappService.witness(dappWitnessDto);
    }

    @ApiOperation("dapp详情")
    @GetMapping("/detail")
    DappDetailDto detail(@RequestParam("dappId") Long dappId) {
        return dappService.detail(dappId);
    }

    @ApiOperation("dapp首页轮播图")
    @GetMapping("/slideshow")
    List<DappSlideshowDto> slideshow() {
        return dappService.slideshow();
    }

    public void checkContract(String address) {
        try {
            String code = DappServiceConfig.web3j.ethGetCode(address, DefaultBlockParameterName.LATEST).send().getCode();
            if ("0x".equals(code)) {
                throw new BaseException(DappErrorMsgEnum.SMART_CONTRACT_ADDRESS_ERROR);
            }
        } catch (IOException e) {
        }
    }

    @ApiOperation("收藏/关注 dapp")
    @PostMapping("/dappFavorite/add")
    void addFavorite(@RequestParam("dappId") Long dappId) {
        dappService.addFavorite(dappId);
    }

    @ApiOperation("dapp取消收藏/关注 ")
    @PostMapping("/dappFavorite/cancel")
    void cancelFavorite(@RequestParam("dappId") Long dappId) {
        dappService.cancelFavorite(dappId);
    }

    @ApiOperation("我的dapp收藏/关注 ")
    @GetMapping("/dappFavorite/my")
    PageInfo<DappListDto> myFavorite(@RequestBody Page page) {
        return dappService.myFavorite(page);
    }
}
