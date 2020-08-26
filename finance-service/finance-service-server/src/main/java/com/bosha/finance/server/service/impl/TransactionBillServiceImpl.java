package com.bosha.finance.server.service.impl;

import cn.hutool.core.util.IdUtil;
import com.bosha.commons.exception.BaseException;
import com.bosha.finance.api.dto.request.*;
import com.bosha.finance.api.dto.response.*;
import com.bosha.finance.api.entity.Asset;
import com.bosha.finance.api.entity.Coin;
import com.bosha.finance.api.entity.TransactionBill;
import com.bosha.finance.api.enums.*;
import com.bosha.finance.api.service.TransactionBillService;
import com.bosha.finance.server.mapper.AssetMapper;
import com.bosha.finance.server.mapper.CoinMapper;
import com.bosha.finance.server.mapper.TransactionBillMapper;
import com.bosha.finance.server.utils.Arith;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class TransactionBillServiceImpl implements TransactionBillService {
    @Autowired
    private TransactionBillMapper transactionBillMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private AssetMapper assetMapper;


    @Override
    public PageInfo<IntoTransactionBillListDto> findIntoTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto) {
        PageHelper.startPage(queryTransactionBillDto.getPage(), queryTransactionBillDto.getSize());
        List<IntoTransactionBillListDto> intoTransactionBillListDto = transactionBillMapper.findIntoTransactionBillPage(queryTransactionBillDto);
        return PageInfo.of(intoTransactionBillListDto);
    }

    @Override
    public PageInfo<OutTransactionBillListDto> findOutTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto) {
        PageHelper.startPage(queryTransactionBillDto.getPage(), queryTransactionBillDto.getSize());
        List<OutTransactionBillListDto> outTransactionBillListDto = transactionBillMapper.findOutTransactionBillPage(queryTransactionBillDto);
        return PageInfo.of(outTransactionBillListDto);
    }

    @Override
    public PageInfo<TransactionBillListDto> findTransactionBillPage(QueryTransactionBillDto queryTransactionBillDto) {
        PageHelper.startPage(queryTransactionBillDto.getPage(), queryTransactionBillDto.getSize());
        List<TransactionBillListDto> transactionBillListDto = transactionBillMapper.findTransactionBillPage(queryTransactionBillDto);
        return PageInfo.of(transactionBillListDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditWithdraw(WithDrawDto withDrawDto) {
        TransactionBill transactionBill = new TransactionBill();
        BeanUtils.copyProperties(withDrawDto, transactionBill);
        transactionBill.setAuditTime(new Date());
        transactionBillMapper.updateByPrimaryKeySelective(transactionBill);
        //如果审核通过，用户资产将冻结减去实际到账的资金
        if (withDrawDto.getStatus().equals(TransactionStatusEnum.PASSED.getStatus())){
            transactionBill = transactionBillMapper.selectByPrimaryKey(withDrawDto.getId());
            assetMapper.updateFrozenBalance(transactionBill.getCoin(),transactionBill.getUserId(),transactionBill.getActualNumber());
            //如果审核失败，就将原来的提币的资金回滚到余额当中，将冻结的资金减去实际到账的资金
        }else if (withDrawDto.getStatus().equals(TransactionStatusEnum.NOT_PASS.getStatus())){
            assetMapper.updateFrozenBalanceAndBalance(transactionBill.getCoin(),transactionBill.getUserId(),transactionBill.getActualNumber(),transactionBill.getNumber());
        }
    }

    @Override
    public PageInfo<UserTransactionBillListDto> findUserBillByUserIdAndCoinId(QueryUserTransactionBillDto queryUserTransactionBillDto) {
        PageHelper.startPage(queryUserTransactionBillDto.getPage(), queryUserTransactionBillDto.getSize());
        List<UserTransactionBillListDto> transactionBillListDto = transactionBillMapper.findUserBillByUserIdAndCoinId(queryUserTransactionBillDto);
        return PageInfo.of(transactionBillListDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withDraw(WithDrawCoinDto withDrawCoinDto) {
        //验证google验证码
        boolean b = userService.verifyGoogleSecret(withDrawCoinDto.getUserId(), withDrawCoinDto.getGoogleCode());
        if (!b) {
            throw new BaseException(FinanceErrorMessageEnum.GOOGLE_CODE_ERROR);
        }
        Coin coin = coinMapper.selectByPrimaryKey(withDrawCoinDto.getCoinId());
        if (StringUtils.isEmpty(coin)) {
            throw new BaseException(FinanceErrorMessageEnum.COIN_IS_NULL);
        }
        //币种状态不能为下架
        if (coin.getStatus().equals(CoinStatusEnum.LOWER.getStatus())) {
            throw new BaseException(FinanceErrorMessageEnum.COIN_IS_NULL);
        }
        //币种未开启提币
        if (coin.getWithdrawStatus().equals(WithDrawStatusEnum.CLOSE.getStatus())) {
            throw new BaseException(FinanceErrorMessageEnum.COIN_WITHDRAW_STATUS_ERROR);
        }
        //如果提币数小于最小提币数
        if (coin.getWithdrawMin().compareTo(withDrawCoinDto.getBalance()) < 0) {
            throw new BaseException(FinanceErrorMessageEnum.COIN_WITHDRAW_BALANCE_MIN_ERROR);
        }
        Asset asset = assetMapper.selectByCoinIdAndUserId(withDrawCoinDto.getCoinId(), withDrawCoinDto.getUserId());
        if (StringUtils.isEmpty(asset)) {
            throw new BaseException(FinanceErrorMessageEnum.USER_ASSET_ERROR);
        }
        //如果用户资产小于提币的数量
        if (asset.getBalance().compareTo(withDrawCoinDto.getBalance()) < 0) {
            throw new BaseException(FinanceErrorMessageEnum.USER_ASSET_ERROR);
        }
        //计算用户的手续费
        BigDecimal fee = Arith.mul(withDrawCoinDto.getBalance(),coin.getFee());
        //实际到账的数量
        BigDecimal actualNumber = Arith.sub(withDrawCoinDto.getBalance(),fee);
        //将用户资产提币余额化到冻结资产当中
        assetMapper.updateBalanceByUserIdAndCoinId(withDrawCoinDto.getUserId(),coin.getId(),actualNumber);
        //记录财务
        TransactionBill transactionBill = new TransactionBill();
        String serialNumber = IdUtil.fastSimpleUUID();
        transactionBill.setUserId(withDrawCoinDto.getUserId());
        transactionBill.setSerialNumber(serialNumber);
        transactionBill.setType(TransactionTypeEnum.OUT.getType());
        transactionBill.setServiceType(FinanceServiceTypeEnum.WITHDRAW.getType());
        transactionBill.setCoin(withDrawCoinDto.getCoinId());
        transactionBill.setNumber(withDrawCoinDto.getBalance());
        transactionBill.setActualNumber(actualNumber);
        transactionBill.setFee(fee);
        transactionBill.setTransactionTime(new Date());
        transactionBill.setStatus(TransactionStatusEnum.REVIEW_ING.getStatus());
        transactionBill.setToAddress(withDrawCoinDto.getToAddress());
        transactionBill.setBalance(asset.getBalance());
        transactionBill.setRemark(withDrawCoinDto.getRemark());
        transactionBillMapper.insertSelective(transactionBill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void charging(ChargingDto chargingDto) {
        log.info("充币到账财务记录：{}",chargingDto);
        Asset asset = assetMapper.selectByCoinIdAndUserId(chargingDto.getCoinId(), chargingDto.getUserId());
        if (StringUtils.isEmpty(asset)) {
            throw new BaseException(FinanceErrorMessageEnum.USER_ASSET_ERROR);
        }
        //将用户资产添加进去
        assetMapper.addBalanceByCoinIdAndUserId(chargingDto.getCoinId(), chargingDto.getUserId(),chargingDto.getMoney());
        //添加交易记录
        TransactionBill transactionBill = new TransactionBill();
        String serialNumber = IdUtil.fastSimpleUUID();
        transactionBill.setUserId(chargingDto.getUserId());
        transactionBill.setSerialNumber(serialNumber);
        transactionBill.setType(TransactionTypeEnum.INTO.getType());
        transactionBill.setServiceType(FinanceServiceTypeEnum.RECHARGE.getType());
        transactionBill.setCoin(chargingDto.getCoinId());
        transactionBill.setNumber(chargingDto.getMoney());
        transactionBill.setActualNumber(chargingDto.getMoney());
        transactionBill.setFee(chargingDto.getFee());
        transactionBill.setTransactionTime(new Date());
        transactionBill.setTransactionTime(new Date());
        transactionBill.setStatus(TransactionStatusEnum.COMPLETED.getStatus());
        transactionBill.setToAddress(chargingDto.getToAddress());
        transactionBill.setFromAddress(chargingDto.getFromAddress());
        transactionBill.setBalance(asset.getBalance());
        transactionBill.setTransactionHash(chargingDto.getTransactionHash());
        transactionBill.setBlockNum(chargingDto.getBlockNum());
        transactionBill.setRemark(chargingDto.getRemark());
        transactionBillMapper.insertSelective(transactionBill);

    }

}
