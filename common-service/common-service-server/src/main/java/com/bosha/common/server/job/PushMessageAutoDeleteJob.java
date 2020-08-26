package com.bosha.common.server.job;

import com.bosha.common.server.mapper.PushMessageMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: PushMessageAutoDeleteJob
 * @Author liqingping
 * @Date 2020-03-17 13:47
 */
@Component
@Slf4j
public class PushMessageAutoDeleteJob {

    @Autowired
    private PushMessageMapper pushMessageMapper;

    @XxlJob("PushMessageAutoDeleteJob")//每2min
    public ReturnT<String> execute(String s) throws Exception {
        try {
            int count = pushMessageMapper.deleteBatch();
            if (count > 0)
                log.info("【删除通知消息】删除条数为：{}", count);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnT.FAIL;
        }
    }
}
