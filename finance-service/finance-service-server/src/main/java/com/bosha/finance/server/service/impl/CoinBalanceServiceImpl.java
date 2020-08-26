package com.bosha.finance.server.service.impl;

import com.bosha.finance.api.dto.response.CoinBalanceListDto;
import com.bosha.finance.api.service.CoinBalanceService;
import com.bosha.finance.server.mapper.CoinBalanceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Slf4j
public class CoinBalanceServiceImpl implements CoinBalanceService {
    @Autowired
    private CoinBalanceMapper coinBalanceMapper;

    @Override
    public List<CoinBalanceListDto> findCoinBalanceList() {
        return coinBalanceMapper.findCoinBalanceList();
    }

    @Override
    public void addPlatformBalance(Long coinId, BigDecimal money) {
        coinBalanceMapper.addPlatformBalance(coinId,money);
    }


}
