package com.bosha.dapp.server.mapper;

import java.util.Date;
import java.util.List;


import com.bosha.dapp.api.dto.DappReportDetailDto;
import com.bosha.dapp.api.dto.DappReportPublicListDto;
import com.bosha.dapp.api.dto.MyDappReportListDto;
import com.bosha.dapp.api.entity.DappReport;
import org.apache.ibatis.annotations.Param;

public interface DappReportMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(DappReport record);

    DappReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DappReport record);

    int batchInsert(@Param("list") List<DappReport> list);

    int countByDappId(@Param("id") Long id);

    List<MyDappReportListDto> listMyRecord(@Param("address") String address);

    DappReportDetailDto detail(@Param("id") Long id);

    List<DappReportPublicListDto> publicList(@Param("address") String address);

    DappReportPublicListDto publicDetail(@Param("address") String address, @Param("id") Long id);

    List<DappReport> publicDateJob(@Param("date") Date date);

    List<DappReport> modifyList(@Param("date") Date date);

    int countExist(@Param("address") String address, @Param("id") Long id);
}