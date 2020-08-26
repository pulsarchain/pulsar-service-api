package com.bosha.user.server.mapper;

import com.bosha.user.api.dto.ContractCoinListDto;import com.bosha.user.api.entity.ContractCoin;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContractCoinMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractCoin record);

    int insertSelective(ContractCoin record);

    ContractCoin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractCoin record);

    int updateByPrimaryKey(ContractCoin record);

    int batchInsert(@Param("list") List<ContractCoin> list);

    ContractCoin selectByHash(String hash);

    List<ContractCoinListDto> list();
}