package com.bosha.common.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;


import com.bosha.common.api.dto.market.CoinPrice;
import com.bosha.common.api.entity.Dict;
import com.bosha.common.api.enums.DictTypeEnum;
import com.bosha.common.api.service.DictService;
import com.bosha.common.api.service.MarketService;
import com.bosha.common.server.redis.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MarketServiceImpl
 * @Author liqingping
 * @Date 2019-12-17 17:20
 */
@RestController
@Slf4j
public class MarketServiceImpl implements MarketService {

    @Autowired
    private RedissonClient redis;
    @Autowired
    private DictService dictService;

    @Override
    public List<CoinPrice> list() {
        RList<CoinPrice> list = redis.getList(CacheKey.Market.COIN_PRICE_LIST.key);
        if (list.isExists()) {
            return list;
        }
        return getCoinPrices();
    }

    @Override
    public CoinPrice getByName(@PathVariable("coin") String coinName) {
        RMap<String, CoinPrice> map = redis.getMap(CacheKey.Market.COIN_PRICE_MAP.key);
        if (map.isEmpty() || map.get(coinName.toUpperCase()) == null) {
            return null;
        }
        return map.get(coinName.toUpperCase());
    }

    @Override
    public Map<String, CoinPrice> getByNames(@RequestBody @Validated @NotEmpty(message = "size不可为空") List<String> names) {
        RMap<String, CoinPrice> map = redis.getMap(CacheKey.Market.COIN_PRICE_MAP.key);
        if (map.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, CoinPrice> coinPriceMap = new HashMap<>();
        names.forEach(s -> {
            CoinPrice coinPrice = map.getOrDefault(s.toUpperCase(), new CoinPrice());
            coinPriceMap.put(s.toUpperCase(), coinPrice);
        });
        return coinPriceMap;
    }

    @Override
    public Map<String, BigDecimal> getLegalTender(@RequestParam("tradeType") String tradeType) {
        RMap<String, Map<String, BigDecimal>> map = redis.getMap(CacheKey.Market.COIN_RATE_MAP.key);
        if (map.isEmpty())
            return new HashMap<>();
        Map<String, BigDecimal> legalTenderMap = map.get(tradeType.toUpperCase());
        return legalTenderMap == null ? new HashMap<>() : legalTenderMap;
    }

    public List<CoinPrice> getCoinPrices() {
        List<Dict> dictList = dictService.listType(DictTypeEnum.market);
        List<CoinPrice> coinPriceList = new ArrayList<>();
        if (CollectionUtils.isEmpty(dictList))
            return new ArrayList<>();
        RMap<String, CoinPrice> map = redis.getMap(CacheKey.Market.COIN_PRICE_MAP.key);
        dictList.forEach(dict -> map.forEach((s, coinPrice) -> {
            if (coinPrice.getTradePairs().equalsIgnoreCase(dict.getKey()))
                coinPriceList.add(coinPrice);
        }));
        return coinPriceList;
    }
}
