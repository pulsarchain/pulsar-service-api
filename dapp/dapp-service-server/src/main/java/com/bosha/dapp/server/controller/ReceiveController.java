package com.bosha.dapp.server.controller;

import java.util.List;


import com.bosha.commons.controller.BaseController;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.entity.SparksReceiveAccount;
import com.bosha.dapp.api.entity.SparksReceiverAddress;
import com.bosha.dapp.api.service.ReveiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappReportController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-13 12:06
 */
@RestController
@Slf4j
@Api(tags = "收款账户、收货地址")
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/sparks")
public class ReceiveController extends BaseController {

    @Autowired
    private ReveiveService reveiveService;

    @ApiOperation("添加收款账户")
    @PostMapping("/receiveAccount/add")
    Long addReceiveAccount(@RequestBody @Validated SparksReceiveAccount account) {
        account.setAddress(getAddress());
        return reveiveService.addReceiveAccount(account);
    }

    @ApiOperation("更改收款账户")
    @PostMapping("/receiveAccount/update")
    boolean updateReceiveAccount(@RequestBody SparksReceiveAccount account) {
        account.setAddress(getAddress());
        return reveiveService.updateReceiveAccount(account);
    }

    @ApiOperation("收款账户列表")
    @GetMapping("/receiveAccount/list")
    List<SparksReceiveAccount> listReceiveAccount() {
        return reveiveService.listReceiveAccount(getAddress());
    }

    @ApiOperation("添加收货地址")
    @PostMapping("/receiveAddress/add")
    Long addReceiveAddress(@RequestBody @Validated SparksReceiverAddress address){
        address.setAddress(getAddress());
        return reveiveService.addReceiveAddress(address);
    }

    @ApiOperation("更新收货地址")
    @PostMapping("/receiveAddress/update")
    boolean updateReceiveAddress(@RequestBody SparksReceiverAddress address){
        return reveiveService.updateReceiveAddress(address);
    }

    @ApiOperation("收货地址列表")
    @GetMapping("/receiveAddress/list")
    List<SparksReceiverAddress> listReceiveAddress(){
        return reveiveService.listReceiveAddress(getAddress());
    }
}
