package com.bosha.finance.server.controller.web;

import com.bosha.commons.controller.BaseController;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.response.MyEarningsDto;
import com.bosha.finance.api.service.MyEarningsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MyEarningsController
 * @Author liqingping
 * @Date 2020-04-15 9:28
 */
@RestController
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/myEarnings")
public class MyEarningsController extends BaseController {

    @Autowired
    private MyEarningsService myEarningsService;

    @ApiOperation("我的收益")
    @GetMapping
    MyEarningsDto myEarnings() {
        return myEarningsService.myEarnings(getAddress());
    }
}
