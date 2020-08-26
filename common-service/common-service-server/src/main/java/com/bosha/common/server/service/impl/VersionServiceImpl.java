package com.bosha.common.server.service.impl;

import java.util.Date;


import com.bosha.common.api.entity.Version;
import com.bosha.common.api.service.VersionService;
import com.bosha.common.server.mapper.VersionMapper;
import com.bosha.commons.dto.Page;
import com.bosha.commons.dto.RequestInfo;
import com.bosha.commons.enums.UserClientTypeEnum;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: VersionServiceImpl
 * @Author liqingping
 * @Date 2019-12-13 11:16
 */
@Slf4j
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionMapper versionMapper;

    @Override
    public Version check() {
        RequestInfo requestInfo = RequestContextUtils.getRequestInfo();
        UserClientTypeEnum clientType = requestInfo.getClientType();
        if (clientType.equals(UserClientTypeEnum.Unknown))
            return null;
        Version max = versionMapper.getMax(clientType.getType());
        return max;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(Version version) {
        version.setCreateTime(new Date());
        version.setStatus(1);
        try {
            versionMapper.insertSelective(version);
        } catch (DuplicateKeyException e) {
            throw new BaseException("该版本号已存在");
        }
        return version.getId();
    }

    @Override
    public Version getById(Long id) {
        return versionMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Version version) {
        version.setUpdateTime(new Date());
        try {
            return versionMapper.updateByPrimaryKeySelective(version) > 0;
        } catch (DuplicateKeyException e) {
           throw new BaseException("该系统类型下已存在此版本");
        }
    }

    @Override
    public PageInfo<Version> list(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(versionMapper.list());
    }
}
