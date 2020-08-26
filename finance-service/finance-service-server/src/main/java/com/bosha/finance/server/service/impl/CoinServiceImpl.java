package com.bosha.finance.server.service.impl;

import com.bosha.common.api.dto.market.CoinPrice;
import com.bosha.common.api.service.MarketService;
import com.bosha.finance.api.dto.request.CoinDto;
import com.bosha.finance.api.dto.request.QueryCoinDto;
import com.bosha.finance.api.dto.response.CoinAssetListDto;
import com.bosha.finance.api.dto.response.CoinDetailListDto;
import com.bosha.finance.api.dto.response.CoinListDto;
import com.bosha.finance.api.entity.Coin;
import com.bosha.finance.api.service.CoinService;
import com.bosha.finance.server.mapper.CoinMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CoinServiceImpl implements CoinService {
    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private MarketService marketService;

    @Override
    public PageInfo<CoinListDto> findCoinPageInfo(QueryCoinDto queryCoinDto) {
        PageHelper.startPage(queryCoinDto.getPage(), queryCoinDto.getSize());
        List<CoinListDto> coinListDto = coinMapper.findCoinPageInfo(queryCoinDto);
        return PageInfo.of(coinListDto);
    }

    @Override
    public void saveOrUpdateCoin(CoinDto coinDto) {
        //如果id为空添加
        Coin coin = new Coin();
        BeanUtils.copyProperties(coinDto, coin);
        coin.setSymbolName(coin.getSymbolName().toUpperCase());
        if (StringUtils.isEmpty(coinDto.getId())) {
            coin.setCreateTime(new Date());
            coinMapper.insertSelective(coin);
        } else {
            coin.setUpdateTime(new Date());
            coinMapper.updateByPrimaryKeySelective(coin);
        }
    }

    @Override
    public List<CoinAssetListDto> findUserCoinAsset(Long userId) {
        List<CoinAssetListDto> userCoinAssetList = coinMapper.findCoinAssetByUserId(userId);
        if (!userCoinAssetList.isEmpty()) {
            //通过symbolName获取币种的行情
            Set<String> stringSet = userCoinAssetList.stream().map(CoinAssetListDto::getSymbolName).collect(Collectors.toSet());
            Map<String, CoinPrice> byNames = marketService.getByNames(Lists.newArrayList(stringSet));
            for (CoinAssetListDto coinAssetListDto : userCoinAssetList) {
                CoinPrice coinPrice = byNames.get(coinAssetListDto.getSymbolName());
                coinAssetListDto.setPriceCny(coinPrice.getCny());
                coinAssetListDto.setPriceUsd(coinPrice.getUsd());
            }
        }
        return userCoinAssetList;
    }

    @Override
    public List<CoinDetailListDto> findWithdrawCoin() {
        List<CoinDetailListDto> coinDetailListDtos = coinMapper.findWithdrawCoin();
        return coinDetailListDtos;
    }

    @Override
    public List<CoinDetailListDto> findChargingCoin() {
        List<CoinDetailListDto> coinDetailListDtos = coinMapper.findChargingCoin();
        return coinDetailListDtos;
    }

    @Override
    public Coin selectCoinBySymbol(String symbol) {
        Coin coin = coinMapper.selectCoinBySymbol(symbol);
        return coin;
    }
}
