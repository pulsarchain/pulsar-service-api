package com.bosha.user.api.service;

import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.QueryCreditScoreRangeDto;
import com.bosha.user.api.entity.AuthorizedCreditScore;
import com.bosha.user.api.entity.CreditScore;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION:
 * @Author liqingping
 * @Date 2018-12-20 10:07
 */

@FeignClient(UserServiceConstants.SERVER_NAME)//服务名
@RequestMapping(UserServiceConstants.SERVER_PRIFEX + "/creditScore")//内部服务前缀
@Api(tags = " 信用分服务server层接口")
public interface CreditScoreService {

    @ApiOperation("根据用户地址获取用户信用分")
    @GetMapping("/{address}")
    CreditScore getByAddress(@ApiParam(value = "用户地址") @PathVariable("address") String address);

    @ApiOperation("新增用户信用分数据")
    @PostMapping("/add")
    Long add(@RequestParam("address") String address);

    @ApiOperation("增加授权信用分的记录")
    @PostMapping("/authorized/add")
    Long addAuthorizedCreditScore(@RequestBody AuthorizedCreditScore authorizedCreditScore);

    @ApiOperation("授权查看用户信用分")
    @GetMapping("/authorized/view/{address}")
    CreditScore view(@ApiParam(value = "用户地址") @PathVariable("address") String address, @RequestParam("userAddress") String userAddress);

    @ApiOperation("根据信用分范围获取地址列表")
    @PostMapping("/listByScore")
    PageInfo<String> listByScore(@RequestBody QueryCreditScoreRangeDto query);

    @ApiOperation("根据信用分获取数量")
    @GetMapping("/countByRange")
    int countByRange(@RequestParam("min") int min, @RequestParam("max") int max);
}
