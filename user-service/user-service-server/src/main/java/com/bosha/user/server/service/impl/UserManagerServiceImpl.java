package com.bosha.user.server.service.impl;

import com.bosha.user.api.dto.ManagerUserListDto;
import com.bosha.user.api.dto.UserManagerDetailDto;
import com.bosha.user.api.dto.UserManagerListDto;
import com.bosha.user.api.service.UserManagerService;
import com.bosha.user.api.service.UserService;
import com.bosha.user.server.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserManagerServiceImpl
 * @Author liqingping
 * @Date 2019-12-16 16:04
 */
@RestController
@Slf4j
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<ManagerUserListDto> list(@ModelAttribute UserManagerListDto dto) {
        PageHelper.startPage(dto.getPage(), dto.getSize());
        return PageInfo.of(userMapper.list(dto));
    }

    @Override
    public UserManagerDetailDto detail(@PathVariable("userId") Long userId) {
        return UserManagerDetailDto.builder().basic(userMapper.basic(userId)).build();
    }
}
