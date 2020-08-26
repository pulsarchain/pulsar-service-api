package com.bosha.user.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.*;
import com.bosha.user.api.entity.ImRed;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.entity.UserInviteRecord;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: AdminUserService
 * @Author liqingping
 * @Date 2018-12-20 10:07
 */

@FeignClient(UserServiceConstants.SERVER_NAME)//服务名
@RequestMapping(UserServiceConstants.SERVER_PRIFEX)//内部服务前缀
@Api(tags = " 红包服务server层接口")
public interface ImRedService {

    @ApiOperation("发送红包")
    @PostMapping("/add")
    ImRed add(@RequestBody ImRedDto imRedDto);

    @ApiOperation("加入红包")
    @PostMapping("/join")
    void join(@RequestParam("id") Long id,@RequestParam("address")String address);


    @ApiOperation("红包详情")
    @GetMapping("/findById")
    ImRedDetailDto findById(@RequestParam("id") Long id,@RequestParam("userAddress")String userAddress);

    @ApiOperation("我发送的红包")
    @GetMapping("/findMySendRed")
    ImRedMyDetailDto findMySendRed(@RequestParam("userAddress")String userAddress,@RequestParam("year")Integer year);


    @ApiOperation("我接收的红包")
    @GetMapping("/findMyReceiveRed")
    ImRedMyDetailDto findMyReceiveRed(@RequestParam("userAddress")String userAddress,@RequestParam("year")Integer date);


    @ApiOperation("我待支付红包")
    @GetMapping("/findMyPaidRed")
    List<ImRedList> findMyPaidRed(@RequestParam("userAddress")String userAddress);


    @ApiOperation("修改红包状态")
    @PostMapping("/updateRed")
    void updateRed(@RequestParam("id") Long id);



    @ApiOperation("修改红包领取状态")
    @PostMapping("/updateRedReceive")
    void updateRedReceive(@RequestParam("redId") Long redId,@RequestParam("address") String address,@RequestParam("hash")String hash);




}
