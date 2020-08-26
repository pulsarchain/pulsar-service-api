package com.bosha.common.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bosha.common.api.dto.MessageRequestDto;
import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.dto.PushMessageExtra;
import com.bosha.common.api.entity.PushConfig;
import com.bosha.common.api.entity.PushMessage;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushConfigService;
import com.bosha.common.api.service.PushService;
import com.bosha.common.server.config.AliYunPushConfig;
import com.bosha.common.server.config.CommonServiceConfig;
import com.bosha.common.server.mapper.PushConfigMapper;
import com.bosha.common.server.mapper.PushMessageMapper;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: PushServiceImpl
 * @Author liqingping
 * @Date 2019-12-12 11:15
 */
@RestController
@Slf4j
public class PushServiceImpl implements PushService {

    @Autowired
    private PushMessageMapper pushMessageMapper;
    @Autowired
    private AliYunPushConfig pushConfig;
    @Autowired
    private PushConfigService pushConfigService;
    @Autowired
    private PushConfigMapper pushConfigMapper;
    @Autowired
    private CommonServiceConfig commonServiceConfig;
    @Autowired
    private UserService userService;


    @Override
    //@Transactional(rollbackFor = Exception.class)
    public void send(@RequestBody @Validated PushMessageDetail pmd) {
        if (CollectionUtils.isEmpty(pmd.getAddresses()))
            throw new RuntimeException("地址列表不可为空");
        List<String> ids = pmd.getAddresses();//new ArrayList<>(new HashSet<>(pmd.getAddresses()));
        String title = pmd.getTitle();
        AliyunPushEnum pushEnum = pmd.getPushEnum();
        PushMessage pm = PushMessage.builder()
                .title(title)
                .status(0)
                .createTime(new Date())
                .pushType(pushEnum.name())
                .body(pmd.getContent())
                .extParameters(JSON.toJSONString(pmd.getData(), SerializerFeature.WriteMapNullValue))
                .type(pmd.getType().name())
                .subType(pmd.getSubType())
                .build();
        GlobalExecutorService.executorService.execute(() -> {
            if (!Boolean.FALSE.equals(pmd.getDb()))
                pushMessageMapper.batchInsert(pm, ids);
            ids.removeIf(id -> isRemove(pmd.getType(), id));
            if (CollectionUtils.isEmpty(ids))
                return;
            pushConfig.push(StringUtils.isBlank(pmd.getPushTitle()) ? title : pmd.getPushTitle(),
                    pm.getBody(),
                    JSON.toJSONString(PushMessageExtra.builder().subType(pmd.getSubType()).type(pmd.getType())
                            .extras(pmd.getData()).build(), SerializerFeature.WriteMapNullValue), ids, pushEnum);
        });
    }

    @Override
    public void sendToAll(@RequestBody PushMessageDetail pmd) {
        GlobalExecutorService.executorService.execute(() -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Date date = new Date();
            String title = pmd.getTitle();
            AliyunPushEnum pushEnum = pmd.getPushEnum();
            PushMessage pm = PushMessage.builder()
                    .title(title)
                    .status(0)
                    .createTime(date)
                    .pushType(pushEnum.name())
                    .body(pmd.getContent())
                    .extParameters(JSON.toJSONString(pmd.getData(), SerializerFeature.WriteMapNullValue))
                    .type(pmd.getType().name())
                    .subType(pmd.getSubType())
                    .build();
            int size = commonServiceConfig.getPushBatchSize();
            int count = pushConfigMapper.countUser();
            int time = count % size == 0 ? count / size : (count / size) + 1;
            for (int i = 1; i <= time; i++) {
                List<PushConfig> configs = pushConfigMapper.listUserAddress((i - 1) * size, size);
                if (CollectionUtils.isEmpty(configs))
                    return;
                if (!Boolean.FALSE.equals(pmd.getDb()))
                    pushMessageMapper.batchInsert(pm, configs.stream().map(PushConfig::getAddress).collect(Collectors.toList()));
                GlobalExecutorService.executorService.execute(() -> {
                    List<String> list = configs.stream().filter(pc -> !isRemove(pmd.getType(), pc)).map(PushConfig::getAddress).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(list))
                        return;
                    pushConfig.push(StringUtils.isBlank(pmd.getPushTitle()) ? title : pmd.getPushTitle(), pm.getBody(),
                            JSON.toJSONString(PushMessageExtra.builder().subType(pmd.getSubType()).type(pmd.getType()).extras(pmd.getData()).build(),
                                    SerializerFeature.WriteMapNullValue), list, pushEnum);
                });
            }
            stopWatch.stop();
            if (Boolean.TRUE.equals(pmd.getDb()))
                log.info("给所有用户发送通知--->新增记录count={}，耗时：{} ms", count, stopWatch.getTotalTimeMillis());
        });
    }

    @Override
    public Object getById(@PathVariable("id") Long id) {
        return pushMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(@RequestBody PushMessage pushMessage) {
        pushMessageMapper.updateByPrimaryKeySelective(pushMessage);
    }

    @Override
    public PageInfo<PushMessage> list(@ModelAttribute @Validated MessageRequestDto messageRequest) {
        PageHelper.startPage(messageRequest.getPage(), messageRequest.getSize());
        return PageInfo.of(pushMessageMapper.list(RequestContextUtils.getAddress(), messageRequest));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean read(@RequestParam(value = "id", required = false) Long id) {
        return pushMessageMapper.updateStatus(RequestContextUtils.getAddress(), id, new Date()) > 0;
    }

    @Override
    public int unreadCount(@RequestParam("address") String address) {
        return pushMessageMapper.unreadCount(address);
    }

    @Override
    public List<PushMessage> listByType(@RequestParam("address") String address, @RequestParam("type") PushMessageTypeEnum type, @RequestParam("subType") String subType) {
        return pushMessageMapper.listByType(address, type.name(), subType);
    }

    private boolean isRemove(PushMessageTypeEnum pushMessageTypeEnum, String address) {
        PushConfig pc = pushConfigService.getByAddress(address);
        return isRemove(pushMessageTypeEnum, pc);
    }

    private boolean isRemove(PushMessageTypeEnum pushMessageTypeEnum, PushConfig pc) {
        if (pc == null)
            return true;
        if (!pc.getAll()) {
            return true;
        }
        switch (pushMessageTypeEnum) {
            case SYSTEM:
                return !pc.getSystem();
            case STAR:
                return !pc.getStar();
            case CHAIN:
                return !pc.getChain();
            case PRICE:
                return !pc.getPrice();
            case DAPP:
                return !pc.getDapp();
            default:
                return false;
        }
    }

}
