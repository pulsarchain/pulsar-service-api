package com.bosha.user.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.ImRedDetailDto;
import com.bosha.user.api.dto.ImRedDto;
import com.bosha.user.api.dto.ImRedList;
import com.bosha.user.api.dto.ImRedMyDetailDto;
import com.bosha.user.api.entity.ImRed;
import com.bosha.user.api.service.ImRedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserService
 * @Author liqingping
 * @Date 2018-12-20 10:07
 */

@RestController
@Api(tags = "红包")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/imRed")
public class ImRedController extends BaseController {
    @Autowired
    private ImRedService imRedService;

    @ApiOperation("发送红包")
    @PostMapping("/add")
    public ImRed add(@RequestBody ImRedDto imRedDto) {
        imRedDto.setUserAddress(getAddress());
        return imRedService.add(imRedDto);
    }

    @ApiOperation("加入红包")
    @PostMapping("/join")
    public void join(@RequestParam("id") Long id) {
        imRedService.join(id, getAddress());
    }


    @ApiOperation("红包详情")
    @GetMapping("/findById")
    public ImRedDetailDto findById(@RequestParam("id") Long id) {
        ImRedDetailDto imRedDetailDto = imRedService.findById(id, getAddress());
        return imRedDetailDto;
    }

    @ApiOperation("我发送的红包")
    @GetMapping("/findMySendRed")
    public ImRedMyDetailDto findMySendRed(@RequestParam("year") Integer year) {
        ImRedMyDetailDto mySendRed = imRedService.findMySendRed(getAddress(), year);
        return mySendRed;
    }


    @ApiOperation("我接收的红包")
    @GetMapping("/findMyReceiveRed")
    public ImRedMyDetailDto findMyReceiveRed(@RequestParam("year") Integer year) {
        return imRedService.findMyReceiveRed(getAddress(), year);
    }


    @ApiOperation("我待支付红包")
    @GetMapping("/findMyPaidRed")
    public List<ImRedList> findMyPaidRed() {
        return imRedService.findMyPaidRed(getAddress());
    }


    @ApiOperation("修改红包状态")
    @PostMapping("/updateRed")
    public void updateRed(@RequestParam("id") Long id) {
        imRedService.updateRed(id);
    }


    @ApiOperation("修改红包领取状态")
    @PostMapping("/updateRedReceive")
    public void updateRedReceive(@RequestParam("id") Long id, @RequestParam("hash") String hash) {
        imRedService.updateRedReceive(id, getAddress(), hash);
    }


}
