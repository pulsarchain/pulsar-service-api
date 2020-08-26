package com.bosha.finance.api.service;

import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.CoinDto;
import com.bosha.finance.api.dto.request.QueryCoinDto;
import com.bosha.finance.api.dto.response.CoinAssetListDto;
import com.bosha.finance.api.dto.response.CoinDetailListDto;
import com.bosha.finance.api.dto.response.CoinListDto;
import com.bosha.finance.api.entity.Coin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserService
 * @Author liqingping
 * @Date 2018-12-20 10:07
 */

@FeignClient(FinanceServiceConstants.SERVER_NAME)//服务名
@RequestMapping(FinanceServiceConstants.SERVER_PRIFEX)//内部服务前缀
@Api(tags = "币种server层接口")
public interface CoinService {

    @ApiOperation("查询所有的币种并分页（后台管理系统）")
    @PostMapping("/findCoinPageInfo")
    PageInfo<CoinListDto> findCoinPageInfo(@RequestBody QueryCoinDto queryCoinDto);

    @ApiOperation("添加修改币种（后台管理系统）")
    @PostMapping("/saveOrUpdateCoin")
    void saveOrUpdateCoin(@RequestBody CoinDto coinDto);

    @ApiOperation("获取用户所有资产信息（web）")
    @GetMapping("/findUserCoinAsset")
    List<CoinAssetListDto> findUserCoinAsset(Long userId);

    @ApiOperation("获取提币地址（web）")
    @GetMapping("/findWithdrawCoin")
    List<CoinDetailListDto> findWithdrawCoin();

    @ApiOperation("获取充币地址（web）")
    @GetMapping("/findChargingCoin")
    List<CoinDetailListDto> findChargingCoin();

    @ApiOperation("获取充币地址（web）")
    @GetMapping("/selectCoinBySymbol")
    Coin selectCoinBySymbol(String symbol);
}
