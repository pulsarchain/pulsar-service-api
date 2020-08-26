package com.bosha.finance.server.controller.manager;

import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.enums.DictTypeEnum;
import com.bosha.common.api.service.DictService;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.CoinDto;
import com.bosha.finance.api.dto.request.QueryCoinDto;
import com.bosha.finance.api.dto.response.AddressPoolDetailDto;
import com.bosha.finance.api.dto.response.AddressPoolListDto;
import com.bosha.finance.api.dto.response.CoinListDto;
import com.bosha.finance.api.service.AddressPoolService;
import com.bosha.finance.api.service.CoinService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "币种管理")
@RestController
@Slf4j
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/manager/coin")
public class CoinManagerController extends BaseController {
    @Autowired
    private CoinService coinService;
    @Autowired
    private DictService dictService;


    @GetMapping("/findCoinPageInfo")
    @ApiOperation("查询所有的币种并分页（后台管理系统）")
    public PageInfo<CoinListDto> findCoinPageInfo(@RequestBody QueryCoinDto queryCoinDto) {
        PageInfo<CoinListDto> coinListDtoPageInfo = coinService.findCoinPageInfo(queryCoinDto);
        return coinListDtoPageInfo;
    }


    @GetMapping("/findCoinDict")
    @ApiOperation("查询币种公链（后台管理系统）")
    public List<Dict> findCoinDict() {
        List<Dict> dicts = dictService.listType(DictTypeEnum.coin);
        return dicts;
    }


    @PostMapping("/saveOrUpdateCoin")
    @ApiOperation("添加修改币种（后台管理系统）")
    public void findAddressPoolDetail(@RequestBody CoinDto coinDto) {
        coinService.saveOrUpdateCoin(coinDto);
    }


}
