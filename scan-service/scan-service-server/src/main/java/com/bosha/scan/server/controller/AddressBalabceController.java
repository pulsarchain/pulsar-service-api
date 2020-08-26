package com.bosha.scan.server.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.commons.utils.AddressValidator;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.scan.api.constants.ScanServiceConstants;
import com.bosha.scan.api.dto.AddressBalanceInfoDto;
import com.bosha.scan.api.dto.AddressStatisticsInfo;
import com.bosha.scan.api.dto.TransactionsDto;
import com.bosha.scan.api.entity.AddressBalance;
import com.bosha.scan.api.service.AddressBalanceService;
import com.bosha.scan.server.config.ScanServiceConfig;
import com.bosha.scan.server.redis.CacheKey;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AddressBalabceController
 * @Author liqingping
 * @Date 2020-04-10 17:52
 */
@RestController
@Slf4j
@Api(tags = "地址余额排行榜等")
@RequestMapping(ScanServiceConstants.WEB_PRIFEX)
public class AddressBalabceController extends BaseController {

    @Autowired
    private AddressBalanceService addressBalanceService;

    @ApiOperation("地址排行榜")
    @GetMapping("/rank")
    PageInfo<AddressBalance> list(@Validated @Min(value = 1, message = "") @RequestParam Integer page,
                                  @RequestParam @Validated @Max(value = 25, message = "") Integer size) {
        Page p = new Page();
        p.setPage(page);
        p.setSize(size > 25 ? 25 : size);
        return addressBalanceService.list(p);
    }

    @ApiOperation("占比图")
    @GetMapping("/percent")
    AddressBalanceInfoDto info() {
        return addressBalanceService.info();
    }

}
