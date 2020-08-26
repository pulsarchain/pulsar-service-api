package com.bosha.finance.server.controller.web;

import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.enums.DictTypeEnum;
import com.bosha.common.api.service.DictService;
import com.bosha.commons.controller.BaseController;
import com.bosha.finance.api.constants.FinanceServiceConstants;
import com.bosha.finance.api.dto.request.CoinDto;
import com.bosha.finance.api.dto.request.QueryCoinDto;
import com.bosha.finance.api.dto.response.CoinListDto;
import com.bosha.finance.api.entity.Asset;
import com.bosha.finance.api.service.AssetService;
import com.bosha.finance.api.service.CoinService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "资产管理")
@RestController
@Slf4j
@RequestMapping(FinanceServiceConstants.WEB_PRIFEX + "/asset")
public class AssetController extends BaseController {
    @Autowired
    private AssetService assetService;

    @GetMapping("/getUserAssetAddress")
    @ApiOperation("充币时获取用户地址（web）")
    public Asset getUserAssetAddress(Long coinId) {
        Long userId = getUserId();
        return assetService.getUserCoinAsset(coinId, userId);
    }

}
