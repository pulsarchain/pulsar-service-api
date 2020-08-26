package com.bosha.user.api.service;

import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.ImGroupDto;
import com.bosha.user.api.dto.ImGroupListDto;
import com.bosha.user.api.dto.ImGroupMemberDto;
import com.bosha.user.api.dto.ImGroupWebDto;
import com.bosha.user.api.entity.ImGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(UserServiceConstants.SERVER_NAME)//服务名
@RequestMapping(UserServiceConstants.SERVER_PRIFEX + "/imGroup")//内部服务前缀
@Api(tags = "群组功能")
public interface ImGroupService {
    @PostMapping("/create")
    @ApiOperation("创建群主")
    ImGroup create(@RequestBody ImGroupDto imGroupDto);

    @PostMapping("/addMember")
    @ApiOperation("添加成员")
    void addMember(@RequestBody ImGroupMemberDto imGroupMemberDto);

    @PostMapping("/removeMember")
    @ApiOperation("移除成员")
    void removeMember(@RequestBody ImGroupMemberDto imGroupMemberDto);


    @PostMapping("/update")
    @ApiOperation("修改群信息")
    void update(@RequestBody ImGroup imGroup);


    @GetMapping("/findByGroupId")
    @ApiOperation("查询群信息")
    ImGroupWebDto findByGroupId(@RequestParam("id") Long id, @RequestParam("address") String address);

    @GetMapping("/list")
    @ApiOperation("查询群信息")
    List<ImGroupListDto> listGroups(String address);


    @PostMapping("/retreatGroup")
    @ApiOperation("退出群")
    void retreatGroup(@RequestParam("groupId") Long groupId,@RequestParam("address") String address);
}
