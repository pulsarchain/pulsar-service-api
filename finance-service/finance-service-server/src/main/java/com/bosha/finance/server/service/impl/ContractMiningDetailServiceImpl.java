package com.bosha.finance.server.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.bosha.finance.api.dto.request.BatchContractMiningDetailDto;
import com.bosha.finance.api.dto.request.ContractMiningDetailDto;
import com.bosha.finance.api.dto.response.ContractMiningDetailListDto;
import com.bosha.finance.api.entity.ContractMiningDetail;
import com.bosha.finance.api.service.ContractMiningDetailService;
import com.bosha.finance.server.mapper.ContractMiningDetailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ContractMiningDetailServiceImpl implements ContractMiningDetailService {
    @Autowired
    private ContractMiningDetailMapper contractMiningDetailMapper;

    @Override
    public BigDecimal total(String address) {
        return contractMiningDetailMapper.total(address);
    }

    @Override
    public PageInfo<ContractMiningDetailListDto> list(Integer page, Integer size, String address) {
        PageHelper.startPage(page, size);
        List<ContractMiningDetailListDto> contractMiningDetailListDtos = contractMiningDetailMapper.list(address);
        return PageInfo.of(contractMiningDetailListDtos);
    }

    @Override
    public Long insert(ContractMiningDetailDto contractMiningDetailDto) {
        ContractMiningDetail contractMiningDetail = new ContractMiningDetail();
        BeanUtils.copyProperties(contractMiningDetailDto, contractMiningDetail);
        contractMiningDetailMapper.insertSelective(contractMiningDetail);
        return contractMiningDetail.getId();
    }

    @Override
    public Map<String, Long> batchInsert(List<ContractMiningDetail> contractMiningDetailDto) {
        contractMiningDetailMapper.batchInsert(contractMiningDetailDto);
        Map<String, Long> map = new HashMap<>();
        for (ContractMiningDetail contractMiningDetail : contractMiningDetailDto) {
            map.put(contractMiningDetail.getAddress(), contractMiningDetail.getId());
        }
        return map;
    }

    @Override
    public Map<String, Long> batchInsertWithType(List<ContractMiningDetail> contractMiningDetailDto) {
        contractMiningDetailMapper.batchInsert(contractMiningDetailDto);
        Map<String, Long> map = new HashMap<>();
        for (ContractMiningDetail contractMiningDetail : contractMiningDetailDto) {
            map.put(contractMiningDetail.getAddress() + contractMiningDetail.getServiceType(), contractMiningDetail.getId());
        }
        return map;
    }

    @Override
    public boolean batchUpdate(BatchContractMiningDetailDto batchContractMiningDetailDto) {
        return contractMiningDetailMapper.batchUpdate(batchContractMiningDetailDto.getContractMiningDetails(),
                batchContractMiningDetailDto.getContractMiningStatusEnum().getStatus()) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public ContractMiningDetail update(@RequestBody ContractMiningDetail contractMiningDetail) {
        contractMiningDetailMapper.updateByPrimaryKeySelective(contractMiningDetail);
        return contractMiningDetailMapper.selectByPrimaryKey(contractMiningDetail.getId());
    }
}
