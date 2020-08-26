package com.bosha.finance.api.service;

import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.response.MyEarningsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserService
 * @Author liqingping
 * @Date 2018-12-20 10:07
 */

@FeignClient(FinanceServiceConstants.SERVER_NAME)//服务名
@RequestMapping(FinanceServiceConstants.SERVER_PRIFEX + "/myEarnings")//内部服务前缀
@Api(tags = "我的收益统计server层接口")
public interface MyEarningsService {

    @ApiOperation("我的收益")
    @GetMapping
    MyEarningsDto myEarnings(@RequestParam("address") String address);

}
