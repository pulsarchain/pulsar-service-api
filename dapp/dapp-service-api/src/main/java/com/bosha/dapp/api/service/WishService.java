package com.bosha.dapp.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.dto.WishDetailDto;
import com.bosha.dapp.api.dto.WishListDto;
import com.bosha.dapp.api.entity.SparksWish;
import com.github.pagehelper.PageInfo;

public interface WishService {

    Long add(SparksWish wish);

    PageInfo<WishListDto> list(Page page);

    WishDetailDto detail(Long id);

    PageInfo<WishListDto> my(String address, Page page);
}
