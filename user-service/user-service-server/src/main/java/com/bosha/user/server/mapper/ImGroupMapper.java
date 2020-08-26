package com.bosha.user.server.mapper;

import com.bosha.user.api.dto.ImGroupWebDto;
import com.bosha.user.api.entity.ImGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImGroupMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ImGroup record);

    int insertSelective(ImGroup record);

    ImGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImGroup record);

    int updateByPrimaryKey(ImGroup record);

    int batchInsert(@Param("list") List<ImGroup> list);

    ImGroupWebDto selectByGroupId(@Param("groupId") Long groupId,@Param("address") String address);


}