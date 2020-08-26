package com.bosha.dapp.server.service.impl;

import java.util.List;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.service.PushService;
import com.bosha.user.api.dto.QueryCreditScoreRangeDto;
import com.bosha.user.api.service.CreditScoreService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: CommonService
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 9:45
 */
@Service
@Slf4j
public class CommonService {

    @Autowired
    private PushService pushService;
    @Autowired
    private CreditScoreService creditScoreService;

    public void pushByScoreRange(Integer start, Integer end, PushMessageDetail detail) {
        QueryCreditScoreRangeDto queryCreditScoreRangeDto = new QueryCreditScoreRangeDto();
        queryCreditScoreRangeDto.setMin(start);
        queryCreditScoreRangeDto.setMax(end);
        queryCreditScoreRangeDto.setPage(1);
        queryCreditScoreRangeDto.setSize(500);
        PageInfo<String> pageInfo = creditScoreService.listByScore(queryCreditScoreRangeDto);
        push(pageInfo.getList(), detail, 1);
        for (int i = 2; i <= pageInfo.getPages(); i++) {
            queryCreditScoreRangeDto.setPage(i);
            PageInfo<String> tmp = creditScoreService.listByScore(queryCreditScoreRangeDto);
            push(tmp.getList(), detail, i);
        }
    }

    private void push(List<String> addresses, PushMessageDetail detail, int batch) {
        if (CollectionUtils.isEmpty(addresses))
            return;
        detail.setAddresses(addresses);
        pushService.send(detail);
        log.info("【{}】发送邀请推送，batch={}，size={}", detail.getTitle(), batch, addresses.size());
    }
}
