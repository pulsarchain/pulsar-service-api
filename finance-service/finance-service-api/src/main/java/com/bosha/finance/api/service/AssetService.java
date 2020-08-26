package com.bosha.finance.api.service;

import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.UserAssetDto;
import com.bosha.finance.api.entity.Asset;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserService
 * @Author liqingping
 * @Date 2018-12-20 10:07
 */

@FeignClient(FinanceServiceConstants.SERVER_NAME)//服务名
@RequestMapping(FinanceServiceConstants.SERVER_PRIFEX)//内部服务前缀
@Api(tags = "用户资产server层接口")
public interface AssetService {

    @ApiOperation("初始化用户资产")
    @PostMapping("/initUserAsset")
    void initUserAsset(Long userId);

    @ApiOperation("通过币种Id查询用户地址和资产信息")
    @GetMapping("/getUserAssetAddress")
    Asset getUserCoinAsset(@RequestParam("coinId") Long coinId,@RequestParam("userId") Long userId);


    @ApiOperation("添加用户资产")
    @PostMapping("/addUserAsset")
    void addUserAsset(@RequestBody UserAssetDto userAssetDto);




}
