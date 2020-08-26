package com.bosha.user.server.service.impl;

import com.bosha.commons.exception.BaseException;
import com.bosha.user.api.dto.*;
import com.bosha.user.api.entity.ImGroup;
import com.bosha.user.api.entity.ImGroupMember;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.service.ImGroupService;
import com.bosha.user.server.mapper.ImGroupMapper;
import com.bosha.user.server.mapper.ImGroupMemberMapper;
import com.bosha.user.server.redis.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.bosha.user.api.enums.UserErrorMessageEnum.GROUP_MEMBER_NOT_FOUND;
import static com.bosha.user.api.enums.UserErrorMessageEnum.GROUP_NOT_FOUND;
import static com.bosha.user.api.enums.UserErrorMessageEnum.GROUP_NOT_OWNER;

@RestController
@Slf4j
public class ImGroupServiceImpl implements ImGroupService {
    @Autowired
    private ImGroupMapper imGroupMapper;
    @Autowired
    private ImGroupMemberMapper imGroupMemberMapper;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public ImGroup create(@RequestBody ImGroupDto imGroupDto) {
        ImGroup imGroup = new ImGroup();
        BeanUtils.copyProperties(imGroupDto, imGroup);
        imGroupMapper.insertSelective(imGroup);
        //获取创建人的信息
        Set<String> set = new LinkedHashSet<>();
        RBucket<User> userRBucket = redissonClient.getBucket(CacheKey.User.USER.getKey() + imGroupDto.getAddress());
        User user = userRBucket.get();
        ImGroupMember imGroupMember = new ImGroupMember();
        if (!StringUtils.isEmpty(user)) {
            imGroupMember.setNickName(user.getNickName());
        }
        imGroupMember.setAddress(imGroupDto.getAddress());
        imGroupMember.setGroupId(imGroup.getId());
        imGroupMemberMapper.insertSelective(imGroupMember);
        set.add(imGroupDto.getAddress());
        //群成员
        for (UserFollowListDto userFollowListDto : imGroupDto.getMember()) {
            imGroupMember = imGroupMemberMapper.selectByGroupIdAndAddress(imGroup.getId(), userFollowListDto.getAddress());
            if (!StringUtils.isEmpty(imGroupMember)) {
                continue;
            }
            imGroupMember = new ImGroupMember();
            imGroupMember.setAddress(userFollowListDto.getAddress());
            imGroupMember.setGroupId(imGroup.getId());
            imGroupMember.setNickName(userFollowListDto.getNickName());
            imGroupMemberMapper.insertSelective(imGroupMember);
            set.add(userFollowListDto.getAddress());
        }
        //将主存入redis
        RBucket<ImGroup> bucket = redissonClient.getBucket(CacheKey.ImGroup.Group.getKey() + imGroup.getId());
        bucket.set(imGroup);
        //将群里面的人存入redis
        RSet<String> members = redissonClient.getSet(CacheKey.ImGroup.Group_member.getKey() + imGroup.getId());
        members.addAll(set);
        return imGroup;
    }

    @Override
    public void addMember(@RequestBody ImGroupMemberDto imGroupMemberDto) {
        Set<String> set = new LinkedHashSet<>();
        ImGroupMember groupMember = imGroupMemberMapper.selectByGroupIdAndAddress(imGroupMemberDto.getGroupId(), imGroupMemberDto.getAddress());
        //如果当前这个人，已经不在群里面了，就提示不能把别人加入群
        if (StringUtils.isEmpty(groupMember)) {
            throw new BaseException(GROUP_MEMBER_NOT_FOUND);
        }
        for (UserFollowListDto userFollowListDto : imGroupMemberDto.getMember()) {
            //如果已经存在此人，就直接跳过
            ImGroupMember imGroupMember = imGroupMemberMapper.selectByGroupIdAndAddress(imGroupMemberDto.getGroupId(), userFollowListDto.getAddress());
            if (!StringUtils.isEmpty(imGroupMember)) {
                continue;
            }
            imGroupMember = new ImGroupMember();
            imGroupMember.setAddress(userFollowListDto.getAddress());
            imGroupMember.setGroupId(imGroupMemberDto.getGroupId());
            imGroupMember.setNickName(userFollowListDto.getNickName());
            imGroupMemberMapper.insertSelective(imGroupMember);
            set.add(userFollowListDto.getAddress());
        }
        //将群里面的人存入redis
        RSet<String> members = redissonClient.getSet(CacheKey.ImGroup.Group_member.getKey() + imGroupMemberDto.getGroupId());
        members.addAll(set);
    }

    @Override
    public void removeMember(@RequestBody ImGroupMemberDto imGroupMemberDto) {
        ImGroup imGroup = imGroupMapper.selectByPrimaryKey(imGroupMemberDto.getGroupId());
        if (StringUtils.isEmpty(imGroup)) {
            throw new BaseException(GROUP_NOT_FOUND);
        }
        //如果不是群主就不能移人
        if (!imGroupMemberDto.getAddress().equals(imGroup.getAddress())) {
            throw new BaseException(GROUP_NOT_OWNER);
        }
        //删除redis里面的人
        RSet<String> set = redissonClient.getSet(CacheKey.ImGroup.Group_member.getKey() + imGroupMemberDto.getGroupId());
        for (UserFollowListDto userFollowListDto : imGroupMemberDto.getMember()) {
            imGroupMemberMapper.deleteByGroupIdAndAddress(imGroupMemberDto.getGroupId(), userFollowListDto.getAddress());
            set.remove(userFollowListDto.getAddress());
        }
    }

    @Override
    public void update(@RequestBody ImGroup imGroup) {
        imGroupMapper.updateByPrimaryKeySelective(imGroup);
        //修改群后，更新redis
        imGroup = imGroupMapper.selectByPrimaryKey(imGroup.getId());
        RBucket<ImGroup> bucket = redissonClient.getBucket(CacheKey.ImGroup.Group.getKey() + imGroup.getId());
        bucket.set(imGroup);
    }

    @Override
    public ImGroupWebDto findByGroupId(Long id, String address) {
        ImGroupWebDto imGroupWebDto = imGroupMapper.selectByGroupId(id, address);
        if (StringUtils.isEmpty(imGroupWebDto)){
            throw new BaseException(GROUP_NOT_FOUND);
        }
        List<ImGroupMemberWebDto> imGroupMembers = imGroupMemberMapper.selectByGroupId(id);
        ImGroupMember imGroupMember = imGroupMemberMapper.selectByGroupIdAndAddress(id, address);
        imGroupWebDto.setExist(StringUtils.isEmpty(imGroupMember) ? 0 : 1);
        imGroupWebDto.setImGroupMemberWebDtos(imGroupMembers);
        return imGroupWebDto;
    }

    @Override
    public List<ImGroupListDto> listGroups(String address) {
        List<ImGroupListDto> imGroupListDtos = imGroupMemberMapper.selectByAddress(address);
        return imGroupListDtos;
    }

    @Override
    public void retreatGroup(Long groupId, String address) {
        Integer count = imGroupMemberMapper.selectByCount(groupId);
        //表示最后一个人退群，就直接删除群，和群成员
        if (count == 1) {
            imGroupMapper.deleteByPrimaryKey(groupId);
            imGroupMemberMapper.deleteByGroupIdAndAddress(groupId, address);
            RSet<String> set = redissonClient.getSet(CacheKey.ImGroup.Group_member.getKey() + groupId);
            set.delete();
            RBucket<ImGroup> bucket = redissonClient.getBucket(CacheKey.ImGroup.Group.getKey() + groupId);
            bucket.delete();
            return;
        }
        ImGroup imGroup = imGroupMapper.selectByPrimaryKey(groupId);
        if (StringUtils.isEmpty(imGroup)) {
            throw new BaseException(GROUP_NOT_FOUND);
        }
        //如果是群主退群，就指派群里面下一个人当群主
        if (address.equals(imGroup.getAddress())) {
            ImGroupMember imGroupMember = imGroupMemberMapper.selectNextAddress(groupId, address);
            imGroup.setAddress(imGroupMember.getAddress());
            imGroupMapper.updateByPrimaryKeySelective(imGroup);
            RBucket<ImGroup> bucket = redissonClient.getBucket(CacheKey.ImGroup.Group.getKey() + groupId);
            bucket.set(imGroup);

        }
        imGroupMemberMapper.deleteByGroupIdAndAddress(groupId, address);
        RSet<String> set = redissonClient.getSet(CacheKey.ImGroup.Group_member.getKey() + groupId);
        set.remove(address);
    }
}
