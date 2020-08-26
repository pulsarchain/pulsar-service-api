package com.bosha.dapp.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.dto.DonateBuyDto;
import com.bosha.dapp.api.dto.DonateDetailDto;
import com.bosha.dapp.api.dto.DonateListDto;
import com.bosha.dapp.api.dto.DonateQuery;
import com.bosha.dapp.api.entity.SparksDonate;
import com.github.pagehelper.PageInfo;

public interface DonateService {

    Long add(SparksDonate donate);

    boolean updateHash(SparksDonate donate);

    void success(String address, DonateBuyDto donateBuyDto);

    PageInfo<DonateListDto> list(DonateQuery query);

    DonateDetailDto detail(Long id);

    void notice(String address, Long id);

    PageInfo<DonateListDto> my(Page page);

    PageInfo<DonateListDto> myBuy(Page page);

    PageInfo<DonateListDto> myFavorite(Page page);
}
