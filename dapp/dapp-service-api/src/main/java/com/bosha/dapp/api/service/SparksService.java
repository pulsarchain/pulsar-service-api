package com.bosha.dapp.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.dto.SparksStarDetailDto;
import com.bosha.dapp.api.dto.SparksStarIndexDto;
import com.bosha.dapp.api.dto.SparksStarListDto;
import com.bosha.dapp.api.dto.SparksStarListQuery;
import com.bosha.dapp.api.entity.SparksStar;
import com.github.pagehelper.PageInfo;

public interface SparksService {

    Long add(SparksStar star);

    boolean update(SparksStar star);

    PageInfo<SparksStarListDto> list(SparksStarListQuery query);

    PageInfo<SparksStarListDto> myList(Page page, String address);

    SparksStarDetailDto detail(Long id);

    SparksStarIndexDto index();
}
