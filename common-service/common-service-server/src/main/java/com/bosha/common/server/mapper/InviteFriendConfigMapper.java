package com.bosha.common.server.mapper;

import com.bosha.common.api.entity.InviteFriendConfig;
import org.apache.ibatis.annotations.Param;

public interface InviteFriendConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InviteFriendConfig record);

    int insertSelective(InviteFriendConfig record);

    InviteFriendConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InviteFriendConfig record);

    int updateByPrimaryKey(InviteFriendConfig record);

    InviteFriendConfig getByCoinId(@Param("coinId") Long coinId);
}