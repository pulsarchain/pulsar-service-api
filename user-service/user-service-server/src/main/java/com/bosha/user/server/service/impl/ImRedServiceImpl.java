package com.bosha.user.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.exception.BaseException;
import com.bosha.user.api.dto.*;
import com.bosha.user.api.entity.ImRed;
import com.bosha.user.api.entity.ImRedReceive;
import com.bosha.user.api.service.ImRedService;
import com.bosha.user.server.mapper.ImRedMapper;
import com.bosha.user.server.mapper.ImRedReceiveMapper;
import com.bosha.user.server.utils.Arith;
import com.bosha.user.server.utils.DateUtils;
import com.bosha.user.server.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.bosha.user.api.constants.UserServiceConstants.IM_RED_TOPIC;
import static com.bosha.user.api.enums.UserErrorMessageEnum.*;

@Slf4j
@RestController
public class ImRedServiceImpl implements ImRedService {
    @Autowired
    private ImRedMapper imRedMapper;
    @Autowired
    private ImRedReceiveMapper imRedReceiveMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImRed add(ImRedDto imRedDto) {
        Integer count = imRedMapper.selectByStatus(imRedDto.getUserAddress());
        if (count >= 1) {
            throw new BaseException(IM_RED_HAS_NOT_PAY_ERROR);
        }
        ImRed imRed = new ImRed();
        BeanUtils.copyProperties(imRedDto, imRed);
        imRedMapper.insertSelective(imRed);
        return imRed;
    }

    @Override
    @RedissonDistributedLock(key = "#id")
    public void join(Long id, String address) {
        ImRed imRed = imRedMapper.selectByPrimaryKey(id);
        if (!StringUtils.isEmpty(imRed)) {
            throw new BaseException(IM_RED_NOT_FOUND);
        }
        //如果状态不为待加入，就表示系统异常
        if (!imRed.getStatus().equals(1)) {
            throw new BaseException(IM_RED_STATUS_ERROR);
        }
        //你已经加入无需再次加入
        ImRedReceive imRedReceive = imRedReceiveMapper.selectByRedIdAndAddress(id, address);
        if (!StringUtils.isEmpty(imRedReceive)) {
            throw new BaseException(IM_RED_IS_JOIN_ERROR);
        }
        Integer count = imRedReceiveMapper.selectCountByRedId(id);
        if (count == imRed.getNumber()) {
            throw new BaseException(IM_RED_PEOPLE_NUMBER_ERROR);
        }
        imRedReceive = ImRedReceive.builder().imRedId(id).address(address).build();
        //如果是个人
        if (imRed.getType().equals(1)) {
            imRedReceive.setMoney(imRed.getMoney());
            //如果为固定
        } else if (imRed.getType().equals(3)) {
            imRedReceive.setMoney(imRed.getMoney());
            //如果为手气红包
        } else if (imRed.getType().equals(2)) {
            //获取加入的人数，和已分配币数
            ImRedResDto imRedResDto = imRedReceiveMapper.selectPeopleAndNumber(id);
            if (StringUtils.isEmpty(imRedResDto)) {
                imRedResDto = new ImRedResDto();
            }
            Integer surplusPeople = imRed.getNumber() - imRedResDto.getNumber();
            log.info("已挖矿人数，{}，已挖完的币数：{}", imRedResDto.getNumber(), imRedResDto.getAmount());
            BigDecimal surplusAmount = Arith.sub(imRed.getMoney(), imRedResDto.getAmount());
            //如果是最后一个用户，就将币全部给该用户，并且修改状态为结束
            BigDecimal minerAmount;
            if (surplusPeople == 1) {
                minerAmount = surplusAmount;
            } else {
                //通过剩余人数，和币数算出需要分配的币数
                minerAmount = RandomUtils.getRandomNumber(surplusAmount, surplusPeople);
            }
            imRedReceive.setMoney(minerAmount);
        }
        imRedReceiveMapper.insertSelective(imRedReceive);
        count = imRedReceiveMapper.selectCountByRedId(id);
        //如果这是最后一个人加入，就发送消息给用户叫用户发红包，并修改红包状态为待支付

        if (count == imRed.getNumber()) {
            log.info("这是最后一个加入的人");
            //修改状态
            imRed = ImRed.builder().id(id).status(2).build();
            imRedMapper.updateByPrimaryKeySelective(imRed);
            log.info("这是最后一个人,修改红包的状态：{}", id);
        }
    }

    @Override
    public ImRedDetailDto findById(Long id, String userAddress) {
        ImRedDetailDto imRedDetailDto = imRedMapper.selectById(id, userAddress);
        List<ImRedDetailReceiveDto> list = imRedReceiveMapper.selectRedReceiveByReadId(id);
        imRedDetailDto.setImRedDetailReceiveDtos(list);
        return imRedDetailDto;
    }

    @Override
    public ImRedMyDetailDto findMySendRed(String userAddress, Integer year) {
        Date beginTime = DateUtils.getBeginTime(year);
        Date endTime = DateUtils.getEndTime(year);
        ImRedMyDetailDto imRedMyDetailDto = imRedMapper.findMySendRed(userAddress, beginTime, endTime);
        if (imRedMyDetailDto != null) {
            List<ImMySendRedDto> list = imRedMapper.findMySendRedList(userAddress, beginTime, endTime);
            imRedMyDetailDto.setImMySendRedDtos(list);
        }
        return imRedMyDetailDto;
    }

    @Override
    public ImRedMyDetailDto findMyReceiveRed(String userAddress, Integer year) {
        Date startTime = DateUtils.getBeginTime(year);
        Date endTime = DateUtils.getEndTime(year);
        ImRedMyDetailDto imRedMyDetailDto = imRedReceiveMapper.findMyReceiveRed(userAddress, startTime, endTime);
        if (imRedMyDetailDto != null) {
            List<ImMyReceiveRedDto> list = imRedReceiveMapper.findMyReceiveRedList(userAddress, startTime, endTime);
            imRedMyDetailDto.setImMyReceiveRedDtos(list);
        }
        return imRedMyDetailDto;
    }

    @Override
    public List<ImRedList> findMyPaidRed(String userAddress) {
        List<ImRedList> imRedLists = imRedMapper.findMyPaidRed(userAddress);
        for (ImRedList imRedList : imRedLists) {
            List<ImRedSendMessage> list = imRedReceiveMapper.selectByReadId(imRedList.getId());
            imRedList.setImRedSendMessages(list);
        }
        return imRedLists;
    }

    @Override
    public void updateRed(Long id) {
        ImRed imRed = new ImRed();
        imRed.setId(id);
        imRed.setStatus(3);
        imRedMapper.updateByPrimaryKeySelective(imRed);
    }

    @Override
    public void updateRedReceive(Long redId, String address, String hash) {
        ImRedReceive imRedReceive = new ImRedReceive();
        imRedReceive.setImRedId(redId);
        imRedReceive.setStatus(2);
        imRedReceive.setAddress(address);
        imRedReceive.setHash(hash);
        imRedReceiveMapper.updateByRedId(imRedReceive);
    }
}
