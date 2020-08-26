package com.bosha.user.api.service;

import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.AddUserFriendDto;
import com.bosha.user.api.dto.UserFollowListDto;
import com.bosha.user.api.dto.UserFriendDto;
import com.bosha.user.api.dto.UserTextFollowDto;
import com.bosha.user.api.entity.UserFollow;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(UserServiceConstants.SERVER_NAME)//服务名
@RequestMapping(UserServiceConstants.SERVER_PRIFEX + "/textFollow")//内部服务前缀
@Api(tags = "地址溥管理")
public interface UserFollowService {
    @ApiOperation("用户添加或者取消关注")
    @PostMapping("/addOrDeleteFollow")
    void addOrDeleteFollow(@RequestParam("followUserAddress") String followUserAddress, @RequestParam("userAddress") String userAddress);


    @ApiOperation("我关注的用户地址薄")
    @GetMapping("/selectUserFollow")
    List<UserFollowListDto> selectUserFollow(@RequestParam("userAddress") String userAddress);


    @ApiOperation("我的已关注好友")
    @GetMapping("/selectUserFriend")
    List<UserFollowListDto> selectUserFriend(@RequestParam("userAddress") String userAddress);


    @ApiOperation("关注我的好友")
    @GetMapping("/selectCoverUserFriend")
    List<UserFollowListDto> selectCoverUserFriend(@RequestParam("userAddress") String address);


    @ApiOperation("我的好友列表")
    @GetMapping("/selectFriend")
    List<UserFollowListDto> selectFriend(@RequestParam("userAddress") String address);



    @ApiOperation("查询用户是否关注")
    @GetMapping("/selectCountByAddress")
    Integer selectCountByAddress(@RequestParam("userAddress") String userAddress, @RequestParam("followUserAddress") String followUserAddress);


    @ApiOperation("用户好友")
    @PostMapping("/addFriend")
    void addFriend(@RequestBody AddUserFriendDto addUserFriendDto);

    @ApiOperation("删除好友")
    @PostMapping("/deleteFriend")
    void deleteFriend(@RequestParam("followUserAddress") String followUserAddress, @RequestParam("userAddress") String userAddress);

    @ApiOperation("修改好友备注信息")
    @PostMapping("/update")
    void update(@RequestBody UserFollow userFollow);

    @ApiOperation("查询用户")
    @PostMapping("/searchAddress")
    UserFriendDto searchAddress(@RequestParam("address") String address, @RequestParam("userAddress") String userAddress);


}
