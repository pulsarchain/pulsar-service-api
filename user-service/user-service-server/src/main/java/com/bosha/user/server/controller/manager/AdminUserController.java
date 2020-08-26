package com.bosha.user.server.controller.manager;

import java.util.List;


import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.AdminUserListDto;
import com.bosha.user.api.dto.AdminUserListRequestDto;
import com.bosha.user.api.dto.AdminUserLoginDto;
import com.bosha.user.api.dto.AdminUserLoginResultDto;
import com.bosha.user.api.entity.AdminUser;
import com.bosha.user.api.entity.Permission;
import com.bosha.user.api.entity.Post;
import com.bosha.user.api.service.AdminUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: Test
 * @Author liqingping
 * @Date 2019-11-27 13:30
 */
@RestController
@Api(tags = "后台管理")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/manager/account")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @ApiOperation("获取所有权限列表")
    @GetMapping(value = "/permissions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "", response = Permission.class)
    List<Permission> listAllPermission() {
        return adminUserService.listAllPermission();
    }

    @ApiOperation("新增岗位")
    @PostMapping("/position/add")
    boolean addPosition(@RequestBody @Validated Post position) {
        return adminUserService.addPosition(position);
    }

    @ApiOperation("修改岗位")
    @PostMapping("/position/update")
    boolean updatePosition(@RequestBody Post position) {
        return adminUserService.updatePosition(position);
    }

    @ApiOperation("岗位列表")
    @GetMapping("/positions")
    List<Post> listAllPosition() {
        return adminUserService.listAllPosition();
    }

    @ApiOperation("添加账号")
    @PostMapping("/add")
    boolean addAccount(@RequestBody @Validated AdminUser adminUser) {
        return adminUserService.addAccount(adminUser);
    }

    @ApiOperation("编辑账号")
    @PostMapping("/update")
    boolean updateAccount(@RequestBody AdminUser adminUser) {
        return adminUserService.updateAccount(adminUser);
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    boolean updatePassword(@ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
                           @ApiParam(value = "新密码", required = true) @RequestParam String newPassword) {
        return adminUserService.updatePassword(oldPassword, newPassword);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    AdminUserLoginResultDto login(@RequestBody @Validated AdminUserLoginDto loginDto) {
        return adminUserService.login(loginDto);
    }

    @ApiOperation("后台用户列表")
    @PostMapping("/list")
    PageInfo<AdminUserListDto> listUser(@RequestBody AdminUserListRequestDto requestDto) {
        return adminUserService.listUser(requestDto);
    }
}
