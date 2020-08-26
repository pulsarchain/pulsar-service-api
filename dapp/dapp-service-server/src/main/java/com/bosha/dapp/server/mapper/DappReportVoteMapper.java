package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.DappReportVote;
import org.apache.ibatis.annotations.Param;

public interface DappReportVoteMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DappReportVote record);

    DappReportVote selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DappReportVote record);

    int batchInsert(@Param("list") List<DappReportVote> list);

    int count(@Param("address") String address, @Param("id") Long id);

    int countVote(@Param("id") Long id);
}