package com.bosha.finance.server.service.impl;

import cn.hutool.core.util.IdUtil;
import com.bosha.common.api.dto.Sms;
import com.bosha.common.api.service.SmsService;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.exception.BaseException;
import com.bosha.finance.api.enums.AddressPoolStatusEnum;
import com.bosha.finance.api.enums.TransactionStatusEnum;
import com.bosha.finance.api.enums.TransactionTypeEnum;
import com.bosha.finance.api.dto.request.UserAssetDto;
import com.bosha.finance.api.entity.*;
import com.bosha.finance.api.enums.FinanceErrorMessageEnum;
import com.bosha.finance.api.service.AssetService;
import com.bosha.finance.server.config.FinanceConfig;
import com.bosha.finance.server.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetMapper assetMapper;
    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private AddressPoolMapper addressPoolMapper;
    @Autowired
    private CoinBalanceMapper coinBalanceMapper;
    @Autowired
    private TransactionBillMapper transactionBillMapper;
    @Autowired
    private FinanceConfig financeConfig;
    @Autowired
    private SmsService smsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initUserAsset(Long userId) {
        List<Coin> coins = coinMapper.selectAllCoinList();
        if (coins.isEmpty()) {
            throw new BaseException(FinanceErrorMessageEnum.COIN_IS_NULL);
        }
        for (Coin coin : coins) {
            Asset asset = assetMapper.selectByCoinIdAndUserId(coin.getId(), userId);
            if (StringUtils.isEmpty(asset)) {
                asset = new Asset();
                asset.setCoinId(coin.getId());
                asset.setUserId(userId);
                assetMapper.insertSelective(asset);
            }

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Asset getUserCoinAsset(Long coinId, Long userId) {
        Asset asset = assetMapper.selectByCoinIdAndUserId(coinId, userId);
        //如果没有当前币种的资产就添加一个资产信息
        if (StringUtils.isEmpty(asset)) {
            asset = new Asset();
            asset.setCoinId(coinId);
            asset.setUserId(userId);
            assetMapper.insertSelective(asset);
        }
        //如果用户的资产地址不存在就获取一个新的币种地址绑定
        if (StringUtils.isEmpty(asset.getAddress())) {
            AddressPool addressPool = addressPoolMapper.selectByCoinIdAndStatus(coinId);
            if (StringUtils.isEmpty(addressPool)) {
                throw new BaseException(FinanceErrorMessageEnum.ADDRESS_POOL_IS_NULL);
            }
            //将地址池中的地址绑定到用户上，并将地址池中地址状态改为已使用
            asset.setAddress(addressPool.getAddress());
            addressPool.setStatus(AddressPoolStatusEnum.USED.getStatus());
            addressPoolMapper.updateByPrimaryKeySelective(addressPool);
            assetMapper.updateByPrimaryKeySelective(asset);
        }
        return asset;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserAsset(UserAssetDto userAssetDto) {
        log.info("平台用户余额添加，并且加入账单..........{}",userAssetDto);
        GlobalExecutorService.executorService.execute(() -> {
                    CoinBalance coinBalance = coinBalanceMapper.selectByCoinId(userAssetDto.getCoinId());
                    if (StringUtils.isEmpty(coinBalance)) {
                        throw new BaseException(FinanceErrorMessageEnum.COIN_BALANCE_IS_NULL);
                    }
                    //如果平台的余额小于需要扣除的余额
                    if (coinBalance.getPlatformBalance().compareTo(userAssetDto.getMoney()) < 0) {
                        //通知管理员转币
                        for (String s : financeConfig.getSms().getPhone()) {
                            log.info("发送短信通知管理员，冲币到平台账户:{},币种Id:{}", s,userAssetDto.getCoinId());
                            Sms sms = Sms.builder().
                                    content(String.format(financeConfig.getSms().getMessage(), coinBalance.getPlatformBalance())).phone(s).build();
                            smsService.send(sms);
                        }
                        throw new BaseException(FinanceErrorMessageEnum.PLATFORM_BALANCE_INSUFFICIENT);
                    }
                    // 先将平台中的余额减去
                    coinBalanceMapper.updatePlatformBalance(userAssetDto.getCoinId(), userAssetDto.getMoney());
                    Asset asset = assetMapper.selectByCoinIdAndUserId(userAssetDto.getCoinId(), userAssetDto.getUserId());
                    BigDecimal oldBalance = asset.getBalance();
                    // 先更新用户余额，再加上用户的流水信息
                    assetMapper.addBalanceByCoinIdAndUserId(userAssetDto.getCoinId(), userAssetDto.getUserId(), userAssetDto.getMoney());
                    TransactionBill transactionBill = new TransactionBill();
                    String serialNumber = IdUtil.fastSimpleUUID();
                    transactionBill.setUserId(userAssetDto.getUserId());
                    transactionBill.setSerialNumber(serialNumber);
                    transactionBill.setType(TransactionTypeEnum.INTO.getType());
                    transactionBill.setServiceType(userAssetDto.getFinanceServiceTypeEnum().getType());
                    transactionBill.setCoin(userAssetDto.getCoinId());
                    transactionBill.setNumber(userAssetDto.getMoney());
                    transactionBill.setActualNumber(userAssetDto.getMoney());
                    transactionBill.setTransactionTime(new Date());
                    transactionBill.setStatus(TransactionStatusEnum.COMPLETED.getStatus());
                    transactionBill.setBalance(oldBalance);
                    transactionBill.setRemark(userAssetDto.getRemark());
                    transactionBillMapper.insertSelective(transactionBill);
                    log.info("用户账户余额添加成功，流水添加成功。。。币种Id: {}, 用户Id:{}  ,添加余额：{}，流水号：{}  ",
                            userAssetDto.getCoinId(), userAssetDto.getUserId(), userAssetDto.getMoney(), serialNumber);
                }
        );
    }
}
