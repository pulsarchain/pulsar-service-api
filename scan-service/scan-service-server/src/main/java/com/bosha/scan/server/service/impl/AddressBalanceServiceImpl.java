package com.bosha.scan.server.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


import com.alibaba.fastjson.JSONObject;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.dto.Page;
import com.bosha.commons.enums.LanguageEnum;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.scan.api.dto.*;
import com.bosha.scan.api.entity.AddressBalance;
import com.bosha.scan.api.service.AddressBalanceService;
import com.bosha.scan.server.config.ScanServiceConfig;
import com.bosha.scan.server.dto.TransactionGroupInfo;
import com.bosha.scan.server.dto.Transactions;
import com.bosha.scan.server.mapper.AddressBalanceMapper;
import com.bosha.scan.server.redis.CacheKey;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameterName;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: AddressBalanceServiceImpl
 * @Author liqingping
 * @Date 2020-04-10 17:50
 */
@Service
@Slf4j
public class AddressBalanceServiceImpl implements AddressBalanceService {

    private static final List<String> ORDER_BY = Arrays.asList("total", "count");

    private static final List<String> SORT = Arrays.asList("asc", "desc");

    @Autowired
    private AddressBalanceMapper addressBalanceMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ScanServiceConfig scanServiceConfig;


    @Override
    public PageInfo<AddressBalance> list(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(addressBalanceMapper.listAddressBalance(null));
    }

    @Override
    public AddressBalanceInfoDto info() {
        LanguageEnum language = RequestContextUtils.getRequestInfo().getLanguage();
        RBucket<AddressBalanceInfoDto> bucket = redis.getBucket(CacheKey.Scan.PERCENT.getKey());
        AddressBalanceInfoDto dto = bucket.get();
        List<AddressRateDto> rateInfoList = dto.getRateInfoList();
        Optional<AddressRateDto> first = rateInfoList.stream().filter(a -> StringUtils.isBlank(a.getAddress())).findFirst();
        first.ifPresent(addressRateDto -> addressRateDto.setRemark(language == LanguageEnum.zh_CN ? "其他" : "other"));
        return dto;
    }

    @Override
    @RedissonDistributedLock(key = "#addressBalance.address")
    public boolean insert(AddressBalance addressBalance) {
        if (isExist(addressBalance.getAddress()))
            return false;
        int i = 0;
        try {
            i = addressBalanceMapper.insertSelective(addressBalance);
        } catch (DuplicateKeyException e) {
            return false;
        }
        log.info("【新增地址】address={}", addressBalance);
        return i > 0;
    }

    @Override
    public boolean isContract(String address) {
        AddressBalance addressBalance = addressBalanceMapper.getByAddress(address);
        return addressBalance != null && addressBalance.getType() == 1;
    }


    @Override
    public boolean isExist(String address) {
        return addressBalanceMapper.isExist(address) > 0;
    }


}
