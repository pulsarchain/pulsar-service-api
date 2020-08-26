package com.bosha.dapp.api.service;

import java.util.List;


import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.dto.ActivityCalendarDto;
import com.bosha.dapp.api.dto.ActivityDetailDto;
import com.bosha.dapp.api.dto.ActivityListDto;
import com.bosha.dapp.api.dto.ActivityQuery;
import com.bosha.dapp.api.entity.SparksActivity;
import com.bosha.dapp.api.entity.SparksActivityJoin;
import com.github.pagehelper.PageInfo;

public interface ActivityService {

    Long add(SparksActivity activity);

    boolean updateHash(SparksActivity activity);

    PageInfo<ActivityListDto> list(ActivityQuery query);

    PageInfo<ActivityListDto> my(Page page);

    ActivityDetailDto detail(Long id);

    boolean join(SparksActivityJoin join);

    boolean cancel(SparksActivityJoin join);

    PageInfo<ActivityListDto> myFavorite(Integer type, Page page);

    List<ActivityCalendarDto> calendar(String address, String startTime, String endTime);

    PageInfo<ActivityListDto> calendar(String address, String startTime, String endTime, Integer type, Page page);
}
