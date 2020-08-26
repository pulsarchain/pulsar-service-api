package com.bosha.finance.api.service;

import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.MiningDetailDto;
import com.bosha.finance.api.dto.response.MiningDetailListDto;
import com.bosha.finance.api.dto.response.MiningDetailStatisticsDto;
import com.bosha.finance.api.entity.MiningDetail;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(FinanceServiceConstants.SERVER_NAME)//服务名
@RequestMapping(FinanceServiceConstants.SERVER_PRIFEX + "/miningDetail")//内部服务前缀
@Api(tags = "挖矿明细service接口")
public interface MiningDetailService {


    @ApiOperation("查询用户收益")
    @GetMapping("/findMiningDetail")
    MiningDetailStatisticsDto findMiningDetail(Long userId);

    @ApiOperation("查询用户收益")
    @GetMapping("/findMiningDetailPage")
    PageInfo<MiningDetailListDto> findMiningDetailPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                       @RequestParam("userId") Long userId);

    @ApiOperation("查询用户收益")
    @PostMapping("/insertMiningDetail")
    Long insertMiningDetail(@RequestBody MiningDetailDto miningDetailDto);

    @ApiOperation("修改用户收益明细")
    @PostMapping("/updateMiningDetail")
    void updateMiningDetail(@RequestBody MiningDetailDto miningDetailDto);

    @ApiOperation("修改用户收益明细")
    @PostMapping("/update")
    MiningDetail update(@RequestBody MiningDetail miningDetail);

}
