package com.bosha.common.server.service.impl;

import com.bosha.common.api.entity.InviteFriendConfig;
import com.bosha.common.api.entity.LiveMiningConfig;
import com.bosha.common.api.entity.StarConfig;
import com.bosha.common.api.entity.SystemConfig;
import com.bosha.common.api.service.SystemConfigService;
import com.bosha.common.server.mapper.InviteFriendConfigMapper;
import com.bosha.common.server.mapper.LiveMiningConfigMapper;
import com.bosha.common.server.mapper.StarConfigMapper;
import com.bosha.common.server.redis.CacheKey;
import com.bosha.commons.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SystemConfigServiceImpl
 * @Author liqingping
 * @Date 2019-12-13 13:45
 */
@RestController
@Slf4j
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private InviteFriendConfigMapper inviteFriendConfigMapper;
    @Autowired
    private StarConfigMapper starConfigMapper;
    @Autowired
    private LiveMiningConfigMapper liveMiningConfigMapper;
    @Autowired
    private RedissonClient redis;

    @Override
    public SystemConfig getSystemConfig(@RequestParam(value = "coinId", required = false) Long coinId) {
        InviteFriendConfig inviteFriendConfig = inviteFriendConfigMapper.getByCoinId(coinId);
        StarConfig starConfig = starConfigMapper.getByCoinId(coinId);
        LiveMiningConfig liveMiningConfig = liveMiningConfigMapper.getByCoinId(coinId);
        return SystemConfig.builder().inviteFriendConfig(inviteFriendConfig).starConfig(starConfig).liveMiningConfig(liveMiningConfig).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean inviteFriend(@RequestBody InviteFriendConfig inviteFriendConfig) {
        return inviteFriendConfigMapper.updateByPrimaryKeySelective(inviteFriendConfig) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean star(@RequestBody StarConfig starConfig) {
        StarConfig db = starConfigMapper.selectByPrimaryKey(starConfig.getId());
        if (db == null)
            throw new BaseException("记录不存在 id=" + starConfig.getId());
        boolean success = true;
        if (starConfig.getInitialValue() != null && !starConfig.getInitialValue().equals(db.getInitialValue())) {
            db.setInitialValue(starConfig.getInitialValue());
            success = starConfigMapper.updateByPrimaryKeySelective(db) > 0;
        }
        boolean fixedStar = starConfig.getFixedStar() == null || db.getFixedStar().equals(starConfig.getFixedStar());
        boolean giantStar = starConfig.getGiantStar() == null || db.getGiantStar().equals(starConfig.getGiantStar());
        boolean superGiantStar = starConfig.getSuperGiantStar() == null || db.getSuperGiantStar().equals(starConfig.getSuperGiantStar());
        if (fixedStar && giantStar && superGiantStar) {
            return success;
        }

        RBucket<StarConfig> bucket = redis.getBucket(CacheKey.Common.STAR_CONFIG_BUCKET.key);
        if (bucket.isExists())
            throw new BaseException("投入比例每天仅可修改一次");
        starConfig.setInitialValue(null);
        bucket.set(starConfig);
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean liveMining(@RequestBody LiveMiningConfig liveMiningConfig) {
        return liveMiningConfigMapper.updateByPrimaryKeySelective(liveMiningConfig) > 0;
    }
}
