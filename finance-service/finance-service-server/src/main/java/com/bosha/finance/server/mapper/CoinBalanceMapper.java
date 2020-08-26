package com.bosha.finance.server.mapper;

import com.bosha.finance.api.dto.response.CoinBalanceListDto;
import com.bosha.finance.api.entity.CoinBalance;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CoinBalanceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CoinBalance record);

    int insertSelective(CoinBalance record);

    CoinBalance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CoinBalance record);

    int updateByPrimaryKey(CoinBalance record);


    List<CoinBalanceListDto> findCoinBalanceList();

    CoinBalance selectByCoinId(Long coinId);

    void updatePlatformBalance(@Param("coinId") Long coinId, @Param("money") BigDecimal money);


    void addPlatformBalance(@Param("coinId") Long coinId, @Param("money") BigDecimal money);

}