package com.bosha.user.server.mapper;


import java.util.List;


import com.bosha.user.api.dto.AdminUserListDto;
import com.bosha.user.api.dto.AdminUserListRequestDto;
import com.bosha.user.api.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPositionId(@Param("positionId") Integer positionId);

    AdminUser getByAccountName(@Param("accountName") String accountName);

    List<AdminUser> listByPositionId(@Param("positionId") Integer positionId);

    List<AdminUserListDto> listUser(AdminUserListRequestDto requestDto);

}