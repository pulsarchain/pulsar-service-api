package com.bosha.dapp.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.dto.WishDetailDto;
import com.bosha.dapp.api.dto.WishListDto;
import com.bosha.dapp.api.entity.SparksWish;
import com.bosha.dapp.api.entity.SparksWishImg;
import com.bosha.dapp.api.service.WishService;
import com.bosha.dapp.server.mapper.SparksWishImgMapper;
import com.bosha.dapp.server.mapper.SparksWishMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WishServiceImpl
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-31 10:46
 */
@Service
@Slf4j
public class WishServiceImpl implements WishService {

    @Autowired
    private SparksWishMapper wishMapper;
    @Autowired
    private SparksWishImgMapper wishImgMapper;

    @Override
    public Long add(SparksWish wish) {
        wish.setCreateTime(new Date());
        wishMapper.insertSelective(wish);
        List<SparksWishImg> list = new ArrayList<>();
        wish.getImgs().forEach(s -> list.add(SparksWishImg.builder().url(s).wishId(wish.getId()).build()));
        wishImgMapper.batchInsert(list);
        log.info("【心愿清单】添加了心愿清单：{}", wish);
        return wish.getId();
    }

    @Override
    public PageInfo<WishListDto> list(Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(wishMapper.list());
    }

    @Override
    public WishDetailDto detail(Long id) {
        return wishMapper.detail(id);
    }

    @Override
    public PageInfo<WishListDto> my(String address, Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(wishMapper.my(address));
    }
}
