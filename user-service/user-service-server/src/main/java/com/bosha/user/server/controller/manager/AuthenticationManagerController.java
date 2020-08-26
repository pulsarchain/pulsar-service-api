package com.bosha.user.server.controller.manager;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.AuthenticationInfoDto;
import com.bosha.user.api.entity.Authentication;
import com.bosha.user.api.service.AuthenticationService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AuthenticationController
 * @Author liqingping
 * @Date 2020-02-09 20:52
 */
@RestController
@Api(tags = "管理后台用户认证")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/manager/authentication")
@Slf4j
public class AuthenticationManagerController extends BaseController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation("认证列表")
    @GetMapping("/list")
    PageInfo<Authentication> list(@ModelAttribute Page page,
                                  @ApiParam(value = "地址") @RequestParam(value = "address", required = false) String address,
                                  @ApiParam(value = "认证类型：3 个人，4 企业，5 政府") @RequestParam(value = "type", required = false) Integer type,
                                  @ApiParam(value = "状态：1 已提交资料，待转账，2，自我认证转账确认中，3 自我认证成功，4 辅助认证进行中，5 已完成认证")
                                  @RequestParam(value = "status", required = false) Integer status) {
        return authenticationService.list(page, address, type, status);
    }

    @ApiOperation("认证信息")
    @GetMapping("/{address}")
    AuthenticationInfoDto info(@PathVariable("address") String address) {
        return authenticationService.info(address);
    }

}
