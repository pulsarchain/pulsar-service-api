package com.bosha.user.server.mapper;

import java.util.Date;
import java.util.List;


import com.bosha.user.api.dto.UserInviteInfoDto;
import com.bosha.user.api.entity.UserInviteRecord;
import org.apache.ibatis.annotations.Param;

public interface UserInviteRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInviteRecord record);

    int insertSelective(UserInviteRecord record);

    UserInviteRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInviteRecord record);

    int updateByPrimaryKey(UserInviteRecord record);

    UserInviteInfoDto inviteCount(@Param("userId") Long userId, @Param("now") Date now);

    int beInviteCount(@Param("address") String address);

    List<UserInviteRecord> list(@Param("userId") Long userId);

    int count(@Param("address") String address);

    int countByIp(@Param("ip") String ip, @Param("startTIme") Date startTIme, @Param("endTime") Date endTime);
}