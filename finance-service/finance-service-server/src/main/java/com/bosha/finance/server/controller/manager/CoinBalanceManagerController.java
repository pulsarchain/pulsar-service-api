package com.bosha.finance.server.controller.manager;

import com.bosha.commons.controller.BaseController;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.CoinDto;
import com.bosha.finance.api.dto.request.QueryCoinDto;
import com.bosha.finance.api.dto.response.CoinBalanceListDto;
import com.bosha.finance.api.dto.response.CoinListDto;
import com.bosha.finance.api.service.CoinBalanceService;
import com.bosha.finance.api.service.CoinService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "热钱包平台余额接口")
@RestController
@Slf4j
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/manager/balance")
public class CoinBalanceManagerController extends BaseController {
    @Autowired
    private CoinBalanceService coinBalanceService;

    @GetMapping("/findCoinBalanceList")
    @ApiOperation("查询热钱包平台余额（后台管理系统）")
    public List<CoinBalanceListDto> findCoinBalanceList() {
        List<CoinBalanceListDto> coinBalanceListDtos = coinBalanceService.findCoinBalanceList();
        return coinBalanceListDtos;
    }

    @GetMapping("/updatePlatformBalance")
    @ApiOperation("修改平台币种余额（后台管理系统）")
    public void updatePlatformBalance(Long coinId, BigDecimal money) {
        coinBalanceService.addPlatformBalance(coinId, money);
    }
}
