package com.bosha.finance.server.controller.web;

import com.bosha.common.api.dto.Sms;
import com.bosha.common.api.service.SmsService;
import com.bosha.commons.controller.BaseController;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.response.CoinAssetListDto;
import com.bosha.finance.api.dto.response.CoinDetailListDto;
import com.bosha.finance.api.service.CoinService;
import com.bosha.finance.server.config.FinanceConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户资产管理")
@RestController
@Slf4j
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/coin")
public class CoinController extends BaseController {
    @Autowired
    private CoinService coinService;

    @GetMapping("/findUserCoinAsset")
    @ApiOperation("获取用户所有资产信息（web）")
    public List<CoinAssetListDto> findUserCoinAsset() {
       Long userId = getUserId();
       // Long userId = 1L;
        List<CoinAssetListDto> userCoinAsset = coinService.findUserCoinAsset(userId);
        return userCoinAsset;
    }

    @GetMapping("/findWithdrawCoin")
    @ApiOperation("获取可提币的币种（web）")
    public List<CoinDetailListDto> findWithdrawCoin() {
        List<CoinDetailListDto> withdrawCoin = coinService.findWithdrawCoin();
        return withdrawCoin;
    }

    @GetMapping("/findChargingCoin")
    @ApiOperation("获取可充币的币种（web）")
    public List<CoinDetailListDto> findChargingCoin() {
        List<CoinDetailListDto> userCoinAsset = coinService.findChargingCoin();
        return userCoinAsset;
    }

}
