package com.bosha.common.api.service;


import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.oss.OssCallbackResponse;
import com.bosha.common.api.dto.oss.OssSignResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(CommonServiceConstants.SERVER_NAME)//服务名
@RequestMapping(CommonServiceConstants.SERVER_PRIFEX + "/file")//内部服务前缀
@Api(description = "FileService")
public interface FileResourceService {

    @ApiOperation("查询上传文件签名")
    @GetMapping("/findOssSign")
    OssSignResponse findOssSign();

    @ApiOperation("上传文件回调验证")
    @PostMapping("/verifyOssCallback")
    boolean verifyOssCallback(@RequestBody OssCallbackResponse ossCallbackDto);

}