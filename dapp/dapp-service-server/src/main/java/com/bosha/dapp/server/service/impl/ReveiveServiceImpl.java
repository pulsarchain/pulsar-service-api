package com.bosha.dapp.server.service.impl;

import java.util.Date;
import java.util.List;


import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.entity.SparksReceiveAccount;
import com.bosha.dapp.api.entity.SparksReceiverAddress;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.service.ReveiveService;
import com.bosha.dapp.server.mapper.SparksReceiveAccountMapper;
import com.bosha.dapp.server.mapper.SparksReceiverAddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ReveiveServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-25 17:58
 */
@Service
@Slf4j
public class ReveiveServiceImpl implements ReveiveService {

    @Autowired
    private SparksReceiveAccountMapper sparksReceiveAccountMapper;
    @Autowired
    private SparksReceiverAddressMapper sparksReceiverAddressMapper;

    @Override
    public Long addReceiveAccount(SparksReceiveAccount account) {
        SparksReceiveAccount receiveAccount = sparksReceiveAccountMapper.getByAddressAndType(account.getAddress(), account.getType());
        if (receiveAccount != null)
            throw new BaseException(DappErrorMsgEnum.ONE_TYPE_ONE_ACCOUNT);
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        sparksReceiveAccountMapper.insertSelective(account);
        return account.getId();
    }

    @Override
    public boolean updateReceiveAccount(SparksReceiveAccount account) {
        account.setType(null);
        account.setUpdateTime(new Date());
        return sparksReceiveAccountMapper.updateByPrimaryKeySelective(account) > 0;
    }

    @Override
    public List<SparksReceiveAccount> listReceiveAccount(String address) {
        return sparksReceiveAccountMapper.list(address);
    }

    @Override
    public Long addReceiveAddress(SparksReceiverAddress address) {
        address.setCreateTime(new Date());
        address.setUpdateTime(new Date());
        sparksReceiverAddressMapper.insertSelective(address);
        return address.getId();
    }

    @Override
    public boolean updateReceiveAddress(SparksReceiverAddress address) {
        address.setUpdateTime(new Date());
        return sparksReceiverAddressMapper.updateByPrimaryKeySelective(address) > 0;
    }

    @Override
    public List<SparksReceiverAddress> listReceiveAddress(String address) {
        return sparksReceiverAddressMapper.list(address);
    }
}
