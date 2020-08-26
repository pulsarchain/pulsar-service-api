package com.bosha.user.api.service;

import java.util.List;
import java.util.Map;


import com.bosha.commons.dto.Page;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.BindGoogleSecretDto;
import com.bosha.user.api.dto.UserBindDto;
import com.bosha.user.api.dto.UserBindResultDto;
import com.bosha.user.api.dto.UserInfoDto;
import com.bosha.user.api.dto.UserInviteDto;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.entity.UserInviteRecord;
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

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserService
 * @Author liqingping
 * @Date 2018-12-20 10:07
 */

@FeignClient(UserServiceConstants.SERVER_NAME)//服务名
@RequestMapping(UserServiceConstants.SERVER_PRIFEX)//内部服务前缀
@Api(tags = " 用户服务server层接口")
public interface UserService {

    @ApiOperation("根据id获取用户")
    @GetMapping
    User getById(@RequestParam("id") Long userId);

    @ApiOperation("根据id获取用户")
    @GetMapping("/check")
    User check(@RequestParam("id") Long userId);

    @ApiOperation("根据id获取用户")
    @GetMapping("/checkByAddress")
    User check(@RequestParam("address") String address);

    @ApiOperation("根据地址获取用户")
    @GetMapping("/{address}")
    User getByAddress(@PathVariable("address") String address);

    @ApiOperation("绑定GoogleSecret")
    @PostMapping("/bindGoogleSecret")
    boolean bindGoogleSecret(@RequestBody @Validated BindGoogleSecretDto google);

    @ApiOperation("根据地址绑定用户")
    @PostMapping("/bind")
    UserBindResultDto bind(@RequestBody @Validated UserBindDto bind);

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    boolean update(@RequestBody UserInfoDto info);

    @ApiOperation("获取邀请信息")
    @GetMapping("/invite")
    UserInviteDto inviteInfo(@RequestParam("userId") Long userId);

    @ApiOperation("校验Google验证码")
    @GetMapping("/verifyGoogleSecret")
    boolean verifyGoogleSecret(@RequestParam("userId") Long userId, @RequestParam("code") String code);

    @ApiOperation("用户邀请记录")
    @GetMapping("/invite/record")
    PageInfo<UserInviteRecord> inviteRecord(@ModelAttribute Page page, @RequestParam("userId") Long userId);

    @ApiOperation("更新用户转币记录")
    @PostMapping("/invite/record/update")
    boolean updateInviteRecord(@RequestBody UserInviteRecord userInviteRecord);

    @ApiOperation("根据用户地址获取用户id")
    @PostMapping("/addressToIds")
    Map<String, Long> addressToIds(@RequestBody List<String> address);

    @ApiOperation("根据用户id获取用户address")
    @PostMapping("/idToAddresses")
    Map<Long, String> idToAddresses(@RequestBody List<Long> uids);

    @ApiOperation("获取所有用户的数量")
    @GetMapping("/count")
    int count();

    @ApiOperation("随机获取用户的地址")
    @GetMapping("/randomAddresses")
    List<String> randomAddresses(@RequestParam(value = "num", required = false) Integer num);

    @ApiOperation("用户地址分页")
    @PostMapping("/addresses")
    PageInfo<String> accounts(@RequestBody Page page);

    @ApiOperation("统计用户的邀请新用户数量")
    @GetMapping("/inviteCount")
    int inviteCount(@RequestParam("address") String address);
}
