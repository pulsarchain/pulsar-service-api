package com.bosha.star.server.mapper;

import java.util.List;


import com.bosha.star.api.entity.StarMember;
import org.apache.ibatis.annotations.Param;

public interface StarMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(StarMember record);

    StarMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StarMember record);

    int batchInsert(@Param("list") List<StarMember> list);

    StarMember getByAddress(@Param("address") String address);

    int countDistribute(@Param("starMemberId") Long starMemberId, @Param("starId") Long starId);

    List<StarMember> listDistribute(@Param("starMemberId") Long starMemberId,
                                    @Param("starId") Long starId);

    int countMember(@Param("starId") Long starId);

}