package com.bosha.user.api.service;

import java.util.List;


import com.bosha.user.api.dto.AdminUserListDto;
import com.bosha.user.api.dto.AdminUserListRequestDto;
import com.bosha.user.api.dto.AdminUserLoginDto;
import com.bosha.user.api.dto.AdminUserLoginResultDto;
import com.bosha.user.api.entity.AdminUser;
import com.bosha.user.api.entity.Permission;
import com.bosha.user.api.entity.Post;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;

public interface AdminUserService {

    @ApiOperation("获取所有权限列表")
    List<Permission> listAllPermission();

    @ApiOperation("新增岗位")
    boolean addPosition(Post position);

    @ApiOperation("修改岗位")
    boolean updatePosition(Post position);

    @ApiOperation("岗位列表")
    List<Post> listAllPosition();

    @ApiOperation("添加账号")
    boolean addAccount(AdminUser adminUser);

    @ApiOperation("编辑账号")
    boolean updateAccount(AdminUser adminUser);

    @ApiOperation("修改密码")
    boolean updatePassword(String rawPassword, String newPassword);

    @ApiOperation("登录")
    AdminUserLoginResultDto login(AdminUserLoginDto loginDto);

    @ApiOperation("后台用户列表")
    PageInfo<AdminUserListDto> listUser(AdminUserListRequestDto requestDto);

    @ApiOperation("获取后台用户信息")
    AdminUser getById(Long userId);

}
