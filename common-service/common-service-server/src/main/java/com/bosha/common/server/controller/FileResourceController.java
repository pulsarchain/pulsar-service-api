package com.bosha.common.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;
import com.bosha.common.api.constants.CommonServiceConstants;
import com.bosha.common.api.dto.oss.OssCallbackResponse;
import com.bosha.common.api.dto.oss.OssSignResponse;
import com.bosha.common.api.service.FileResourceService;
import com.bosha.commons.annotation.NoWrapper;
import com.bosha.commons.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (FileResource)表控制层
 *
 * @author makejava
 * @since 2018-12-11 14:12:44
 */
@Api(tags = "文件管理")
@RestController
@Slf4j
@RequestMapping(CommonServiceConstants.WEB_PRIFEX + "/file")
public class FileResourceController extends BaseController {

    @Autowired
    private FileResourceService fileResourceService;

    @GetMapping("/findOssSign")
    @ApiOperation("获取上传文件签名")
    public OssSignResponse findOssSign() {
        OssSignResponse ossSign = fileResourceService.findOssSign();
        return ossSign;
    }

    @PostMapping("/verifyOssCallback")
    @ApiOperation("回调验证")
    @NoWrapper
    public void verifyOssCallback(HttpServletRequest request, HttpEntity<String> httpEntity, HttpServletResponse response) throws IOException {
        log.info("回调验证进入verifyOSSCallback");
        String ossCallbackBody = httpEntity.getBody();
        String authorization = request.getHeader("authorization");
        String autorizationInput = new String(authorization);
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        OssCallbackResponse ossCallbackDto = new OssCallbackResponse(autorizationInput, pubKeyInput, uri, queryString, ossCallbackBody);
        boolean flag = fileResourceService.verifyOssCallback(ossCallbackDto);
        response.addHeader("Content-Type", "application/json");
        JSONObject js = new JSONObject();
        if (flag) {
            js.put("Status","OK");
            response(request, response, js.toJSONString(), HttpServletResponse.SC_OK);
        } else {
            js.put("Status","verdify not ok");
            response(request, response, js.toJSONString(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status) throws IOException {
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(status);
        response.flushBuffer();
    }

}