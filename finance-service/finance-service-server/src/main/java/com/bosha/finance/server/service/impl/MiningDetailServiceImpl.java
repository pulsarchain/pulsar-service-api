package com.bosha.finance.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import com.bosha.finance.api.dto.request.MiningDetailDto;
import com.bosha.finance.api.dto.response.MiningDetailListDto;
import com.bosha.finance.api.dto.response.MiningDetailStatisticsDto;
import com.bosha.finance.api.entity.MiningDetail;
import com.bosha.finance.api.service.MiningDetailService;
import com.bosha.finance.server.mapper.MiningDetailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MiningDetailServiceImpl implements MiningDetailService {
    @Autowired
    private MiningDetailMapper miningDetailMapper;

    @Override
    public MiningDetailStatisticsDto findMiningDetail(Long userId) {
        MiningDetailStatisticsDto miningDetailStatisticsDto = new MiningDetailStatisticsDto();
        BigDecimal toDayMoney = miningDetailMapper.selectToDayMoney(userId);
        BigDecimal totalMoney = miningDetailMapper.selectTotalMoney(userId);
        miningDetailStatisticsDto.setTodayMoney(toDayMoney);
        miningDetailStatisticsDto.setTotalMoney(totalMoney);
        return miningDetailStatisticsDto;
    }

    @Override
    public PageInfo<MiningDetailListDto> findMiningDetailPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                              @RequestParam("userId") Long userId) {
        PageHelper.startPage(page, size);
        List<MiningDetailListDto> miningDetailListDtoList = miningDetailMapper.findMiningDetailPage(userId);
        return PageInfo.of(miningDetailListDtoList);
    }

    @Override
    public Long insertMiningDetail(@RequestBody MiningDetailDto miningDetailDto) {
        MiningDetail miningDetail = new MiningDetail();
        BeanUtils.copyProperties(miningDetailDto, miningDetail);
        miningDetail.setServiceType(miningDetailDto.getServiceType().getType());
        miningDetail.setCreateTime(new Date());
        miningDetailMapper.insertSelective(miningDetail);
        return miningDetail.getId();
    }

    @Override
    public void updateMiningDetail(@RequestBody MiningDetailDto miningDetailDto) {
        MiningDetail miningDetail = new MiningDetail();
        BeanUtils.copyProperties(miningDetailDto, miningDetail);
        miningDetailMapper.updateByPrimaryKeySelective(miningDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public MiningDetail update(@RequestBody MiningDetail miningDetail) {
        miningDetailMapper.updateByPrimaryKeySelective(miningDetail);
        return miningDetailMapper.selectByPrimaryKey(miningDetail.getId());
    }
}
