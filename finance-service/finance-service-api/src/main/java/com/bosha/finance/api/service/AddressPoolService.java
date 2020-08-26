package com.bosha.finance.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.CoinDto;
import com.bosha.finance.api.dto.request.QueryCoinDto;
import com.bosha.finance.api.dto.response.AddressPoolDetailDto;
import com.bosha.finance.api.dto.response.AddressPoolListDto;
import com.bosha.finance.api.dto.response.CoinListDto;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "地址池server层接口")
public interface AddressPoolService {

    @ApiOperation("查询地址池 （后台管理系统）")
    @GetMapping("/findAddressPool")
    List<AddressPoolListDto> findAddressPool();

    @ApiOperation("查询地址池详细信息 （后台管理系统）")
    @GetMapping("/findAddressPoolDetail")
    PageInfo<AddressPoolDetailDto> findAddressPoolDetail(@RequestParam("id") Long id,
                                                         @RequestParam("page") Integer page, @RequestParam("size") Integer size);
}
