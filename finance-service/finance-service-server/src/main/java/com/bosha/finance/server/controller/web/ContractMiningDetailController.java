package com.bosha.finance.server.controller.web;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.response.ContractMiningDetailListDto;
import com.bosha.finance.api.dto.response.MiningDetailListDto;
import com.bosha.finance.api.dto.response.MiningDetailStatisticsDto;
import com.bosha.finance.api.service.ContractMiningDetailService;
import com.bosha.finance.api.service.MiningDetailService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api(tags = "合约挖矿明细（新）")
@RestController
@Slf4j
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/contractMining")
public class ContractMiningDetailController extends BaseController {
    @Autowired
    private ContractMiningDetailService contractMiningDetailService;

    @ApiOperation("查询总收益")
    @GetMapping("/total")
    public BigDecimal total() {
        return contractMiningDetailService.total(getAddress());
    }


    @ApiOperation("查询收益明细列表")
    @GetMapping("/listPage")
    public PageInfo<ContractMiningDetailListDto> listPage(Page page) {
        PageInfo<ContractMiningDetailListDto> miningDetailPage = contractMiningDetailService.list(page.getPage(), page.getSize(), getAddress());
        return miningDetailPage;
    }
}
