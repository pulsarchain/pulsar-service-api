package com.bosha.user.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.contract.api.entity.ContractCoinTask;
import com.bosha.contract.api.service.ContractCoinTaskService;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.ContractCoinDto;
import com.bosha.user.api.dto.ContractCoinListDto;
import com.bosha.user.api.service.ContractCoinService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserService
 * @Author liqingping
 * @Date 2018-12-20 10:07
 */

@RestController
@Api(tags = "合约币接口")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/contractCoin")
@Slf4j
public class ContractCoinController extends BaseController {
    @Autowired
    private ContractCoinService contractCoinService;
    @Autowired
    private ContractCoinTaskService contractCoinTaskService;

    @ApiOperation("添加合约币信息")
    @PostMapping("/save")
    public void save(@RequestBody ContractCoinDto contractCoinDto) {
        contractCoinDto.setUserAddress(getAddress());
        contractCoinService.save(contractCoinDto);
        ContractCoinTask build = ContractCoinTask.builder()
                .status(2)
                .hash(contractCoinDto.getHash())
                .userAddress(contractCoinDto.getUserAddress()).build();
        contractCoinTaskService.save(build);

    }

    @ApiOperation("查询合约币列表")
    @GetMapping("/list")
    public PageInfo<ContractCoinListDto> list(Page page) {
        return contractCoinService.list(page);
    }
}
