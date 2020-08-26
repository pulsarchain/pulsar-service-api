package com.bosha.star.server.controller;

import java.util.List;


import com.bosha.commons.controller.BaseController;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.web.BuyGiftDto;
import com.bosha.star.api.dto.web.GiftAssetDto;
import com.bosha.star.api.dto.web.SendGiftDto;
import com.bosha.star.api.entity.LiveMiningGift;
import com.bosha.star.api.service.LiveGiftService;
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
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningController
 * @Author liqingping
 * @Date 2020-03-25 12:17
 */
@Api(tags = "直播礼物")
@Slf4j
@RestController
@RequestMapping(StarServiceConstants.WEB_PRIFEX + "/liveMining/gift")
public class GiftController extends BaseController {

    @Autowired
    private LiveGiftService giftService;

    @ApiOperation("我的礼物资产")
    @GetMapping("/asset")
    List<GiftAssetDto> asset() {
        return giftService.asset(getAddress());
    }

    @ApiOperation("礼物列表")
    @GetMapping("/list")
    List<LiveMiningGift> giftList() {
        return giftService.giftList();
    }

    @ApiOperation("购买礼物")
    @PostMapping("/buy")
    void buy(@RequestBody @Validated BuyGiftDto buy) {
        giftService.buy(buy);
    }

    @ApiOperation("送礼物")
    @PostMapping("/send")
    void send(@RequestBody @Validated SendGiftDto sendGift) {
        sendGift.setAddress(getAddress());
        giftService.send(sendGift);
    }
}
