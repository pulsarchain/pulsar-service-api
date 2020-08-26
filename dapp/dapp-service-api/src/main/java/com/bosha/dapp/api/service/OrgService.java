package com.bosha.dapp.api.service;

import com.bosha.commons.dto.Page;
import com.bosha.dapp.api.dto.OrgDetailDto;
import com.bosha.dapp.api.dto.OrgListDto;
import com.bosha.dapp.api.dto.OrgQuery;
import com.bosha.dapp.api.entity.SparksOrg;
import com.github.pagehelper.PageInfo;

public interface OrgService {

    Long add(SparksOrg org);

    PageInfo<OrgListDto> list(OrgQuery query);

    boolean follow(String address, Long orgId);

    boolean unFollow(String address, Long orgId);

    OrgDetailDto detail(Long id);

    PageInfo<OrgListDto> my(Page page);

    PageInfo<OrgListDto> myFollow(Page page);
}
