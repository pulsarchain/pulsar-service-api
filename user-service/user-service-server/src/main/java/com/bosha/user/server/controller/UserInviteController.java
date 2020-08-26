package com.bosha.user.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.UserInviteDto;
import com.bosha.user.api.entity.UserInviteRecord;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserInviteController
 * @Author liqingping
 * @Date 2019-12-16 13:49
 */
@RestController
@Api(tags = "用户邀请")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/invite")
public class UserInviteController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取邀请信息")
    @GetMapping("/info")
    UserInviteDto inviteInfo() {
        return userService.inviteInfo(getUserId());
    }

    @ApiOperation("邀请记录")
    @GetMapping("/records")
    PageInfo<UserInviteRecord> inviteInfo(@ModelAttribute @Validated Page page) {
        return userService.inviteRecord(page, getUserId());
    }

}
