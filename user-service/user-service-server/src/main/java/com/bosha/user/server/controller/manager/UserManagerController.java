package com.bosha.user.server.controller.manager;

import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.ManagerUserListDto;
import com.bosha.user.api.dto.UserManagerDetailDto;
import com.bosha.user.api.dto.UserManagerListDto;
import com.bosha.user.api.service.UserManagerService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserManagerController
 * @Author liqingping
 * @Date 2019-12-16 16:10
 */
@RestController
@Api(tags = "地址管理")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/manager/user")
public class UserManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @ApiOperation("用户地址列表")
    @GetMapping("/list")
    PageInfo<ManagerUserListDto> list(@ApiParam @ModelAttribute @Validated UserManagerListDto dto) {
        return userManagerService.list(dto);
    }

    @ApiOperation("详情")
    @GetMapping("/{userId}")
    UserManagerDetailDto detail(@ApiParam(value = "用户id",required = true) @PathVariable("userId") Long userId) {
        return userManagerService.detail(userId);
    }
}
