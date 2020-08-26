package com.bosha.user.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.AddUserFriendDto;
import com.bosha.user.api.dto.ImGroupMemberDto;
import com.bosha.user.api.dto.UserFollowListDto;
import com.bosha.user.api.dto.UserFriendDto;
import com.bosha.user.api.entity.UserFollow;
import com.bosha.user.api.service.UserFollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: Test
 * @Author liqingping
 * @Date 2019-11-27 13:30
 */
@RestController
@Api(tags = "用户地址薄")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/follow")
public class UserFollowController extends BaseController {
    @Autowired
    private UserFollowService userTextFollowService;

    @ApiOperation("添加删除关注")
    @PostMapping("/addOrDeleteFollow/{followUserAddress}")
    public void addOrDeleteFollow(@PathVariable("followUserAddress") String followUserAddress) {
        userTextFollowService.addOrDeleteFollow(followUserAddress.trim(), getAddress());
    }

    @ApiOperation("我关注的用户地址薄")
    @GetMapping("/selectUserFollow")
    public List<UserFollowListDto> selectUserFollow() {
        return userTextFollowService.selectUserFollow(getAddress());
    }

    @ApiOperation("我的已关注好友")
    @GetMapping("/selectUserFriend")
    public List<UserFollowListDto> selectUserFriend() {
        return userTextFollowService.selectUserFriend(getAddress());
    }


    @ApiOperation("关注我的好友")
    @GetMapping("/selectCoverUserFriend")
    public List<UserFollowListDto> selectCoverUserFriend() {
        return userTextFollowService.selectCoverUserFriend(getAddress());
    }

    @ApiOperation("我的好友")
    @GetMapping("/selectFriend")
    public List<UserFollowListDto> selectFriend() {
        return userTextFollowService.selectFriend(getAddress());
    }

    @ApiOperation("添加好友")
    @PostMapping("/addFriend")
    public void addFriend(@RequestBody AddUserFriendDto addUserFriendDto) {
        addUserFriendDto.setUserAddress(getAddress());
        userTextFollowService.addFriend(addUserFriendDto);
    }

    @ApiOperation("删除好友")
    @PostMapping("/deleteFriend/{followUserAddress}")
    public void deleteFriend(@PathVariable("followUserAddress") String followUserAddress) {
        userTextFollowService.deleteFriend(followUserAddress.trim(), getAddress());
    }

    @ApiOperation("搜索地址 OR 查询详情")
    @GetMapping("/searchAddress")
    public UserFriendDto searchAddress(String address) {
        UserFriendDto userFriendDto = userTextFollowService.searchAddress(address.trim(), getAddress());
        return userFriendDto;
    }

    @ApiOperation("修改备注等信息")
    @PostMapping("/update")
    public void update(@RequestBody UserFollow userFollow) {
        userTextFollowService.update(userFollow);
    }


}
