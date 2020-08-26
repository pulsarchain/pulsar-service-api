package com.bosha.common.server.job;

import com.alibaba.fastjson.JSON;
import com.bosha.common.api.entity.StarConfig;
import com.bosha.common.server.mapper.StarConfigMapper;
import com.bosha.common.server.redis.CacheKey;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: StarConfigJob
 * @Author liqingping
 * @Date 2019-12-16 11:16
 */

@Component
@Slf4j
public class StarConfigJob     {

    @Autowired
    private RedissonClient redis;
    @Autowired
    private StarConfigMapper starConfigMapper;

    @XxlJob("StarConfigJob")//每天凌晨4点
    public ReturnT<String> execute(String s) throws Exception {
        try {
            RBucket<StarConfig> bucket = redis.getBucket(CacheKey.Common.STAR_CONFIG_BUCKET.key);
            if (!bucket.isExists())
                return ReturnT.SUCCESS;
            StarConfig starConfig = bucket.get();
            bucket.delete();
            int i = starConfigMapper.updateByPrimaryKeySelective(starConfig);
            if (i > 0) {
                log.info("修改星系配置成功！starConfig={}", JSON.toJSONString(starConfig));
                return ReturnT.SUCCESS;
            } else {
                log.error("修改星系配置失败！starConfig={}", JSON.toJSONString(starConfig));
                XxlJobLogger.log("修改星系配置失败！starConfig={}", JSON.toJSONString(starConfig));
                return ReturnT.FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.FAIL;
        }
    }
}
