package com.bosha.user.server.mapper;

import com.bosha.user.api.dto.ImGroupListDto;
import com.bosha.user.api.dto.ImGroupMemberWebDto;
import com.bosha.user.api.entity.ImGroupMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImGroupMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImGroupMember record);

    int insertSelective(ImGroupMember record);

    ImGroupMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImGroupMember record);

    int updateByPrimaryKey(ImGroupMember record);

    int batchInsert(@Param("list") List<ImGroupMember> list);

    ImGroupMember selectByGroupIdAndAddress(@Param("groupId") Long groupId, @Param("address") String address);

    void deleteByGroupIdAndAddress(@Param("groupId") Long groupId, @Param("address") String address);

    List<ImGroupMemberWebDto> selectByGroupId(Long groupId);

    List<ImGroupListDto> selectByAddress(String address);

    Integer selectByCount(Long groupId);

    ImGroupMember selectNextAddress(@Param("groupId") Long groupId,@Param("address")  String address);

}
