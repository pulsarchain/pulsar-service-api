package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.DappReportCategory;
import org.apache.ibatis.annotations.Param;

public interface DappReportCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DappReportCategory record);

    DappReportCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DappReportCategory record);

    int batchInsert(@Param("list") List<DappReportCategory> list);

    List<DappReportCategory> list();
}