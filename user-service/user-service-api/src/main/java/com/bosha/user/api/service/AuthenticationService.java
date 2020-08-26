package com.bosha.user.api.service;


import java.util.List;


import com.bosha.commons.dto.Page;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.AuthenticationInfoDto;
import com.bosha.user.api.entity.Authentication;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(UserServiceConstants.SERVER_NAME)//服务名
@RequestMapping(UserServiceConstants.SERVER_PRIFEX + "/authentication")//内部服务前缀
@Api(tags = " 认证服务server层接口")
public interface AuthenticationService {

    @ApiOperation("添加认证信息")
    @PostMapping("/")
    Long add(@RequestBody Authentication authentication);

    @ApiOperation("认证信息")
    @GetMapping("/info")
    AuthenticationInfoDto info(@RequestParam("address") String address);

    @ApiOperation("根据地址获取用户认证信息")
    @GetMapping("/{address}")
    Authentication getByAddress(@PathVariable("address") String address);

    @ApiOperation("辅助认证")
    @PostMapping("/auxiliaryAuthentication")
    void auxiliaryAuthentication(@RequestBody List<String> addresses);

    @ApiOperation("认证列表")
    @GetMapping("/list")
    PageInfo<Authentication> list(@ModelAttribute Page page,
                                  @ApiParam(value = "地址") @RequestParam(value = "address", required = false) String address,
                                  @ApiParam(value = "认证类型：3 个人，4 企业，5 政府") @RequestParam(value = "type", required = false) Integer type,
                                  @ApiParam(value = "") @RequestParam(value = "status", required = false) Integer status);

    @ApiOperation("修改自我认证转账中状态")
    @PostMapping("/updateStatusSelfConfirming")
    boolean updateStatus(@RequestParam("address") String address);

}
