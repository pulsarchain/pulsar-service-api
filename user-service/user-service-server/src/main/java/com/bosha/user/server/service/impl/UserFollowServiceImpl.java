package com.bosha.user.server.service.impl;

import java.util.List;


import com.bosha.commons.exception.BaseException;
import com.bosha.user.api.dto.AddUserFriendDto;
import com.bosha.user.api.dto.UserFollowListDto;
import com.bosha.user.api.dto.UserFriendDto;
import com.bosha.user.api.entity.UserFollow;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.bosha.user.api.service.UserFollowService;
import com.bosha.user.server.mapper.UserFollowMapper;
import com.bosha.user.server.redis.CacheKey;
import com.bosha.user.server.utils.ImageConvertBase64Util;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ProjectServiceImpl
 * @Author liqingping
 * @Date 2019-12-20 11:59
 */
@RestController
@Slf4j
public class UserFollowServiceImpl implements UserFollowService {
    @Autowired
    private UserFollowMapper userFollowMapper;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void addOrDeleteFollow(String followUserAddress, String userAddress) {
        if (userAddress.equals(followUserAddress)) {
            throw new BaseException(UserErrorMessageEnum.USER_FOLLOW_SELF_ERROR);
        }
        UserFollow userFollow = userFollowMapper.selectByAddress(userAddress, followUserAddress);
        if (StringUtils.isEmpty(userFollow)) {
            userFollow = new UserFollow();
            userFollow.setFollowUserAddress(followUserAddress);
            userFollow.setUserAddress(userAddress);
            userFollow.setFollow(1);
            userFollowMapper.insertSelective(userFollow);
            return;
        }
        //未关注
        if (userFollow.getFollow().equals(0)) {
            userFollow.setFollow(1);
        } else {
            userFollow.setFollow(0);
        }
        userFollowMapper.updateByPrimaryKey(userFollow);
    }

    @Override
    public List<UserFollowListDto> selectUserFollow(String userAddress) {
        List<UserFollowListDto> userTextFollowDtoList = userFollowMapper.selectUserFollow(userAddress);
        return userTextFollowDtoList;
    }

    @Override
    public List<UserFollowListDto> selectUserFriend(String address) {
        List<UserFollowListDto> userTextFollowDtoList = userFollowMapper.selectUserFriend(address);
        return userTextFollowDtoList;
    }

    @Override
    public List<UserFollowListDto> selectCoverUserFriend(String address) {
        List<UserFollowListDto> userTextFollowDtoList = userFollowMapper.selectCoverUserFriend(address);
        return userTextFollowDtoList;
    }

    @Override
    public List<UserFollowListDto> selectFriend(String address) {
        List<UserFollowListDto> userTextFollowDtoList = userFollowMapper.selectFriend(address);
        return userTextFollowDtoList;
    }

    @Override
    public Integer selectCountByAddress(String userAddress, String followUserAddress) {
        Integer count = userFollowMapper.selectCountByAddress(userAddress, followUserAddress);
        return count;
    }

    @Override
    public void addFriend(@RequestBody AddUserFriendDto userFriendDto) {
        if (userFriendDto.getUserAddress().equals(userFriendDto.getFollowUserAddress())) {
            throw new BaseException(UserErrorMessageEnum.USER_FOLLOW_SELF_ERROR);
        }
        UserFollow userFollow = userFollowMapper.selectByAddress(userFriendDto.getUserAddress(), userFriendDto.getFollowUserAddress());
        if (StringUtils.isEmpty(userFollow)) {
            userFollow = new UserFollow();
            userFollow.setFollowUserAddress(userFriendDto.getFollowUserAddress());
            userFollow.setUserAddress(userFriendDto.getUserAddress());
            userFollow.setFriend(1);
            userFollow.setRemark(userFriendDto.getRemark());
            userFollowMapper.insertSelective(userFollow);
        } else {
            userFollow.setFriend(1);
            userFollow.setRemark(userFriendDto.getRemark());
            userFollowMapper.updateByPrimaryKeySelective(userFollow);
        }
        RBucket<UserFollow> bucket = redissonClient.getBucket(CacheKey.Friend.Friend.getKey() + userFriendDto.getUserAddress() + "_" + userFriendDto.getFollowUserAddress());
        bucket.set(userFollow);
        userFollow = userFollowMapper.selectByAddress(userFriendDto.getFollowUserAddress(), userFriendDto.getUserAddress());
        if (StringUtils.isEmpty(userFollow)) {
            userFollow = new UserFollow();
            userFollow.setFollowUserAddress(userFriendDto.getUserAddress());
            userFollow.setUserAddress(userFriendDto.getFollowUserAddress());
            userFollow.setRemark(userFriendDto.getFromRemark());
            userFollow.setFriend(1);
            userFollowMapper.insertSelective(userFollow);
        } else {
            userFollow.setFriend(1);
            userFollow.setRemark(userFriendDto.getFromRemark());
            userFollowMapper.updateByPrimaryKeySelective(userFollow);
        }
        bucket = redissonClient.getBucket(CacheKey.Friend.Friend.getKey() + userFriendDto.getFollowUserAddress() + "_" + userFriendDto.getUserAddress());
        bucket.set(userFollow);
    }

    @Override
    public void deleteFriend(String followUserAddress, String userAddress) {
        UserFollow userFollow = userFollowMapper.selectByAddress(userAddress, followUserAddress);
        if (!StringUtils.isEmpty(userFollow)) {
            userFollow.setFriend(0);
            userFollow.setRemark(null);
            userFollowMapper.updateByPrimaryKeySelective(userFollow);
            //删除好友关系
            RBucket<UserFollow> bucket = redissonClient.getBucket(CacheKey.Friend.Friend.getKey() + userAddress + "_" + followUserAddress);
            bucket.delete();
        }
        userFollow = userFollowMapper.selectByAddress(followUserAddress, userAddress);
        if (!StringUtils.isEmpty(userFollow)) {
            userFollow.setFriend(0);
            userFollow.setRemark(null);
            userFollowMapper.updateByPrimaryKeySelective(userFollow);
            //删除好友关系
            RBucket<UserFollow> bucket = redissonClient.getBucket(CacheKey.Friend.Friend.getKey() + followUserAddress + "_" + userAddress);
            bucket.delete();
        }

    }

    @Override
    public void update(UserFollow userFollow) {
        userFollowMapper.updateByPrimaryKeySelective(userFollow);
        userFollow = userFollowMapper.selectByPrimaryKey(userFollow.getId());
        //修改好友里面的备注信息
        RBucket<UserFollow> bucket = redissonClient.getBucket(CacheKey.Friend.Friend.getKey() + userFollow.getUserAddress() + "_" + userFollow.getFollowUserAddress());
        bucket.set(userFollow);
    }

    @Override
    public UserFriendDto searchAddress(@RequestParam("address") String address, @RequestParam("userAddress") String userAddress) {
        UserFriendDto userFriendDto = userFollowMapper.searchAddress(address, userAddress);
        if (StringUtils.isEmpty(userFriendDto)) {
            return userFriendDto;
        }
        userFriendDto.setHeadImgBase64(ImageConvertBase64Util.getImgUrlToBase64(userFriendDto.getHeadImg()));
        return userFriendDto;
    }

}
