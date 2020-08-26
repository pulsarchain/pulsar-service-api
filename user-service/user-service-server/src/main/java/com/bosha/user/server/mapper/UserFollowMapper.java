package com.bosha.user.server.mapper;

import com.bosha.user.api.dto.UserFollowListDto;
import com.bosha.user.api.dto.UserFriendDto;
import com.bosha.user.api.dto.UserTextFollowDto;
import com.bosha.user.api.entity.UserFollow;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserFollowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFollow record);

    int insertSelective(UserFollow record);

    UserFollow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFollow record);

    int updateByPrimaryKey(UserFollow record);

    int batchInsert(@Param("list") List<UserFollow> list);

    UserFollow selectByAddress(@Param("userAddress") String userAddress,@Param("followUserAddress") String followUserAddress);

    List<UserFollowListDto> selectUserFollow(String userAddress);

    List<UserFollowListDto> selectUserFriend(String userAddress);

    List<UserFollowListDto> selectCoverUserFriend(String address);

    Integer selectCountByAddress(@Param("userAddress")String userAddress,@Param("followUserAddress") String followUserAddress);

    UserFriendDto searchAddress(@Param("address") String address,@Param("userAddress") String userAddress);


    List<UserFollowListDto> selectFriend(String address);

}