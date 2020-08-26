package com.bosha.dapp.server.controller;

import java.util.List;


import com.bosha.commons.controller.BaseController;
import com.bosha.commons.dto.Page;
import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.constants.DappServiceConstants;
import com.bosha.dapp.api.dto.ActivityCalendarDto;
import com.bosha.dapp.api.dto.ActivityDetailDto;
import com.bosha.dapp.api.dto.ActivityListDto;
import com.bosha.dapp.api.dto.ActivityQuery;
import com.bosha.dapp.api.entity.SparksActivity;
import com.bosha.dapp.api.entity.SparksActivityJoin;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.api.service.ActivityService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ActivityController
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 15:11
 */
@Api(tags = "活动（走进星星）")
@RestController
@Slf4j
@RequestMapping(DappServiceConstants.WEB_PRIFEX + "/activity")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityService activityService;

    @ApiOperation("新增活动")
    @PostMapping("/add")
    Long add(@RequestBody @Validated SparksActivity activity) {
        activity.setAddress(getAddress());
        return activityService.add(activity);
    }

    @ApiOperation("打币推送更新hash")
    @PostMapping("/updateHash")
    boolean updateHash(@RequestBody SparksActivity activity) {
        if (StringUtils.isBlank(activity.getHash()))
            throw new BaseException("hash不可为空");
        return activityService.updateHash(SparksActivity.builder().id(activity.getId()).hash(activity.getHash()).build());
    }

    @ApiOperation("活动列表")
    @GetMapping("/list")
    PageInfo<ActivityListDto> list(@ApiParam @ModelAttribute ActivityQuery query) {
        return activityService.list(query);
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    ActivityDetailDto detail(@ApiParam(value = "活动的id") @RequestParam("id") Long id) {
        return activityService.detail(id);
    }

    @ApiOperation("参加/感兴趣")
    @PostMapping("/join")
    boolean join(@RequestBody @Validated SparksActivityJoin join) {
        if (join.getWithFriend() > 4)
            throw new BaseException(DappErrorMsgEnum.WITH_FRIEND_MAX);
        join.setAddress(getAddress());
        return activityService.join(join);
    }

    @ApiOperation("取消 参加/感兴趣")
    @PostMapping("/cancel")
    boolean cancel(@RequestBody SparksActivityJoin join) {
        join.setAddress(getAddress());
        return activityService.cancel(join);
    }

    @ApiOperation("我创建的活动")
    @GetMapping("/my")
    PageInfo<ActivityListDto> my(Page page) {
        return activityService.my(page);
    }

    @ApiOperation("我收藏的活动")
    @GetMapping("/myFavorite")
    PageInfo<ActivityListDto> myFavorite(@ApiParam(value = "1 感兴趣，2 要参加，3 已过期，该字段不传查询所有")
                                         @RequestParam(value = "type", required = false, defaultValue = "") Integer type, Page page) {
        return activityService.myFavorite(type, page);
    }

    @ApiOperation("星星日历")
    @GetMapping("/calendar")
    List<ActivityCalendarDto> calendar(@ApiParam(value = "开始时间，格式：yyyy-MM-dd HH:mmss") @RequestParam("startTime") String startTime,
                                       @ApiParam(value = "结束时间，格式：yyyy-MM-dd HH:mmss") @RequestParam("endTime") String endTime) {
        return activityService.calendar(getAddress(), startTime, endTime);
    }

    @ApiOperation("星星日历列表")
    @GetMapping("/calendar/list")
    PageInfo<ActivityListDto> calendar(@ApiParam(value = "开始时间，格式：yyyy-MM-dd HH:mmss") @RequestParam("startTime") String startTime,
                                       @ApiParam(value = "结束时间，格式：yyyy-MM-dd HH:mmss") @RequestParam("endTime") String endTime,
                                       @ApiParam(value = "1 感兴趣，2 要参加，3 已过期")
                                       @RequestParam(value = "type") Integer type, Page page) {
        return activityService.calendar(getAddress(), startTime, endTime, type, page);
    }
}
