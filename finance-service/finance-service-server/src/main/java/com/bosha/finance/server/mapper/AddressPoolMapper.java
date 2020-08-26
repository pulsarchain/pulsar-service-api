package com.bosha.finance.server.mapper;

import com.bosha.finance.api.dto.response.AddressPoolDetailDto;
import com.bosha.finance.api.entity.AddressPool;

import java.util.List;

public interface AddressPoolMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddressPool record);

    int insertSelective(AddressPool record);

    AddressPool selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AddressPool record);

    int updateByPrimaryKey(AddressPool record);

    List<AddressPoolDetailDto> selectByCoinId(Long id);

    AddressPool selectByCoinIdAndStatus(Long coinId);

}