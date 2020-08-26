package com.bosha.user.server.controller;

import com.bosha.commons.controller.BaseController;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.AddressValidator;
import com.bosha.commons.utils.BeanUtils;
import com.bosha.commons.utils.GoogleGenerator;
import com.bosha.user.api.constants.UserServiceConstants;
import com.bosha.user.api.dto.BindGoogleSecretDto;
import com.bosha.user.api.dto.UserBindDto;
import com.bosha.user.api.dto.UserBindResultDto;
import com.bosha.user.api.dto.UserGoogleSecretDto;
import com.bosha.user.api.dto.UserInfoDetailDto;
import com.bosha.user.api.dto.UserInfoDto;
import com.bosha.user.api.entity.Authentication;
import com.bosha.user.api.entity.CreditScore;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.bosha.user.api.service.AuthenticationService;
import com.bosha.user.api.service.CreditScoreService;
import com.bosha.user.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: UserController
 * @Author liqingping
 * @Date 2019-12-16 13:49
 */
@RestController
@Api(tags = "用户账户")
@RequestMapping(UserServiceConstants.WEB_PRIFEX + "/account")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private CreditScoreService creditScoreService;
    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation("绑定GoogleSecret")
    @PostMapping("/googleSecret/bind")
    boolean bindGoogleSecret(@RequestBody @Validated BindGoogleSecretDto google) {
        return userService.bindGoogleSecret(google);
    }

    @ApiOperation("根据地址绑定用户")
    @PostMapping("/bind")
    UserBindResultDto bind(@RequestBody @Validated UserBindDto bind) {
        if (!AddressValidator.isValid(bind.getAddress()))
            throw new BaseException(UserErrorMessageEnum.ADDRESS_ERROR);
        return userService.bind(bind);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    boolean update(@RequestBody UserInfoDto info) {
        if (new UserInfoDto().equals(info))
            return false;
        return userService.update(info);
    }

    @ApiOperation("校验Google验证码")
    @GetMapping("/googleSecret/verify")
    boolean verifyGoogleSecret(@ApiParam(value = "验证码", required = true) @RequestParam("code") String code) {
        return userService.verifyGoogleSecret(getUserId(), code);
    }

    @ApiOperation("生成Google验证码")
    @GetMapping("/googleSecret/generate")
    String generateGoogleSecret() {
        return GoogleGenerator.generateSecretKey();
    }

    @ApiOperation("是否已设置Google验证码")
    @GetMapping("/googleSecret/check")
    UserGoogleSecretDto checkGoogleSecret() {
        User user = userService.check(getUserId());
        return UserGoogleSecretDto.builder()
                .enableGoogleSecret(user.getEnableGoogleSecret())
                .isSetGoogleSecret(StringUtils.isNotBlank(user.getGoogleSecret()))
                .build();
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    UserInfoDetailDto info() {
        User user = userService.check(getAddress());
        CreditScore creditScore = creditScoreService.getByAddress(getAddress());

        UserInfoDetailDto infoDetailDto = BeanUtils.copyProperties(user, UserInfoDetailDto.class);
        infoDetailDto.setCreditScore(UserInfoDetailDto.CreditScoreDto.builder()
                .creditScore(creditScore.getScore()).updateTime(creditScore.getUpdateTime())
                .build());
        UserInfoDetailDto.AuthenticationInfoDto authenticationInfoDto = UserInfoDetailDto.AuthenticationInfoDto.builder().build();
        if (user.getType() != null && user.getType() >= 3) {
            authenticationInfoDto.setType(user.getType());
            authenticationInfoDto.setStatus(Authentication.STATUS_SUCCESS);
        } else {
            Authentication authentication = authenticationService.getByAddress(getAddress());
            if (authentication != null) {
                authenticationInfoDto.setType(authentication.getType());
                authenticationInfoDto.setStatus(authentication.getStatus());
            }
        }
        infoDetailDto.setAuthenticationInfo(authenticationInfoDto);
        return infoDetailDto;
    }

}
