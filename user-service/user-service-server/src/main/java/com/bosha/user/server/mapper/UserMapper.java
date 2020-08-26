package com.bosha.user.server.mapper;

import java.util.List;import com.bosha.user.api.dto.AddressToUid;import com.bosha.user.api.dto.ManagerUserListDto;import com.bosha.user.api.dto.UserBasicDto;import com.bosha.user.api.dto.UserManagerListDto;import com.bosha.user.api.entity.User;import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getByAddress(@Param("address") String address);

    List<ManagerUserListDto> list(@Param("dto") UserManagerListDto dto);

    UserBasicDto basic(@Param("userId") Long userId);

    List<AddressToUid> addressToIds(@Param("address") List<String> address);

    List<AddressToUid> idToAddresses(@Param("ids") List<Long> ids);

    List<String> randomAddresses(@Param("num") Integer num);

    int count();

    List<String> addresses(@Param("page") Integer page, @Param("size") Integer size);

    List<String> addressList();

    List<User> listUserHeadImgIsNull();
}