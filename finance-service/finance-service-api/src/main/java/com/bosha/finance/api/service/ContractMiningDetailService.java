package com.bosha.finance.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.BatchContractMiningDetailDto;
import com.bosha.finance.api.dto.request.ContractMiningDetailDto;
import com.bosha.finance.api.dto.response.ContractMiningDetailListDto;
import com.bosha.finance.api.entity.ContractMiningDetail;
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
@RequestMapping(FinanceServiceConstants.SERVER_PRIFEX + "/contractMining")//内部服务前缀
@Api(tags = "挖矿明细service接口")
public interface ContractMiningDetailService {


    @ApiOperation("查询总收益")
    @GetMapping("/total")
    BigDecimal total(@RequestParam("address") String address);

    @ApiOperation("查询收益明细并分页")
    @GetMapping("/findPage")
    PageInfo<ContractMiningDetailListDto> list(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                               @RequestParam("address") String address);
    @ApiOperation("插入用户收益")
    @PostMapping("/insert")
    Long insert(@RequestBody ContractMiningDetailDto contractMiningDetailDto);

    @ApiOperation("批量插入")
    @PostMapping("/batchInsert")
    Map<String,Long> batchInsert(@RequestBody List<ContractMiningDetail> contractMiningDetailDto);

    @ApiOperation("批量插入")
    @PostMapping("/batchInsertWithType")
    Map<String,Long> batchInsertWithType(@RequestBody List<ContractMiningDetail> contractMiningDetailDto);

    @ApiOperation("批量修改")
    @PostMapping("/batchUpdate")
    boolean batchUpdate(@RequestBody BatchContractMiningDetailDto batchContractMiningDetailDto);


    @ApiOperation("修改用户收益")
    @PostMapping("/update")
    ContractMiningDetail update(@RequestBody ContractMiningDetail contractMiningDetail);

}
