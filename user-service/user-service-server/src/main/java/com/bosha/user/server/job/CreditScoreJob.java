package com.bosha.user.server.job;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.user.api.entity.CreditScore;
import com.bosha.user.api.service.UserService;
import com.bosha.user.server.config.UserServiceConfig;
import com.bosha.user.server.mapper.CreditScoreMapper;
import com.bosha.user.server.mapper.UserMapper;
import com.bosha.user.server.redis.CacheKey;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: CreditScoreJob
 * @Author liqingping
 * @Date 2020-02-09 17:00
 */
@Service
@Slf4j
public class CreditScoreJob {

    @Autowired
    private CreditScoreMapper creditScoreMapper;
    @Autowired
    private RedissonClient redis;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceConfig userServiceConfig;
    @Autowired
    private UserService userService;

    @XxlJob("calculateCreditScoreJob")//每天凌晨0点开始
    public ReturnT<String> calculate(String s) throws Exception {
        int per = 30;
        if (StringUtils.isNumeric(s)) {
            per = Integer.parseInt(s);
        }
        int num = userMapper.count();
        int time = num % per == 0 ? num / per : ((num / per) + 1);
        log.info("【计算信用分】开始，per={}，count={}，time={}", per, num, time);
        for (int i = 1; i <= time; i++) {
            List<String> addresses = userMapper.addresses((i - 1) * per, per);
            Map<String, Object> map = new HashMap<>();
            map.put("address", String.join(",", addresses));//逗号分隔，
            map.put("startTime", DateUtil.offsetDay(new Date(), -userServiceConfig.getCreditScore().getCalcDays()));
            map.put("endTime", DateUtil.now());
            int finalI = i;
            int size = addresses.size();
            GlobalExecutorService.executorService.execute(() -> {
                try {
                    String str = "第 " + finalI + " 批";
                    log.info("【计算信用分】开始分批次计算，{}，{}", str, size);
                    StopWatch stopWatch = new StopWatch(str);
                    stopWatch.start();
                    String result = HttpUtil.post(userServiceConfig.getExploerApi().getCreditScoreData(), map, 30 * 1000);
                    JSONObject jsonObject = JSON.parseObject(result);
                    CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>(addresses);
                    cowList.parallelStream().forEach(addr -> {
                        JSONObject js = jsonObject.getJSONObject(addr);
                        BigDecimal amount = js.getBigDecimal("amount");
                        Integer count = js.getInteger("count");
                        Integer days = js.getInteger("days");
                        // 计算公式：
                        //（100天内的转账天数/100）*30%+（100天内总转账笔数/1000）*30%+（100天总转账数额/100000）*40%
                        BigDecimal transferDays = BigDecimal.valueOf(days).multiply(BigDecimal.valueOf(0.3333));
                        BigDecimal transferTotal = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(1000), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(BigDecimal.valueOf(0.3333));
                        BigDecimal amountTotal = amount.divide(BigDecimal.valueOf(10 * 10000), 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(BigDecimal.valueOf(0.4)).setScale(4, BigDecimal.ROUND_HALF_UP);
                        int score = transferDays.add(transferTotal).add(amountTotal).intValue();
                        if (score <= 0)
                            score = 0;
                        if (score >= 100)
                            score = 100;
                        CreditScore cs = creditScoreMapper.getByAddress(addr);
                        if (cs == null) {
                            if (userService.getByAddress(addr) == null)
                                return;
                            cs = CreditScore.builder().address(addr).createTime(new Date()).updateTime(new Date()).score(score).build();
                            creditScoreMapper.insertSelective(cs);
                            return;
                        }
                        cs.setUpdateTime(new Date());
                        cs.setScore(score);
                        creditScoreMapper.updateByPrimaryKeySelective(cs);
                        RMapCache<String, CreditScore> mapCache = redis.getMapCache(CacheKey.User.CREDIT_SCORE_MAP.getKey());
                        if (!mapCache.isExists())
                            return;
                        mapCache.remove(addr);
                    });
                    stopWatch.stop();
                    log.info("【计算信用分】结束分批计算，{}，耗时：{} s ", str, stopWatch.getTotalTimeSeconds());
                } catch (Exception e) {
                    log.error("每日计算信用分失败：", e);
                }
            });
        }
        return ReturnT.SUCCESS;
    }

}
