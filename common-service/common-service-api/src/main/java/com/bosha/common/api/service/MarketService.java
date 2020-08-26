package com.bosha.common.api.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.market.CoinPrice;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(CommonServiceConstants.SERVER_NAME)
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/market")
public interface MarketService {

    @ApiOperation("行情列表")
    @GetMapping("/list")
    List<CoinPrice> list();

    @ApiOperation("根据币种查询价格")
    @GetMapping("/{coin}")
    CoinPrice getByName(@PathVariable("coin") String coinName);

    @ApiOperation("根据币种查询价格")
    @PostMapping("/names")
    Map<String, CoinPrice> getByNames(@RequestBody @Validated @NotEmpty(message = "size不可为空") List<String> names);

    @ApiOperation("根据交易方式（USDT等）获取 cny/usd 等价格")
    @GetMapping("/legalTender")
    Map<String, BigDecimal> getLegalTender(@RequestParam("tradeType") String tradeType);

}
