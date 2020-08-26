package com.bosha.star.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.web.LiveAcceptDto;
import com.bosha.star.api.dto.web.LiveConfirmDto;
import com.bosha.star.api.dto.web.LiveInfoDto;
import com.bosha.star.api.dto.web.LiveMiningDto;
import com.bosha.star.api.dto.web.LiveMiningListQueryDto;
import com.bosha.star.api.dto.web.LiveMiningMineDto;
import com.bosha.star.api.dto.web.LiveMiningMinerDto;
import com.bosha.star.api.entity.LiveMining;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(StarServiceConstants.SERVER_NAME)//服务名
@RequestMapping(StarServiceConstants.SERVER_PRIFEX + "/liveMining")//内部服务前缀
@Api(tags = " 直播挖矿server层接口")
public interface LiveMiningService {

    @ApiOperation("直播挖矿的信息")
    @GetMapping("/info")
    LiveInfoDto info(@RequestParam("address") String address);

    @ApiOperation("创建直播挖矿")
    @PostMapping("/create")
    Long create(@RequestBody @Validated LiveMining liveMining);

    @ApiOperation("更新")
    @PostMapping("/update")
    LiveMining update(@RequestBody LiveMining liveMining);

    @ApiOperation("接受邀请")
    @PostMapping("/accept")
    void accept(@RequestBody LiveAcceptDto accept);

    @ApiOperation("直播列表")
    @PostMapping("/list")
    PageInfo<LiveMiningDto> list(@RequestBody LiveMiningListQueryDto dto);

    @ApiOperation("搜索")
    @GetMapping("/list/search")
    PageInfo<LiveMiningDto> search(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page, @RequestParam("size") Integer size);

    @ApiOperation("直播列表")
    @PostMapping("/historyList")
    PageInfo<LiveMiningDto> historyList(@RequestBody LiveMiningListQueryDto dto);

    @ApiOperation("我发起的直播详情")
    @GetMapping("/mine/{id}")
    LiveMiningMineDto mine(@PathVariable("id") Long id);

    @ApiOperation("我发起的直播列表")
    @GetMapping("/mine/list")
    PageInfo<LiveMiningMineDto> mineList(@ModelAttribute Page page);

    @ApiOperation("我参与的挖矿详情")
    @GetMapping("/miner/{id}")
    LiveMiningMinerDto miner(@PathVariable("id") Long id);

    @ApiOperation("我参与的挖矿列表")
    @GetMapping("/miner/list")
    PageInfo<LiveMiningMinerDto> minerList(@ModelAttribute Page page);

    @ApiOperation("是否可以开播")
    @GetMapping("/confirm")
    LiveConfirmDto confirm(@RequestParam("address") String address, @RequestParam("id") Long id);

    @ApiOperation("导入用户到im")
    @PostMapping("/importToIm")
    void importToIm(@RequestParam("address") String address);

    @ApiOperation(" 创建聊天室")
    @GetMapping("/createAvChatRoom")
    void createAvChatRoom(@RequestParam("id") Long id);

    @ApiOperation("主播离开了直播间")
    @PostMapping("/left")
    void left(@RequestParam("address") String address, @RequestParam("liveMiningId") Long liveMiningId);

    @ApiOperation("获取直播信息")
    @GetMapping("/getById")
    LiveMining getById(@RequestParam("liveMiningId") Long liveMiningId);
}
