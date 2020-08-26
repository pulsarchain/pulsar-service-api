package com.bosha.finance.server.controller.manager;

import com.bosha.commons.controller.BaseController;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import com.bosha.finance.api.dto.request.QueryTransactionBillDto;
import com.bosha.finance.api.dto.request.WithDrawDto;
import com.bosha.finance.api.dto.response.IntoTransactionBillListDto;
import com.bosha.finance.api.dto.response.OutTransactionBillListDto;
import com.bosha.finance.api.dto.response.TransactionBillListDto;
import com.bosha.finance.api.service.TransactionBillService;
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
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/manager/bill")
public class TransactionBillManagerController extends BaseController {
    @Autowired
    private TransactionBillService transactionBillService;


    @GetMapping("/findIntoTransactionBillPage")
    @ApiOperation("查询转入记录并分页（后台管理系统）")
    public PageInfo<IntoTransactionBillListDto> findIntoTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto) {
        PageInfo<IntoTransactionBillListDto> intoTransactionBillListDtoPageInfo = transactionBillService.findIntoTransactionBillPage(queryTransactionBillDto);
        return intoTransactionBillListDtoPageInfo;
    }

    @GetMapping("/findOutTransactionBillPage")
    @ApiOperation("查询转出记录并分页 （后台管理系统）")
    public PageInfo<OutTransactionBillListDto> findOutTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto) {
        PageInfo<OutTransactionBillListDto> intoTransactionBillListDtoPageInfo = transactionBillService.findOutTransactionBillPage(queryTransactionBillDto);
        return intoTransactionBillListDtoPageInfo;
    }

    @GetMapping("/findTransactionBillPage")
    @ApiOperation("查询所有的交易记录 （后台管理系统）")
    public PageInfo<TransactionBillListDto> findTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto) {
        PageInfo<TransactionBillListDto> transactionBillListDtoPageInfo = transactionBillService.findTransactionBillPage(queryTransactionBillDto);
        return transactionBillListDtoPageInfo;
    }

    @PostMapping("/auditWithdraw")
    @ApiOperation("审核提币 （后台管理系统）")
    public void auditWithdraw(@RequestBody WithDrawDto withDrawDto) {
        withDrawDto.setOperator(super.getRequestInfo().getAdminUserName());
        transactionBillService.auditWithdraw(withDrawDto);
    }


    @GetMapping("/findFinanceType")
    @ApiOperation("查询所有业务类型（后台管理系统）")
    public List<FinanceServiceTypeEnum> findCoinDict() {
        List<FinanceServiceTypeEnum> financeServiceTypeEnums = FinanceServiceTypeEnum.selectEnums();
        return financeServiceTypeEnums;
    }
}
