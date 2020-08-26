package com.bosha.common.server.controller;

import java.util.List;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.market.CoinPrice;
import com.bosha.common.api.service.MarketService;
import com.bosha.commons.annotation.NoLog;
import com.bosha.commons.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MessageController
 * @Author liqingping
 * @Date 2019-12-12 13:31
 */
@RestController
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + "/market")
@Slf4j
@Api(tags = "行情")
public class MarketController extends BaseController {

    @Autowired
    private MarketService marketService;

    @ApiOperation("行情列表")
    @GetMapping("/list")
    @NoLog
    List<CoinPrice> list() {
        return marketService.list();
    }

}
