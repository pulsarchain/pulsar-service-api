package com.bosha.common.api.service;

import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.entity.PushConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(CommonServiceConstants.SERVER_NAME)
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/pushConfig")
public interface PushConfigService {

    @ApiOperation("新增用户推送配置")
    @PostMapping("/insert")
    boolean insert(@RequestParam("address") String address);

    @ApiOperation("更改用户推送配置")
    @PostMapping("/update")
    boolean update(@RequestBody PushConfig pushConfig);

    @ApiOperation("根据用户地址获取推送配置")
    @GetMapping("/{address}")
    PushConfig getByAddress(@PathVariable("address") String address);


}
