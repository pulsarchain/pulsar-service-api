package com.bosha.finance.server.service.impl;

import java.math.BigDecimal;


import com.bosha.finance.api.dto.response.MyEarningsDto;
import com.bosha.finance.api.service.MyEarningsService;
import com.bosha.finance.server.mapper.ContractMiningDetailMapper;
import com.bosha.finance.server.mapper.MiningDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MyEarningsServiceImpl
 * @Author liqingping
 * @Date 2020-04-15 8:51
 */
@RestController
@Slf4j
public class MyEarningsServiceImpl implements MyEarningsService {

    @Autowired
    private ContractMiningDetailMapper contractMiningDetailMapper;
    @Autowired
    private MiningDetailMapper miningDetailMapper;

    @Override
    public MyEarningsDto myEarnings(@RequestParam("address") String address) {
        MyEarningsDto myEarnings = contractMiningDetailMapper.myEarnings(address);
        myEarnings.setOther(miningDetailMapper.myEarnings(address));
        BigDecimal total = myEarnings.getCommissionedMining().add(myEarnings.getLive()).add(myEarnings.getNews()).add(myEarnings.getOther()).add(myEarnings.getStar());
        if (total.compareTo(BigDecimal.ZERO) == 0)
            return myEarnings;

        BigDecimal star = myEarnings.getStar().divide(total, 4, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).setScale(2);
        BigDecimal live = myEarnings.getLive().divide(total, 4, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).setScale(2);
        BigDecimal news = myEarnings.getNews().divide(total, 4, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).setScale(2);
        BigDecimal other = myEarnings.getOther().divide(total, 4, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).setScale(2);

        myEarnings.setStarPercent(star);
        myEarnings.setLivePercent(live);
        myEarnings.setNewsPercent(news);
        myEarnings.setOtherPercent(other);

        BigDecimal add = star.add(live).add(news).add(other);
        if (add.compareTo(BigDecimal.valueOf(100)) != 0) {
            if (myEarnings.getOther().compareTo(BigDecimal.ZERO)>0){
                myEarnings.setOtherPercent(other.add(BigDecimal.valueOf(100).subtract(add)));
            }else if (myEarnings.getStar().compareTo(BigDecimal.ZERO)>0){
                myEarnings.setStarPercent(star.add(BigDecimal.valueOf(100).subtract(add)));
            }else if (myEarnings.getLive().compareTo(BigDecimal.ZERO)>0){
                myEarnings.setLivePercent(live.add(BigDecimal.valueOf(100).subtract(add)));
            }else if (myEarnings.getNews().compareTo(BigDecimal.ZERO)>0){
                myEarnings.setNewsPercent(news.add(BigDecimal.valueOf(100).subtract(add)));
            }
        }
        return myEarnings;
    }
}
