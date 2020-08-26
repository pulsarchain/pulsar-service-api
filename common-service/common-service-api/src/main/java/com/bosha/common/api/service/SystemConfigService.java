package com.bosha.common.api.service;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.entity.InviteFriendConfig;
import com.bosha.common.api.entity.LiveMiningConfig;
import com.bosha.common.api.entity.StarConfig;
import com.bosha.common.api.entity.SystemConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(CommonServiceConstants.SERVER_NAME)//服务名
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/system/config")//内部服务前缀
@Api(tags = " 系统配置server层接口")
public interface SystemConfigService {

    @ApiOperation("获取系统配置")
    @GetMapping
    SystemConfig getSystemConfig(@RequestParam(value = "coinId", required = false) Long coinId);

    @ApiOperation("修改邀请好友配置")
    @PostMapping("/inviteFriend")
    boolean inviteFriend(@RequestBody InviteFriendConfig inviteFriendConfig);

    @ApiOperation("修改星系配置")
    @PostMapping("/star")
    boolean star(@RequestBody StarConfig starConfig);

    @ApiOperation("修改直播挖矿配置")
    @PostMapping("/liveMining")
    boolean liveMining(@RequestBody LiveMiningConfig liveMiningConfig);
}
