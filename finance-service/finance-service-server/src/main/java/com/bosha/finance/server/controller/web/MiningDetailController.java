package com.bosha.finance.server.controller.web;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.commons.enums.LanguageEnum;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.response.CoinAssetListDto;
import com.bosha.finance.api.dto.response.CoinDetailListDto;
import com.bosha.finance.api.dto.response.MiningDetailListDto;
import com.bosha.finance.api.dto.response.MiningDetailStatisticsDto;
import com.bosha.finance.api.service.CoinService;
import com.bosha.finance.api.service.MiningDetailService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "挖矿明细")
@RestController
@Slf4j
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/mining")
public class MiningDetailController extends BaseController {
    @Autowired
    private MiningDetailService miningDetailService;

    @ApiOperation("查询用户收益")
    @GetMapping("/findMiningDetail")
    public MiningDetailStatisticsDto findMiningDetail() {
        return miningDetailService.findMiningDetail(getUserId());
    }


    @ApiOperation("查询用户收益明细列表")
    @GetMapping("/findMiningDetailPage")
    public PageInfo<MiningDetailListDto> findMiningDetailPage(Page page) {
        PageInfo<MiningDetailListDto> miningDetailPage = miningDetailService.findMiningDetailPage(page.getPage(), page.getSize(), getUserId());
        return miningDetailPage;
    }
}
