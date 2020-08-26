package com.bosha.user.server.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import com.bosha.commons.controller.BaseController;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.AddressValidator;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.AuthenticationInfoDto;
import com.bosha.user.api.entity.Authentication;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.bosha.user.api.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AuthenticationController
 * @Author liqingping
 * @Date 2020-02-09 20:52
 */
@RestController
@Api(tags = "用户认证")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/authentication")
@Slf4j
public class AuthenticationController extends BaseController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation("添加认证信息")
    @PostMapping({"/", ""})
    Long add(@RequestBody @Validated Authentication authentication) {
        authentication.setAddress(getAddress());
        return authenticationService.add(authentication);//TODO 余额大于1个PUL才能认证
    }

    @ApiOperation("认证信息")
    @GetMapping("/info")
    public AuthenticationInfoDto info() {
        AuthenticationInfoDto info = authenticationService.info(getAddress());
        if (info == null)
            throw new BaseException(UserErrorMessageEnum.AUTHENTICATION_NOT_FOUND);
        return info;
    }

    @ApiOperation("辅助认证")
    @PostMapping("/auxiliaryAuthentication")
    void auxiliaryAuthentication(@RequestBody @Validated AddressesDto addressesDto) {
        ArrayList<String> list = new ArrayList<>(new HashSet<>(addressesDto.getAddresses()));
        if (list.size() == 0)
            throw new BaseException(UserErrorMessageEnum.ADDRESS_ERROR);
        for (String s : list) {
            if (!AddressValidator.isValid(s))
                throw new BaseException(UserErrorMessageEnum.ADDRESS_ERROR);
        }
        authenticationService.auxiliaryAuthentication(list);
    }

    @ApiOperation("修改自我认证转账中状态")
    @PostMapping("/selfConfirming")
    boolean updateStatus() {
        return authenticationService.updateStatus(getAddress());
    }

    @Data
    public static class AddressesDto {
        @ApiModelProperty("地址列表")
        @Size(min = 1, max = 5, message = "范围1-5")
        @NotEmpty(message = "列表不可为空")
        private List<String> addresses;
    }
}
