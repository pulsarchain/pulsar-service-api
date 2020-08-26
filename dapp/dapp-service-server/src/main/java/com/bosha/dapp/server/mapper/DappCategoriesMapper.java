package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.dto.DappCategoriesDto;
import com.bosha.dapp.api.entity.DappCategories;
import org.apache.ibatis.annotations.Param;

public interface DappCategoriesMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DappCategories record);

    DappCategories selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DappCategories record);

    int updateBatchSelective(List<DappCategories> list);

    int batchInsert(@Param("list") List<DappCategories> list);

    List<DappCategoriesDto> list();

}