package com.bosha.star.api.service;

import java.math.BigDecimal;
import java.util.List;


import com.bosha.commons.dto.Page;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.server.InsertStarRewardCharityDto;
import com.bosha.star.api.dto.server.UpdateStarRewardDto;
import com.bosha.star.api.dto.web.CreateStarDto;
import com.bosha.star.api.dto.web.JoinStarDto;
import com.bosha.star.api.dto.web.JoinStarResultDto;
import com.bosha.star.api.dto.web.MyRecommendDto;
import com.bosha.star.api.dto.web.QueryStarListDto;
import com.bosha.star.api.dto.web.StarDetailDto;
import com.bosha.star.api.dto.web.StarListDto;
import com.bosha.star.api.entity.Star;
import com.bosha.star.api.entity.StarMember;
import com.bosha.star.api.entity.StarReward;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(StarServiceConstants.SERVER_NAME)//服务名
@RequestMapping(StarServiceConstants.SERVER_PRIFEX)//内部服务前缀
@Api(tags = " 星系服务server层接口")
public interface StarService {

    @ApiOperation("创建星系")
    @PostMapping("/create")
    Long create(@RequestBody CreateStarDto create);

    @ApiOperation("加入星系")
    @PostMapping("/join")
    JoinStarResultDto join(@RequestBody JoinStarDto join);

    @ApiOperation("星系列表")
    @GetMapping("/list")
    PageInfo<StarListDto> list(@ModelAttribute QueryStarListDto query);

    @ApiOperation("星系详情")
    @GetMapping("/detail/{id}")
    StarDetailDto detail(@PathVariable("id") Long id, @RequestParam(value = "address", required = false, defaultValue = "") String address);

    @ApiOperation("我的星系")
    @GetMapping("/my")
    StarDetailDto my(@RequestParam(value = "address") String address);

    @ApiOperation("我的推荐")
    @GetMapping("/myRecommend")
    PageInfo<MyRecommendDto> myRecommend(@ModelAttribute Page page);

    @ApiOperation("是否已创建或加入过星系")
    @GetMapping("/exist")
    Integer exist(@RequestParam("address") String address);

    @ApiOperation("根据id获取星系")
    @GetMapping("/getById")
    Star getById(@RequestParam("id") Long id);

    @ApiOperation("根据地址获取星系")
    @GetMapping("/getStar")
    Star getStar(@RequestParam("address") String address);

    @ApiOperation("更新星系")
    @PostMapping("/updateStar")
    Star updateStar(@RequestBody Star star);

    @ApiOperation("根据地址获取星系成员")
    @GetMapping("/getStarMember")
    StarMember getStarMember(@RequestParam("address") String address);

    @ApiOperation("根据地址获取星系成员")
    @GetMapping("/getStarMemberFromCache")
    StarMember getStarMemberFromCache(@RequestParam("address") String address);

    @ApiOperation("根据地址获取用户算力值")
    @GetMapping("/getStarHz")
    BigDecimal getStarHz(@RequestParam("address") String address);

    @ApiOperation("更新星系成员")
    @PostMapping("/updateStarMember")
    StarMember updateStarMember(@RequestBody StarMember starMember);

    @ApiOperation("新增星系奖励发放记录")
    @PostMapping("/starReward/insertBatch")
    List<StarReward> insertStarRewardBatch(@RequestParam("starMemberId") Long starMemberId);

    @ApiOperation("新增星系奖励发放记录")
    @PostMapping("/starReward/insertCharity")
    StarReward insertStarRewardCharity(@RequestBody InsertStarRewardCharityDto insert);

    @ApiOperation("批量更新星系奖励状态")
    @PostMapping("/starReward/updateBatch")
    boolean updateStarRewardBatch(@RequestBody List<UpdateStarRewardDto> list);

}
