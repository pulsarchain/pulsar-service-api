package com.bosha.user.api.service;

import com.bosha.user.api.dto.BlockDto;
import io.swagger.annotations.ApiOperation;

public interface AccountArrivalNoticeService {

    @ApiOperation("认证成功确认")
    void confirm(BlockDto block);
}
