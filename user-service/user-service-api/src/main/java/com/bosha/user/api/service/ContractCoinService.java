package com.bosha.user.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.*;
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
@Api(tags = "合约币种server层接口")
public interface ContractCoinService {
    @ApiOperation("添加合约币信息")
    @PostMapping("/save")
    void save(@RequestBody ContractCoinDto contractCoinDto);

    @ApiOperation("查询合约币列表")
    @GetMapping("/list")
    PageInfo<ContractCoinListDto> list(@RequestBody Page page);

    @ApiOperation("绑定合约地址")
    @PostMapping("/bindContract")
    void bindContract(@RequestParam("hash") String hash,@RequestParam("contractAddress")String contractAddress);




}
