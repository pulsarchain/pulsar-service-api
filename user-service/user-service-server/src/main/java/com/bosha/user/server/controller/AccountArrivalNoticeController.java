package com.bosha.user.server.controller;

import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.BlockDto;
import com.bosha.user.api.service.AccountArrivalNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AccountArrivalNoticeController
 * @Author liqingping
 * @Date 2020-02-10 9:38
 */

@RestController
@Api(tags = "地址到账通知")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/notice")
@Slf4j
public class AccountArrivalNoticeController {

    @Autowired
    private AccountArrivalNoticeService accountArrivalNoticeService;

    @ApiOperation("地址到账通知")
    @PostMapping({"/", ""})
    void notice(@RequestBody BlockDto blockDto) {
        log.info("区块到账通知---> blockNumber={}，size={}", blockDto.getBlockNumber(), blockDto.getTransactions().size());
        accountArrivalNoticeService.confirm(blockDto);
    }
}
