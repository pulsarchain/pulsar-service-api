package com.bosha.dapp.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.dto.FundationDetailDto;
import com.bosha.dapp.api.dto.FundationListDto;
import com.bosha.dapp.api.entity.SparksFundation;
import com.github.pagehelper.PageInfo;

public interface FundationService {

    Long add(SparksFundation fundation);

    boolean updateHash(SparksFundation fundation);

    PageInfo<FundationListDto> list(Page page);

    FundationDetailDto detail(Long id);

    PageInfo<FundationListDto> my(Page page, String address);
}
