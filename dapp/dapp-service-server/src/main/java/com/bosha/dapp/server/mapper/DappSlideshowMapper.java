package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.DappSlideshowDto;
import com.bosha.dapp.api.entity.DappSlideshow;

public interface DappSlideshowMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DappSlideshow record);

    DappSlideshow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DappSlideshow record);

    List<DappSlideshowDto> list();
}