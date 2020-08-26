package com.bosha.dapp.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.entity.SparksFavorite;
import com.bosha.dapp.api.service.FavoriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: FavoriteController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-27 17:48
 */
@Api(tags = "收藏")
@RestController
@Slf4j
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/favorite")
public class FavoriteController extends BaseController {

    @Autowired
    private FavoriteService favoriteService;

    @ApiOperation("添加收藏")
    @PostMapping("/add")
    public Long add(@RequestBody @Validated SparksFavorite favorite) {
        favorite.setAddress(getAddress());
        return favoriteService.add(favorite);
    }

    @ApiOperation("取消收藏")
    @PostMapping("/cancel")
    public boolean cancel(@RequestBody @Validated SparksFavorite favorite) {
        favorite.setAddress(getAddress());
        return favoriteService.cancel(favorite);
    }

}
