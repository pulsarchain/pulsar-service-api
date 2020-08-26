package com.bosha.star.api.service;

import java.util.List;


import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.web.BuyGiftDto;
import com.bosha.star.api.dto.web.GiftAssetDto;
import com.bosha.star.api.dto.web.SendGiftDto;
import com.bosha.star.api.entity.LiveMiningGift;
import com.bosha.star.api.entity.LiveMiningGiftRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(StarServiceConstants.SERVER_NAME)//服务名
@RequestMapping(StarServiceConstants.SERVER_PRIFEX + "/gift")//内部服务前缀
@Api(tags = " 直播礼物server层接口")
public interface LiveGiftService {

    @ApiOperation("我的礼物资产")
    @GetMapping("/asset")
    List<GiftAssetDto> asset(@RequestParam("address") String address);

    @ApiOperation("礼物列表")
    @GetMapping("/list")
    List<LiveMiningGift> giftList();

    @ApiOperation("购买礼物")
    @PostMapping("/buy")
    void buy(@RequestBody BuyGiftDto buy);

    @ApiOperation("送礼物")
    @PostMapping("/send")
    void send(@RequestBody SendGiftDto sendGift);

    @ApiOperation("到账更新")
    @PostMapping("/update")
    void update(@RequestBody LiveMiningGiftRecord record);
}
