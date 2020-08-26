package com.bosha.finance.server.mapper;

import com.bosha.finance.api.dto.request.QueryCoinDto;
import com.bosha.finance.api.dto.response.AddressPoolListDto;
import com.bosha.finance.api.dto.response.CoinAssetListDto;
import com.bosha.finance.api.dto.response.CoinDetailListDto;
import com.bosha.finance.api.dto.response.CoinListDto;
import com.bosha.finance.api.entity.Coin;

import java.util.List;

public interface CoinMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Coin record);

    int insertSelective(Coin record);

    Coin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Coin record);

    int updateByPrimaryKey(Coin record);

    List<CoinListDto> findCoinPageInfo(QueryCoinDto queryCoinDto);

    List<AddressPoolListDto> findAddressPool();

    List<Coin> selectAllCoinList();


    List<CoinAssetListDto> findCoinAssetByUserId(Long userId);

    List<CoinDetailListDto> findWithdrawCoin();


    List<CoinDetailListDto> findChargingCoin();

    Coin selectCoinBySymbol(String symbol);

}