package com.bosha.finance.server.mapper;

import java.math.BigDecimal;
import java.util.List;


import com.bosha.finance.api.dto.response.MiningDetailListDto;
import com.bosha.finance.api.entity.MiningDetail;
import org.apache.ibatis.annotations.Param;

public interface MiningDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiningDetail record);

    int insertSelective(MiningDetail record);

    MiningDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiningDetail record);

    int updateByPrimaryKey(MiningDetail record);

    BigDecimal selectToDayMoney(Long userId);


    BigDecimal selectTotalMoney(Long userId);

    List<MiningDetailListDto> findMiningDetailPage(Long userId);

    BigDecimal myEarnings(@Param("address") String address);
}