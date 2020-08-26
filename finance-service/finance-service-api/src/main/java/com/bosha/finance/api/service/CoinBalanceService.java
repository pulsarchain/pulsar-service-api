package com.bosha.finance.api.service;

import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.response.CoinBalanceListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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
@Api(tags = "币种余额service接口")
public interface CoinBalanceService {

    @ApiOperation("查询所有的币种余额并分页（后台管理系统）")
    @PostMapping("/findCoinBalanceList")
    List<CoinBalanceListDto> findCoinBalanceList();

    @ApiOperation("修改平台币种余额（后台管理系统）")
    @PostMapping("/addPlatformBalance")
    void addPlatformBalance(@RequestParam("coinId") Long coinId, @RequestParam("money") BigDecimal money);
}
