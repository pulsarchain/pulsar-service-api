package com.bosha.user.server.service.impl;

import com.bosha.common.api.service.PushService;
import com.bosha.commons.dto.Page;
import com.bosha.user.api.dto.ContractCoinDto;
import com.bosha.user.api.dto.ContractCoinListDto;
import com.bosha.user.api.entity.ContractCoin;
import com.bosha.user.api.service.ContractCoinService;
import com.bosha.user.server.mapper.ContractCoinMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContractCoinServiceImpl implements ContractCoinService {
    @Autowired
    private ContractCoinMapper contractCoinMapper;


    @Override
    public void save(@RequestBody ContractCoinDto contractCoinDto) {
        ContractCoin contractCoin = new ContractCoin();
        BeanUtils.copyProperties(contractCoinDto, contractCoin);
        contractCoinMapper.insertSelective(contractCoin);
    }

    @Override
    public PageInfo<ContractCoinListDto> list(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        List<ContractCoinListDto> contractCoinListDtoList = contractCoinMapper.list();
        return PageInfo.of(contractCoinListDtoList);
    }

    @Override
    public void bindContract(@RequestParam("hash") String hash,@RequestParam("contractAddress")  String contractAddress) {
        ContractCoin contractCoin = contractCoinMapper.selectByHash(hash);
        if (StringUtils.isEmpty(contractCoin)) {
            return;
        }
        contractCoin.setContractAddress(contractAddress);
        contractCoin.setStatus(2);
        contractCoinMapper.updateByPrimaryKeySelective(contractCoin);
    }
}
