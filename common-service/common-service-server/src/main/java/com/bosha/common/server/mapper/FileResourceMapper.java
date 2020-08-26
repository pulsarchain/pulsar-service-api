package com.bosha.common.server.mapper;

import com.bosha.common.api.entity.FileResource;

public interface FileResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileResource record);

    int insertSelective(FileResource record);

    FileResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileResource record);

    int updateByPrimaryKey(FileResource record);
}