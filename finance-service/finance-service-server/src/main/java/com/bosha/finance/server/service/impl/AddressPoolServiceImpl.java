package com.bosha.finance.server.service.impl;

import com.bosha.finance.api.dto.request.CoinDto;
import com.bosha.finance.api.dto.request.QueryCoinDto;
import com.bosha.finance.api.dto.response.AddressPoolDetailDto;
import com.bosha.finance.api.dto.response.AddressPoolListDto;
import com.bosha.finance.api.dto.response.CoinListDto;
import com.bosha.finance.api.entity.Coin;
import com.bosha.finance.api.service.AddressPoolService;
import com.bosha.finance.api.service.CoinService;
import com.bosha.finance.server.mapper.AddressPoolMapper;
import com.bosha.finance.server.mapper.CoinMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class AddressPoolServiceImpl implements AddressPoolService {
    @Autowired
    private AddressPoolMapper addressPoolMapper;
    @Autowired
    private CoinMapper coinMapper;

    @Override
    public List<AddressPoolListDto> findAddressPool() {
        List<AddressPoolListDto> addressPoolListDtos =  coinMapper.findAddressPool();
        return addressPoolListDtos;
    }

    @Override
    public PageInfo<AddressPoolDetailDto> findAddressPoolDetail(Long id, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<AddressPoolDetailDto> addressPoolDetailDto = addressPoolMapper.selectByCoinId(id);
        return  PageInfo.of(addressPoolDetailDto);
    }
}
