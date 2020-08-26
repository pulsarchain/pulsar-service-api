package com.bosha.user.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.ImGroupDto;
import com.bosha.user.api.dto.ImGroupListDto;
import com.bosha.user.api.dto.ImGroupMemberDto;
import com.bosha.user.api.dto.ImGroupWebDto;
import com.bosha.user.api.entity.ImGroup;
import com.bosha.user.api.service.ImGroupService;
import io.swagger.annotations.Api;
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
@Api(tags = "群组管理")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/imGroup")
public class ImGroupController extends BaseController {
    @Autowired
    private ImGroupService imGroupService;

    @PostMapping("/create")
    @ApiOperation("创建群主")
    public ImGroup create(@RequestBody ImGroupDto imGroupDto) {
        imGroupDto.setAddress(getAddress());
        return imGroupService.create(imGroupDto);

    }

    @PostMapping("/addMember")
    @ApiOperation("添加成员")
    public void addMember(@RequestBody ImGroupMemberDto imGroupMemberDto) {
        imGroupMemberDto.setAddress(getAddress());
        imGroupService.addMember(imGroupMemberDto);
    }


    @PostMapping("/removeMember")
    @ApiOperation("移除成员")
    public void removeMember(@RequestBody ImGroupMemberDto imGroupMemberDto) {
        imGroupMemberDto.setAddress(getAddress());
        imGroupService.removeMember(imGroupMemberDto);
    }

    @PostMapping("/retreatMember")
    @ApiOperation("主动退出群")
    public void retreatGroup(Long groupId) {
        imGroupService.retreatGroup(groupId, getAddress());
    }

    @PostMapping("/update")
    @ApiOperation("修改群信息")
    public void update(@RequestBody ImGroup imGroup) {
        imGroupService.update(imGroup);
    }


    @GetMapping("/findByGroupId")
    @ApiOperation("查询群详情信息")
    public ImGroupWebDto findByGroupId(Long id) {
        return imGroupService.findByGroupId(id, getAddress());
    }

    @GetMapping("/listGroups")
    @ApiOperation("获取当前用户所有的群")
    public List<ImGroupListDto> listGroups() {
        List<ImGroupListDto> imGroupListDtos = imGroupService.listGroups(getAddress());
        return imGroupListDtos;
    }

}
