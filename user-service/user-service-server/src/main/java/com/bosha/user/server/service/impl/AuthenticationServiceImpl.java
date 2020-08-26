package com.bosha.user.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.commons.utils.StrUtils;
import com.bosha.commons.utils.UUIDUtils;
import com.bosha.user.api.dto.AuthenticationInfoDto;
import com.bosha.user.api.dto.AuxiliaryAuthenticationNotice;
import com.bosha.user.api.entity.Authentication;
import com.bosha.user.api.entity.AuxiliaryAuthentication;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.bosha.user.api.service.AuthenticationService;
import com.bosha.user.api.service.UserService;
import com.bosha.user.server.config.UserServiceConfig;
import com.bosha.user.server.mapper.AuthenticationMapper;
import com.bosha.user.server.mapper.AuxiliaryAuthenticationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AuthenticationServiceImpl
 * @Author liqingping
 * @Date 2020-02-09 18:48
 */
@RestController
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationMapper authenticationMapper;
    @Autowired
    private AuxiliaryAuthenticationMapper auxiliaryAuthenticationMapper;
    @Autowired
    private PushService pushService;
    @Autowired
    private UserServiceConfig userServiceConfig;
    @Autowired
    private UserService userService;

    @Override
    @RedissonDistributedLock(key = "#authentication.address")
    public Long add(@RequestBody Authentication authentication) {
        Authentication db = authenticationMapper.getByAddress(authentication.getAddress());
        if (db != null)
            return db.getId();
        authentication.setCreateTime(new Date());
        authentication.setStatus(Authentication.STATUS_TO_BE_TRANSFER);
        authentication.setIdentitySignature(UUIDUtils.getUUID());
        authenticationMapper.insertSelective(authentication);
        return authentication.getId();
    }

    @Override
    public AuthenticationInfoDto info(@RequestParam("address") String address) {
        Authentication authentication = authenticationMapper.getByAddress(address);
        if (authentication == null)
            return null;
        List<AuxiliaryAuthentication> list = auxiliaryAuthenticationMapper.list(authentication.getId());
        return AuthenticationInfoDto.builder()
                .authentication(authentication).auxiliaries(list).systemAddress(userServiceConfig.getAuthentication().getSystemAddress())
                .build();
    }

    @Override
    public Authentication getByAddress(@PathVariable("address") String address) {
        return authenticationMapper.getByAddress(address);
    }

    @Override
    @RedissonDistributedLock(key = "@address")
    public void auxiliaryAuthentication(@RequestBody List<String> addresses) {
        String address = RequestContextUtils.getAddress();
        Authentication authentication = getByAddress(address);
        if (authentication == null)
            throw new BaseException(UserErrorMessageEnum.PLEASE_COMPLETE_SELF_CERTIFICATION_FIRST);
        if (authentication.getStatus() == Authentication.STATUS_TO_BE_TRANSFER || authentication.getStatus() == Authentication.STATUS_SELF_CONFIRMING)
            throw new BaseException(UserErrorMessageEnum.PLEASE_COMPLETE_SELF_CERTIFICATION_FIRST);
        if (authentication.getStatus() == Authentication.STATUS_SUCCESS)
            throw new BaseException(UserErrorMessageEnum.NO_NEED_RE_AUTHENTICATE);
        AuxiliaryAuthentication aa = new AuxiliaryAuthentication();
        aa.setAddress(address);
        aa.setAuId(authentication.getId());
        aa.setCreateTime(new Date());
        List<String> list = new ArrayList<>();
        addresses.forEach(s -> {
            if (address.equalsIgnoreCase(s))
                return;
            //TODO 判断地址是否 脉冲信用分大于10分
            list.add(s);
            aa.setAuxiliaryAddress(s);
            int count = auxiliaryAuthenticationMapper.countIsAuxiliary(address, s);
            if (count > 0) {
                log.warn("addr={}，辅助地址={}，已存在！！", StrUtils.addr(address), StrUtils.addr(s));
                return;
            }
            auxiliaryAuthenticationMapper.insertSelective(aa);
        });
        User user = userService.getByAddress(address);
        if (user == null)
            return;
        if (CollectionUtils.isNotEmpty(list)) {
            if (authentication.getStatus() == Authentication.STATUS_SELF_SUCCESS) {
                authentication.setStatus(Authentication.STATUS_AUXILIARY_AUTHENTICATION_ING);
                authentication.setUpdateTime(new Date());
                authenticationMapper.updateByPrimaryKey(authentication);
            }
            String nickName = user.getNickName();
            pushService.send(PushMessageDetail.builder()
                    .type(PushMessageTypeEnum.SYSTEM).subType(PushMessageSubTypeEnum.System.AUXILIARY_CERTIFICATION.name())
                    .pushEnum(AliyunPushEnum.NOTICE).addresses(list)
                    .pushTitle("帮Ta完成身份认证")
                    .title((StringUtils.isBlank(nickName) ? StrUtils.addr(address) : nickName) + "邀请您帮Ta完成身份认证")
                    .content((StringUtils.isBlank(nickName) ? StrUtils.addr(address) : nickName) + "邀请您帮Ta完成身份认证")
                    .data(AuxiliaryAuthenticationNotice.builder().address(address).nickName(nickName).build())
                    .build());
        }

    }

    @Override
    public PageInfo<Authentication> list(@ModelAttribute Page page,
                                         @ApiParam(value = "地址") @RequestParam(value = "address", required = false) String address,
                                         @ApiParam(value = "认证类型：3 个人，4 企业，5 政府") @RequestParam(value = "type", required = false) Integer type,
                                         @ApiParam(value = "") @RequestParam(value = "status", required = false) Integer status) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(authenticationMapper.list(address, type, status));
    }

    @Override
    public boolean updateStatus(@RequestParam("address") String address) {
        Authentication authentication = authenticationMapper.getByAddress(address);
        if (authentication == null)
            throw new BaseException("请先提交资料");
        if (authentication.getStatus() == Authentication.STATUS_TO_BE_TRANSFER) {
            authentication.setStatus(Authentication.STATUS_SELF_CONFIRMING);
            return authenticationMapper.updateByPrimaryKeySelective(authentication) > 0;
        }
        throw new BaseException("状态异常");
    }

}
