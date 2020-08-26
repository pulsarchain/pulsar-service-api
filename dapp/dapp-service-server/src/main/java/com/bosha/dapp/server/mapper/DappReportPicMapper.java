package com.bosha.dapp.server.mapper;

import java.util.List;


import com.bosha.dapp.api.entity.DappReportPic;
import org.apache.ibatis.annotations.Param;

public interface DappReportPicMapper {
    int insertSelective(DappReportPic record);

    int batchInsert(@Param("list") List<DappReportPic> list);

    List<String> list(@Param("reportId") Long reportId);
}