package com.bosha.user.api.service;


import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.ManagerUserListDto;
import com.bosha.user.api.dto.UserManagerDetailDto;
import com.bosha.user.api.dto.UserManagerListDto;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(UserServiceConstants.SERVER_NAME)//服务名
@RequestMapping(UserServiceConstants.SERVER_PRIFEX + "/manager/account")//内部服务前缀
@Api(tags = " 用户管理server层接口")
public interface UserManagerService {

    @ApiOperation("用户地址列表")
    @GetMapping("/list")
    PageInfo<ManagerUserListDto> list(@ModelAttribute UserManagerListDto dto);

    @ApiOperation("详情")
    @GetMapping("/{userId}")
    UserManagerDetailDto detail(@PathVariable("userId") Long userId);
}
