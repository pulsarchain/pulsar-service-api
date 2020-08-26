package com.bosha.dapp.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.OrgDetailDto;
import com.bosha.dapp.api.dto.OrgListDto;
import com.bosha.dapp.api.dto.OrgQuery;
import com.bosha.dapp.api.entity.SparksOrg;
import com.bosha.dapp.api.service.OrgService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ActivityController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 15:11
 */
@Api(tags = "机构")
@RestController
@Slf4j
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/org")
public class OrgController extends BaseController {

    @Autowired
    private OrgService orgService;

    @ApiOperation("申请机构")
    @PostMapping("/add")
    Long add(@RequestBody @Validated SparksOrg org) {
        org.setAddress(getAddress());
        return orgService.add(org);
    }

    @ApiOperation("机构列表")
    @GetMapping("/list")
    PageInfo<OrgListDto> list(@ApiParam @ModelAttribute OrgQuery query) {
        return orgService.list(query);
    }

    @ApiOperation("关注")
    @PostMapping("/follow")
    boolean follow(@ApiParam(value = "机构的id") @RequestParam("id") Long id) {
        return orgService.follow(getAddress(), id);
    }

    @ApiOperation("取消关注")
    @PostMapping("/unFollow")
    boolean unFollow(@ApiParam(value = "机构的id") @RequestParam("id") Long id) {
        return orgService.unFollow(getAddress(), id);
    }

    @ApiOperation("机构详情")
    @GetMapping("/detail")
    OrgDetailDto detail(Long id) {
        return orgService.detail(id);
    }

    @ApiOperation("我创建的机构")
    @GetMapping("/my")
    PageInfo<OrgListDto> my(Page page) {
        return orgService.my(page);
    }

    @ApiOperation("我关注的机构")
    @GetMapping("/myFollow")
    PageInfo<OrgListDto> myFollow(Page page){
        return orgService.myFollow(page);
    }
}
