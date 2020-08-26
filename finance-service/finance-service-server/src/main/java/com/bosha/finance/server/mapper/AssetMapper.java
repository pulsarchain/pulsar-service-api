package com.bosha.finance.server.mapper;

import com.bosha.finance.api.entity.Asset;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface AssetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Asset record);

    int insertSelective(Asset record);

    Asset selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Asset record);

    int updateByPrimaryKey(Asset record);

    Asset selectByCoinIdAndUserId(@Param("coinId") Long coinId, @Param("userId") Long userId);

    void addBalanceByCoinIdAndUserId(@Param("coinId") Long coinId, @Param("userId") Long userId, @Param("money") BigDecimal money);

    void updateBalanceByUserIdAndCoinId(@Param("userId") Long userId, @Param("coinId") Long coinId, @Param("actualNumber") BigDecimal actualNumber);

    void updateFrozenBalance(@Param("coinId") Long coinId, @Param("userId") Long userId, @Param("actualNumber") BigDecimal actualNumber);

    void updateFrozenBalanceAndBalance(@Param("coinId") Long coinId, @Param("userId") Long userId, @Param("actualNumber") BigDecimal actualNumber, @Param("number") BigDecimal number);
}