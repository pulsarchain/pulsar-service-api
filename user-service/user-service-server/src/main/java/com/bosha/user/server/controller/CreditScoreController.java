package com.bosha.user.server.controller;

import java.util.Date;


import com.bosha.commons.controller.BaseController;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.entity.AuthorizedCreditScore;
import com.bosha.user.api.entity.CreditScore;
import com.bosha.user.api.service.CreditScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CreditScoreController
 * @Author liqingping
 * @Date 2020-02-09 20:16
 */
@RestController
@Api(tags = "用户信用分")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/creditScore")
@Slf4j
public class CreditScoreController extends BaseController {

    @Autowired
    private CreditScoreService creditScoreService;

    @ApiOperation("获取自己的信用分")
    @GetMapping({"/", ""})
    CreditScore getByAddress() {
        return creditScoreService.getByAddress(getAddress());
    }

    @ApiOperation("增加授权信用分的记录（获取授权转账之后调用）")
    @PostMapping("/authorized/add")
    Long addAuthorizedCreditScore(@ApiParam(value = "要查看的用户地址", required = true) @RequestParam("address") String address) {
        if (StringUtils.equalsIgnoreCase(address, getAddress()))
            return null;
        AuthorizedCreditScore build = AuthorizedCreditScore.builder()
                .createTime(new Date()).from(getAddress()).to(address).status(AuthorizedCreditScore.STATUS_CONFIRMING)
                .build();
        return creditScoreService.addAuthorizedCreditScore(build);
    }

    @ApiOperation("查看用户信用分")
    @GetMapping("/authorized/view/{address}")
    CreditScore view(@ApiParam(value = "要查看的用户地址") @PathVariable("address") String address) {
        if (StringUtils.equalsIgnoreCase(address, getAddress()))
            return getByAddress();
        return creditScoreService.view(address, getAddress());
    }

    @ApiOperation("根据信用分获取数量")
    @GetMapping("/countByRange")
    int countByRange(@RequestParam("min") int min, @RequestParam("max") int max) {
        return creditScoreService.countByRange(min, max);
    }
}
