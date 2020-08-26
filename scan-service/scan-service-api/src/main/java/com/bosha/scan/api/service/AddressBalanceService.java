package com.bosha.scan.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.scan.api.dto.AddressBalanceInfoDto;
import com.bosha.scan.api.dto.AddressStatisticsInfo;
import com.bosha.scan.api.entity.AddressBalance;
import com.github.pagehelper.PageInfo;

public interface AddressBalanceService {

    PageInfo<AddressBalance> list(Page page);

    AddressBalanceInfoDto info();

    boolean insert(AddressBalance addressBalance);

    boolean isContract(String address);

    boolean isExist(String address);

}
