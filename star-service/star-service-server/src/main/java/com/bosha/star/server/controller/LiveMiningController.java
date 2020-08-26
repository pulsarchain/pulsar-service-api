package com.bosha.star.server.controller;

import java.util.Arrays;
import java.util.Collections;


import com.bosha.commons.annotation.NoLog;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.web.LiveAcceptDto;
import com.bosha.star.api.dto.web.LiveConfirmDto;
import com.bosha.star.api.dto.web.LiveInfoDto;
import com.bosha.star.api.dto.web.LiveMiningDto;
import com.bosha.star.api.dto.web.LiveMiningListQueryDto;
import com.bosha.star.api.dto.web.LiveMiningMineDto;
import com.bosha.star.api.dto.web.LiveMiningMinerDto;
import com.bosha.star.api.dto.web.LiveRoomInfoDto;
import com.bosha.star.api.dto.web.MiningDto;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.api.service.LiveMiningDetailService;
import com.bosha.star.api.service.LiveMiningService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningController
 * @Author liqingping
 * @Date 2020-03-25 12:17
 */
@Api(tags = "直播挖矿")
@Slf4j
@RestController
@RequestMapping(StarServiceConstants.WEB_PRIFEX + "/liveMining")
public class LiveMiningController extends BaseController {

    @Autowired
    private LiveMiningService liveMiningService;
    @Autowired
    private LiveMiningDetailService liveMiningDetailService;

    @ApiOperation("直播挖矿的信息")
    @GetMapping("/info")
    LiveInfoDto info() {
        return liveMiningService.info(getAddress());
    }

    @ApiOperation("创建直播挖矿")
    @PostMapping("/create")
    Long create(@RequestBody @Validated LiveMining liveMining) {
        return liveMiningService.create(liveMining);
    }

    @ApiOperation("接受邀请")
    @PostMapping("/accept")
    void accept(@RequestBody LiveAcceptDto accept) {
        liveMiningService.accept(accept);
    }

    @ApiOperation("直播列表")
    @GetMapping("/list")
    PageInfo<LiveMiningDto> list(@ModelAttribute @Validated Page page) {
        LiveMiningListQueryDto build = LiveMiningListQueryDto.builder()
                .page(page).list(Arrays.asList(LiveMining.STATUS_CONFIRMING, LiveMining.STATUS_TO_BE_LIVE, LiveMining.STATUS_LIVING))
                .build();
        return liveMiningService.list(build);
    }

    @ApiOperation("搜索")
    @GetMapping("/list/search")
    PageInfo<LiveMiningDto> search(@ModelAttribute @Validated Page page, @ApiParam(value = "搜索直播的关键字") @RequestParam("keyword") String keyword) {
        return liveMiningService.search(keyword, page.getPage(), page.getSize());
    }

    @ApiOperation("往期回顾列表")
    @GetMapping("/histories")
    PageInfo<LiveMiningDto> histories(@ModelAttribute @Validated Page page) {
        LiveMiningListQueryDto build = LiveMiningListQueryDto.builder()
                .page(page).list(Collections.singletonList(LiveMining.STATUS_FINISHED))
                .build();
        return liveMiningService.historyList(build);
    }

    @ApiOperation("我发起的直播详情")
    @GetMapping("/mine/{id}")
    LiveMiningMineDto mine(@ApiParam(value = "直播挖矿的id", required = true) @PathVariable("id") Long id) {
        return liveMiningService.mine(id);
    }

    @ApiOperation("我发起的直播列表")
    @GetMapping("/mine/list")
    PageInfo<LiveMiningMineDto> mineList(@ModelAttribute Page page) {
        return liveMiningService.mineList(page);
    }

    @ApiOperation("我参与的挖矿详情")
    @GetMapping("/miner/{id}")
    LiveMiningMinerDto miner(@ApiParam(value = "直播挖矿的id", required = true) @PathVariable("id") Long id) {
        return liveMiningService.miner(id);
    }

    @ApiOperation("我参与的挖矿列表")
    @GetMapping("/miner/list")
    PageInfo<LiveMiningMinerDto> minerList(@ModelAttribute Page page) {
        return liveMiningService.minerList(page);
    }

    @ApiOperation("主播查询自己是否可以开播，可以时会返回推流地址")
    @GetMapping("/confirm/{id}")
    LiveConfirmDto confirm(@ApiParam(value = "直播挖矿的id", required = true) @PathVariable("id") Long id) {
        return liveMiningService.confirm(getAddress(), id);
    }

    @ApiOperation("直播间信息")
    @GetMapping("/liveRoom/info/{id}")
    LiveRoomInfoDto liveRoomInfo(@ApiParam(value = "直播挖矿的id", required = true) @PathVariable("id") Long id) {
        return liveMiningDetailService.liveRoomInfo(id, false);
    }

    @ApiOperation("挖矿（观看、点赞、分享、评论、礼物）")
    @NoLog
    @PostMapping("/mining")
    @RedissonDistributedLock(key = {"@address", "#miningDto.liveMiningId", "#miningDto.type"},
            leaseTime = 60, waitTime = 1, automaticRelease = false, failStrategy = RedissonDistributedLock.FailStrategy.RETURN_NULL)
    public void mining(@RequestBody @Validated MiningDto miningDto) {
        liveMiningDetailService.mining(miningDto);
    }

    @ApiOperation("主播主动离开直播间")
    @PostMapping("/left")
    public void left(@RequestParam("liveMiningId") Long liveMiningId) {
        liveMiningService.left(getAddress(), liveMiningId);
    }
}
