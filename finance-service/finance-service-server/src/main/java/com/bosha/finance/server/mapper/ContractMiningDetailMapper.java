package com.bosha.finance.server.mapper;

import java.math.BigDecimal;
import java.util.List;


import com.bosha.finance.api.dto.response.ContractMiningDetailListDto;
import com.bosha.finance.api.dto.response.MyEarningsDto;
import com.bosha.finance.api.entity.ContractMiningDetail;
import org.apache.ibatis.annotations.Param;

public interface ContractMiningDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContractMiningDetail record);

    int insertSelective(ContractMiningDetail record);

    ContractMiningDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContractMiningDetail record);

    int updateByPrimaryKey(ContractMiningDetail record);

    int batchInsert(@Param("list") List<ContractMiningDetail> list);

    List<ContractMiningDetailListDto> list(String address);

    BigDecimal total(String address);

    int batchUpdate(@Param("list") List<ContractMiningDetail> list, @Param("status") int status);

    MyEarningsDto myEarnings(@Param("address") String address);
}