package com.bosha.scan.server.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.bosha.commons.enums.LanguageEnum;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.scan.api.dto.AddressBalanceInfoDto;
import com.bosha.scan.api.dto.AddressRateDto;
import com.bosha.scan.api.dto.TotalInfo;
import com.bosha.scan.api.entity.AddressBalance;
import com.bosha.scan.server.mapper.AddressBalanceMapper;
import com.bosha.scan.server.redis.CacheKey;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CalcRateJob
 * @Author liqingping
 * @Date 2020-04-10 18:07
 */
@Component
@Slf4j
public class CalcRateJob {

    @Autowired
    private AddressBalanceMapper addressBalanceMapper;
    @Autowired
    private RedissonClient redis;

    @XxlJob("calcRate")//每30s运行一次
    public ReturnT<String> calcRateJob(String s) {
        RBucket<AddressBalanceInfoDto> bucket = redis.getBucket(CacheKey.Scan.PERCENT.getKey());
        TotalInfo totalInfo = addressBalanceMapper.totalInfo();
        List<AddressBalance> list = addressBalanceMapper.listAddressBalance(20);
        List<AddressRateDto> updateList = new ArrayList<>();
        RMap<String, BigDecimal> map = redis.getMap(CacheKey.Scan.ALL_DELEGATE_MINERS.getKey());
        BigDecimal delegateAmount = BigDecimal.ZERO;
        if (map.isExists()) {
            Set<Map.Entry<String, BigDecimal>> entries = map.entrySet();
            delegateAmount = entries.stream().map(Map.Entry::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        BigDecimal totalBalance = delegateAmount.add(totalInfo.getTotalBalance());
        for (AddressBalance balance : list) {
            BigDecimal percent = balance.getBalance().divide(totalBalance, 4, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100));
            AddressRateDto build = AddressRateDto.builder()
                    .address(balance.getAddress()).balance(balance.getBalance()).percent(percent.setScale(2))
                    .type(balance.getType()).transactionCount(balance.getTransactionCount()).remark(balance.getRemark())
                    .build();
            updateList.add(build);
        }
        BigDecimal percentTotal = updateList.stream().map(AddressRateDto::getPercent).reduce(BigDecimal.ZERO, BigDecimal::add);
        Long transactionCount = updateList.stream().map(AddressRateDto::getTransactionCount).reduce(0L, Long::sum);
        BigDecimal balanceTotal = list.stream().map(AddressBalance::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
        updateList.add(AddressRateDto.builder().transactionCount(totalInfo.getTotalTransactionCount() - transactionCount)
                .address("").balance(totalBalance.subtract(balanceTotal).setScale(2, BigDecimal.ROUND_DOWN)).percent(BigDecimal.valueOf(100).subtract(percentTotal))
                .remark("其他")
                .build());
        updateList.sort((o1, o2) -> o2.getPercent().compareTo(o1.getPercent()));
        for (int i = 1; i <= updateList.size(); i++) {
            AddressRateDto dto = updateList.get(i - 1);
            dto.setRanking(i);
        }
        AddressBalanceInfoDto build = AddressBalanceInfoDto.builder().rateInfoList(updateList).totalOutput(totalBalance).totalAccount(totalInfo.getTotalNum()).build();
        bucket.set(build);
        return ReturnT.SUCCESS;
    }
}
