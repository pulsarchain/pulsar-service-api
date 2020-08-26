package com.bosha.common.server.controller.manager;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.entity.InviteFriendConfig;
import com.bosha.common.api.entity.LiveMiningConfig;
import com.bosha.common.api.entity.StarConfig;
import com.bosha.common.api.entity.SystemConfig;
import com.bosha.common.api.service.SystemConfigService;
import com.bosha.commons.constants.CommonConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SystemConfigManagerController
 * @Author liqingping
 * @Date 2019-12-13 13:24
 */
@RestController
@Slf4j
@Api(tags = "系统配置")
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + CommonConstants.Path.MANAGER + "/system/config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @ApiOperation("获取系统配置")
    @GetMapping
    SystemConfig systemConfig() {
        return systemConfigService.getSystemConfig(null);
    }

    @ApiOperation("修改邀请好友配置")
    @PostMapping("/inviteFriend")
    boolean inviteFriend(@RequestBody InviteFriendConfig inviteFriendConfig) {
        return systemConfigService.inviteFriend(inviteFriendConfig);
    }

    @ApiOperation("修改星系配置")
    @PostMapping("/star")
    boolean star(@RequestBody StarConfig starConfig) {
        return systemConfigService.star(starConfig);
    }

    @ApiOperation("修改直播挖矿配置")
    @PostMapping("/liveMining")
    boolean liveMining(@RequestBody LiveMiningConfig liveMiningConfig) {
        return systemConfigService.liveMining(liveMiningConfig);
    }
}
