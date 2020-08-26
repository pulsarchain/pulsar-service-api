package com.bosha.finance.server.controller.web;

import com.bosha.commons.controller.BaseController;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.QueryUserTransactionBillDto;
import com.bosha.finance.api.dto.request.WithDrawCoinDto;
import com.bosha.finance.api.service.TransactionBillService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "交易管理")
@RestController
@Slf4j
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/transaction")
public class TransactionBillController extends BaseController {
    @Autowired
    private TransactionBillService transactionBillService;

    @GetMapping("/findUserTransactionBillPage")
    @ApiOperation("获取用户的交易记录（web）")
    public PageInfo findUserTransactionBillPage(QueryUserTransactionBillDto queryUserTransactionBillDto) {
        queryUserTransactionBillDto.setUserId(getUserId());
        return transactionBillService.findUserBillByUserIdAndCoinId(queryUserTransactionBillDto);
    }

    @PostMapping("/withDraw")
    @ApiOperation("提币")
    public void withDraw(WithDrawCoinDto withDrawCoinDto){
        transactionBillService.withDraw(withDrawCoinDto);
    }

}
