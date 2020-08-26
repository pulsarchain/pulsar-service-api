package com.bosha.user.server.mapper;

import java.util.List;


import com.bosha.user.api.entity.AuthorizedCreditScore;
import org.apache.ibatis.annotations.Param;

public interface AuthorizedCreditScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AuthorizedCreditScore record);

    AuthorizedCreditScore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthorizedCreditScore record);

    int updateByPrimaryKey(AuthorizedCreditScore record);

    int updateBatch(List<AuthorizedCreditScore> list);

    int batchInsert(@Param("list") List<AuthorizedCreditScore> list);

    int insertOrUpdate(AuthorizedCreditScore record);

    int insertOrUpdateSelective(AuthorizedCreditScore record);

    AuthorizedCreditScore getByFromAndTo(@Param("from") String from, @Param("to") String to);
}