package com.bosha.user.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.enums.UserTypeEnum;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.JWTUtil;
import com.bosha.commons.utils.PhoneUtils;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.commons.utils.StrUtils;
import com.bosha.user.api.dto.AdminUserListDto;
import com.bosha.user.api.dto.AdminUserListRequestDto;
import com.bosha.user.api.dto.AdminUserLoginDto;
import com.bosha.user.api.dto.AdminUserLoginResultDto;
import com.bosha.user.api.entity.AdminUser;
import com.bosha.user.api.entity.Permission;
import com.bosha.user.api.entity.Post;
import com.bosha.user.api.service.AdminUserService;
import com.bosha.user.server.config.UserServiceConfig;
import com.bosha.user.server.mapper.AdminUserMapper;
import com.bosha.user.server.mapper.PermissionMapper;
import com.bosha.user.server.mapper.PostMapper;
import com.bosha.user.server.redis.CacheKey;
import com.bosha.user.server.utils.PasswordUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AdminUserServiceImpl
 * @Author liqingping
 * @Date 2019-04-12 15:14
 */
@Slf4j
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private PostMapper positionMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserServiceConfig userServiceConfig;

    private static final List<String> USER_NAME = Arrays.asList("admin", "administrator", "root");

    @Override
    public List<Permission> listAllPermission() {
        return permissionMapper.listAll(new HashSet<>());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addPosition(Post position) {
        position.setName(StrUtils.removeBlank(position.getName()));
        if (StringUtils.isBlank(position.getName()))
            throw new BaseException("岗位名称不可为空");
        if (positionMapper.countByName(position.getName()) > 0)
            throw new BaseException("该岗位名称已存在");
        position.setCreateTime(new Date());
        position.setStatus(1);
        position.setId(null);
        if (StringUtils.isNotBlank(position.getPermissionTags()))
            position.setPermissionTags(String.join(",", new HashSet<>(Arrays.asList(position.getPermissionTags().split(",")))));
        return positionMapper.insertSelective(position) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePosition(Post position) {
        position.setName(StrUtils.removeBlank(position.getName()));
        Post select = positionMapper.selectByPrimaryKey(position.getId());
        Post byName = positionMapper.getByName(position.getName());
        if (byName != null && !byName.getId().equals(position.getId()))
            throw new BaseException("该岗位不存在");
        if (select == null)
            throw new BaseException("不存在此岗位 id=" + position.getId());
        position.setCreateTime(select.getCreateTime());
        if (select.equals(position))
            return true;
        if (position.getStatus() != null && position.getStatus() == 0) {
            List<AdminUser> adminUserList = adminUserMapper.listByPositionId(position.getId());
            adminUserMapper.updateByPositionId(position.getId());
            log.info("岗位 {} 被 {} 禁用，该岗位下所有用户将被禁用", select.getName(), RequestContextUtils.getRequestInfo().getAdminUserName());
            if (CollectionUtils.isNotEmpty(adminUserList)) {
                RMap<Long, String> map = redis.getMap(CacheKey.Admin.STATUS_DISABLED_MAP.key);
                adminUserList.forEach(adminUser -> map.put(adminUser.getId(), "您的账号已被禁用"));
            }
        }
        if (StringUtils.isNotBlank(position.getPermissionTags()))
            position.setPermissionTags(String.join(",", new HashSet<>(Arrays.asList(position.getPermissionTags().split(",")))));

        return positionMapper.updateByPrimaryKeySelective(position) > 0;
    }

    @Override
    public List<Post> listAllPosition() {
        List<Post> positions = positionMapper.listAll();
        for (Post position : positions) {
            if (StringUtils.isBlank(position.getPermissionTags()))
                position.setPermissionList(new ArrayList<>());
            else
                position.setPermissionList(permissionMapper.listAll(new HashSet<>(Arrays.asList(position.getPermissionTags().split(",")))));
        }
        return positions;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(AdminUser adminUser) {
        adminUser.setAccountName(StrUtils.removeBlank(adminUser.getAccountName()));
        USER_NAME.forEach(s -> {
            if (s.equalsIgnoreCase(adminUser.getAccountName()))
                throw new BaseException("该用户名为系统保留账号，无法创建");
        });
        AdminUser db = adminUserMapper.getByAccountName(adminUser.getAccountName());
        if (db != null)
            throw new BaseException("该用户名已存在");
        PasswordUtils.check(adminUser.getPassword());
        boolean verifyPhone = PhoneUtils.verifyPhone(adminUser.getMobile());
        if (!verifyPhone)
            throw new BaseException("手机号格式错误");
        Date date = new Date();
        adminUser.setCreateTime(date);
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        adminUser.setStatus(1);
        adminUser.setUpdateTime(date);
        positonStatusCheck(adminUser.getPositionId());
        return adminUserMapper.insertSelective(adminUser) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAccount(AdminUser adminUser) {
        if (StringUtils.isNotBlank(adminUser.getMobile()) && !PhoneUtils.verifyPhone(adminUser.getMobile()))
            throw new BaseException("手机号格式错误");
        if (StringUtils.isNotBlank(adminUser.getAccountName())) {
           // throw new BaseException("用户名无法修改");
            if (adminUser.getAccountName().length() >= 100)
                throw new BaseException("用户名最多100个字");
            AdminUser db = adminUserMapper.getByAccountName(adminUser.getAccountName());
            if (db != null && !db.getId().equals(adminUser.getId()))
                throw new BaseException("该用户名已存在");
            USER_NAME.forEach(s -> {
                if (s.equalsIgnoreCase(adminUser.getAccountName()))
                    throw new BaseException("该用户名为系统保留账号，无法创建");
            });
        }
        if (StringUtils.isNotBlank(adminUser.getRealName()) && adminUser.getRealName().length() >= 100)
            throw new BaseException("姓名最多100个字");
        AdminUser dbUser = adminUserMapper.selectByPrimaryKey(adminUser.getId());
        if (dbUser == null)
            throw new BaseException("该用户不存在");
        if (StringUtils.isNotBlank(adminUser.getPassword())) {
            PasswordUtils.check(adminUser.getPassword());
            if (!passwordEncoder.matches(adminUser.getPassword(), dbUser.getPassword())) {//重置密码
                log.info("用户-后台管理：用户{} 密码被 {} 重置为{}", dbUser.getAccountName(), RequestContextUtils.getRequestInfo().getAdminUserName(), adminUser.getPassword());
                //加入标记
                RMap<Long, String> map = redis.getMap(CacheKey.Admin.RESET_PASSWORD_MAP.key);
                map.put(adminUser.getId(), "您的密码已被重置，请重新登录");
            }
            adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        }
        if (adminUser.getStatus() != null && adminUser.getStatus() == 0 && !Objects.equals(dbUser.getStatus(), adminUser.getStatus())) {
            if (RequestContextUtils.getUserId().equals(dbUser.getId()))
                throw new BaseException("无法禁用自己的账号");
            log.info("用户-后台管理：用户{} 账号被 {} 禁用", dbUser.getAccountName(), RequestContextUtils.getRequestInfo().getAdminUserName());
            RMap<Long, String> map = redis.getMap(CacheKey.Admin.STATUS_DISABLED_MAP.key);
            map.put(adminUser.getId(), "您的账号已被禁用");
        }
        if (adminUser.getStatus() != null && adminUser.getStatus() == 1) {
            RMap<Long, String> map = redis.getMap(CacheKey.Admin.STATUS_DISABLED_MAP.key);
            map.remove(adminUser.getId());
        }
        if (adminUser.getPositionId() != null)
            positonStatusCheck(adminUser.getPositionId());
        adminUser.setCreateTime(null);
        adminUser.setUpdateTime(new Date());
        return adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0;
    }

    @Override
    public boolean updatePassword(String rawPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(RequestContextUtils.getUserId());
        if (adminUser == null)
            throw new BaseException("用户不存在");
        if (!passwordEncoder.matches(rawPassword, adminUser.getPassword()))
            throw new BaseException("旧密码错误");
        adminUser.setPassword(passwordEncoder.encode(newPassword));
        return adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0;
    }

    @Override
    @RedissonDistributedLock(key = "#loginDto.loginAccount",
            failStrategy = RedissonDistributedLock.FailStrategy.THROW_EXCEPTION,
            automaticRelease = false,
            msgKey = "ADMIN_USER_LOGINING")
    public AdminUserLoginResultDto login(AdminUserLoginDto loginDto) {
        AdminUser adminUser = adminUserMapper.getByAccountName(loginDto.getLoginAccount());
        List<Permission> permissionList;
        String tags;
        AdminUserLoginResultDto result = new AdminUserLoginResultDto();
        if (!loginDto.getLoginAccount().equals(userServiceConfig.getAdmin().getAccount())) {
            if (adminUser == null)
                throw new BaseException("用户不存在");
            if (adminUser.getStatus() == 0)
                throw new BaseException("您的账户已被禁用");
            if (!passwordEncoder.matches(loginDto.getPassword(), adminUser.getPassword()))
                throw new BaseException("密码错误，请重新输入");
            if (adminUser.getPositionId() == 1) {
                tags = String.join(",", listAllPermission().stream().map(Permission::getTag).collect(Collectors.toSet()));
                permissionList = permissionMapper.listAll(new HashSet<>());
            } else {
                Post position = positionMapper.selectByPrimaryKey(adminUser.getPositionId());
                tags = position.getPermissionTags();
                permissionList = permissionMapper.listAll(new HashSet<>(StringUtils.isBlank(tags) ? new ArrayList<>() : Arrays.asList(tags.split(","))));
            }
        } else {
            if (!passwordEncoder.matches(loginDto.getPassword(), userServiceConfig.getAdmin().getPassword()))
                throw new BaseException("密码错误，请重新输入");
            if (adminUser == null)
                adminUser = AdminUser.builder().id(-1L).accountName(userServiceConfig.getAdmin().getAccount()).mobile("").positionId(0).status(1).realName("-").build();
            tags = String.join(",", listAllPermission().stream().map(Permission::getTag).collect(Collectors.toSet()));
            permissionList = permissionMapper.listAll(new HashSet<>());
        }
        adminUser.setPassword(null);
        result.setUser(adminUser);
        result.setTags(tags);
        String deviceId = String.valueOf(System.currentTimeMillis());
        String token = jwtUtil.sign(adminUser.getId(), UserTypeEnum.MANAGER, adminUser.getAccountName(), deviceId);
        result.setToken(token);
        if (CollectionUtils.isNotEmpty(permissionList)) {
            RMap<String, String> map = redis.getMap(CacheKey.Admin.PERMISSION_MAP.key + adminUser.getId());
            permissionList.forEach(p -> map.put(p.getUrl(), p.getUrl()));
            map.expire(CacheKey.Admin.LOGIN_MAP.expireTime + 1, CacheKey.Admin.LOGIN_MAP.timeUnit);
        }
        RMapCache<Long, String> loginMap = redis.getMapCache(CacheKey.Admin.LOGIN_MAP.key);
        loginMap.put(adminUser.getId(), deviceId, CacheKey.Admin.LOGIN_MAP.expireTime, CacheKey.Admin.LOGIN_MAP.timeUnit);// 1 获取不到 要求登录 2 不一样则是被别人登录
        // 登录之后清除标记
        redis.getMap(CacheKey.Admin.RESET_PASSWORD_MAP.key).removeAsync(adminUser.getId());
        return result;
    }

    @Override
    public PageInfo<AdminUserListDto> listUser(AdminUserListRequestDto requestDto) {
        PageHelper.startPage(requestDto.getPage(), requestDto.getSize());
        return new PageInfo<>(adminUserMapper.listUser(requestDto));
    }

    @Override
    public AdminUser getById(Long userId) {
        return adminUserMapper.selectByPrimaryKey(userId);
    }


    private void positonStatusCheck(Integer positionId) {
        Post position = positionMapper.selectByPrimaryKey(positionId);
        if (position == null)
            throw new BaseException("岗位不存在");
        if (position.getStatus() == 0)
            throw new BaseException("该岗位已被禁用，无法添加");
    }

}
