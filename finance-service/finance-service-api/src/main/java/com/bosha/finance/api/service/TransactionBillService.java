package com.bosha.finance.api.service;

import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.*;
import com.bosha.finance.api.dto.response.*;
import com.github.pagehelper.PageInfo;
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
@Api(tags = "交易记录服务接口")
public interface TransactionBillService {

    @ApiOperation("查询转入记录并分页 （后台管理系统）")
    @PostMapping("/findIntoTransactionBillPage")
    PageInfo<IntoTransactionBillListDto> findIntoTransactionBillPage(@RequestBody QueryTransactionBillDto queryTransactionBillDto);

    @ApiOperation("查询转出记录并分页 （后台管理系统）")
    @PostMapping("/findOutTransactionBillPage")
    PageInfo<OutTransactionBillListDto> findOutTransactionBillPage(@RequestBody QueryTransactionBillDto queryTransactionBillDto);

    @ApiOperation("查询所有的交易记录 （后台管理系统）")
    @PostMapping("/findTransactionBillPage")
    PageInfo<TransactionBillListDto> findTransactionBillPage(@RequestBody QueryTransactionBillDto queryTransactionBillDto);

    @ApiOperation("审核提币 （后台管理系统）")
    @PostMapping("/auditWithdraw")
    void auditWithdraw(@RequestBody WithDrawDto withDrawDto);

    @ApiOperation("用户的交易记录（Web）")
    @GetMapping("/findUserBillByUserIdAndCoinId")
    PageInfo<UserTransactionBillListDto> findUserBillByUserIdAndCoinId(@RequestBody QueryUserTransactionBillDto queryUserTransactionBillDto);

    @ApiOperation("用户的交易记录（Web）")
    @PostMapping("/withDraw")
    void withDraw(@RequestBody WithDrawCoinDto withDrawCoinDto);

    @ApiOperation("用户的充币交易记录（Web）")
    @PostMapping("/charging")
    void charging(@RequestBody ChargingDto chargingDto);
}
