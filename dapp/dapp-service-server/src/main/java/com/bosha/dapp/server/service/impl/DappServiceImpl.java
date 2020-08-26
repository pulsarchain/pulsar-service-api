package com.bosha.dapp.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.bosha.common.api.dto.PushMessageDetail;
import com.bosha.common.api.enums.AliyunPushEnum;
import com.bosha.common.api.enums.PushMessageSubTypeEnum;
import com.bosha.common.api.enums.PushMessageTypeEnum;
import com.bosha.common.api.service.PushService;
import com.bosha.commons.annotation.RedissonDistributedLock;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.commons.utils.RequestContextUtils;
import com.bosha.dapp.api.dto.DappCategoriesDto;
import com.bosha.dapp.api.dto.DappDetailDto;
import com.bosha.dapp.api.dto.DappDetailExtra;
import com.bosha.dapp.api.dto.DappListDto;
import com.bosha.dapp.api.dto.DappListQuery;
import com.bosha.dapp.api.dto.DappSlideshowDto;
import com.bosha.dapp.api.dto.DappWitnessDto;
import com.bosha.dapp.api.dto.DappWitnessNoticeDto;
import com.bosha.dapp.api.entity.Dapp;
import com.bosha.dapp.api.entity.DappCategories;
import com.bosha.dapp.api.entity.DappFavorite;
import com.bosha.dapp.api.entity.DappUseRecord;
import com.bosha.dapp.api.entity.DappWitness;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.enums.DappStatusEnum;
import com.bosha.dapp.api.service.DappService;
import com.bosha.dapp.server.mapper.DappCategoriesMapper;
import com.bosha.dapp.server.mapper.DappFavoriteMapper;
import com.bosha.dapp.server.mapper.DappMapper;
import com.bosha.dapp.server.mapper.DappReportMapper;
import com.bosha.dapp.server.mapper.DappSlideshowMapper;
import com.bosha.dapp.server.mapper.DappUseRecordMapper;
import com.bosha.dapp.server.mapper.DappWitnessMapper;
import com.bosha.user.api.entity.User;
import com.bosha.user.api.enums.UserErrorMessageEnum;
import com.bosha.user.api.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappServiceImpl
 * @Author liqingping
 * @Date 2020-05-09 18:53
 */
@RestController
@Slf4j
public class DappServiceImpl implements DappService {

    @Autowired
    private DappCategoriesMapper dappCategoriesMapper;
    @Autowired
    private DappMapper dappMapper;
    @Autowired
    private DappUseRecordMapper dappUseRecordMapper;
    @Autowired
    private DappWitnessMapper dappWitnessMapper;
    @Autowired
    private PushService pushService;
    @Autowired
    private UserService userService;
    @Autowired
    private DappReportMapper dappReportMapper;
    @Autowired
    private DappSlideshowMapper dappSlideshowMapper;
    @Autowired
    private DappFavoriteMapper favoriteMapper;

    @Override
    public List<DappCategoriesDto> categories() {
        return dappCategoriesMapper.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(@RequestBody Dapp dapp) {
        DappCategories dc = dappCategoriesMapper.selectByPrimaryKey(dapp.getCategoryId());
        if (dc == null)
            throw new BaseException("该分类不存在");
        if (dc.getShow() == 0)
            throw new BaseException("该分类暂不可用");
        dapp.setCreateTime(new Date());
        dapp.setUpdateTime(new Date());
        dapp.setAddress(RequestContextUtils.getAddress());
        dapp.setStatus(DappStatusEnum.UNINVITED_WITNESS.getStatus());
        dappMapper.insertSelective(dapp);
        return dapp.getId();
    }

    @Override
    public boolean update(@RequestBody Dapp dapp) {
        Dapp select = dappMapper.selectByPrimaryKey(dapp.getId());
        if (!select.getAddress().equalsIgnoreCase(RequestContextUtils.getAddress()))
            throw new BaseException(DappErrorMsgEnum.NOT_DAPP_OWNER);
        if (select.getStatus() == DappStatusEnum.OFF_LINE.getStatus())
            throw new BaseException("下架状态的dapp不允许修改");
        dapp.setUpdateTime(new Date());
        return dappMapper.updateByPrimaryKeySelective(dapp) > 0;
    }

    @Override
    public boolean updateStatus(@RequestBody Dapp dapp) {
        dapp.setUpdateTime(new Date());
        return dappMapper.updateByPrimaryKeySelective(dapp) > 0;
    }

    @Override
    public PageInfo<Dapp> released(@RequestBody Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(dappMapper.released(RequestContextUtils.getAddress()));
    }

    @Override
    public PageInfo<DappListDto> list(@ModelAttribute DappListQuery query) {
        PageHelper.startPage(query.getPage(), query.getSize());
        return PageInfo.of(dappMapper.list(query));
    }

    @Override
    @RedissonDistributedLock(key = "@address")
    public void useRecord(@RequestParam("dappId") Long dappId) {
        DappUseRecord record = dappUseRecordMapper.selectByPrimaryKey(RequestContextUtils.getAddress(), dappId);
        if (record != null) {
            record.setUpdateTime(new Date());
            dappUseRecordMapper.updateByPrimaryKeySelective(record);
            return;
        }
        dappUseRecordMapper.insertSelective(DappUseRecord.builder().address(RequestContextUtils.getAddress()).dappId(dappId).createTime(new Date()).build());
    }

    @Override
    @RedissonDistributedLock(key = "@address")
    public boolean witness(@RequestBody DappWitnessDto dappWitnessDto) {
        String address = RequestContextUtils.getAddress();
        User user = userService.getByAddress(address);
        if (user == null)
            throw new BaseException(UserErrorMessageEnum.USER_NOT_FOUND);
        Dapp dapp = dappMapper.selectByPrimaryKey(dappWitnessDto.getDappId());
        if (dapp == null || !dapp.getAddress().equalsIgnoreCase(address))
            throw new BaseException("您没有此Dapp");
        List<String> list = new ArrayList<>();
        for (String s : dappWitnessDto.getAddress()) {
            boolean duplicate = dappWitnessMapper.countDuplicate(address, dappWitnessDto.getDappId(), s) > 0;
            if (duplicate)
                continue;
            list.add(s);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(s -> dappWitnessMapper.insertSelective(DappWitness.builder().address(address).auxiliaryAddress(s).createTime(new Date()).dappId(dappWitnessDto.getDappId()).build()));
            if (dapp.getStatus() == DappStatusEnum.UNINVITED_WITNESS.getStatus()) {
                dapp.setStatus(DappStatusEnum.IN_WITNESS.getStatus());
                updateStatus(dapp);
            }
        }
        String nickName = user.getNickName();
        pushService.send(PushMessageDetail.builder()
                .type(PushMessageTypeEnum.DAPP).subType(PushMessageSubTypeEnum.Dapp.RELEASE_WITNESS.name())
                .pushEnum(AliyunPushEnum.NOTICE).addresses(list)
                .title(nickName + "邀请您帮Ta完成Dapp发布见证")
                .content(dapp.getName())
                .data(DappWitnessNoticeDto.builder().nickName(nickName).dappId(dappWitnessDto.getDappId()).build())
                .build());
        return true;
    }

    @Override
    public DappDetailDto detail(@RequestParam("dappId") Long dappId) {
        String address = RequestContextUtils.getAddress();
        Dapp dapp = dappMapper.selectByPrimaryKey(dappId);
        if (dapp == null)
            throw new BaseException("该Dapp不存在");
        DappDetailDto detailDto = new DappDetailDto();
        detailDto.setDapp(dapp);
        if (dapp.getAddress().equalsIgnoreCase(address)) {
            int count = dappReportMapper.countByDappId(dappId);
            List<DappWitness> list = dappWitnessMapper.list(dappId, address);
            detailDto.setExtra(DappDetailExtra.builder().witnessList(list).report(count).build());
        }
        return detailDto;
    }

    @Override
    public List<DappSlideshowDto> slideshow() {
        return dappSlideshowMapper.list();
    }

    @Override
    @RedissonDistributedLock(key = {"@address", "#dappId"})
    public void addFavorite(@RequestParam("dappId") Long dappId) {
        DappFavorite favorite = favoriteMapper.getByAddressAndDappId(RequestContextUtils.getAddress(), dappId);
        if (favorite != null)
            return;
        Dapp dapp = dappMapper.selectByPrimaryKey(dappId);
        if (dapp == null)
            throw new BaseException("该Dapp不存在");
        favoriteMapper.insertSelective(DappFavorite.builder().address(RequestContextUtils.getAddress()).dappId(dappId).createTime(new Date()).build());
    }

    @Override
    public void cancelFavorite(@RequestParam("dappId") Long dappId) {
        favoriteMapper.deleteByAddressAndDappId(RequestContextUtils.getAddress(), dappId);
    }

    @Override
    public PageInfo<DappListDto> myFavorite(@RequestBody Page page) {
        PageHelper.startPage(page.getPage(), page.getSize());
        return PageInfo.of(favoriteMapper.list(RequestContextUtils.getAddress()));
    }
}
