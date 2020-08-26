package com.bosha.star.api.dto.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveRoomInfoPushDto
 * @Author liqingping
 * @Date 2020-03-28 11:42
 */
@Data
@ApiModel("直播间详情推送")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveRoomInfoPushDto {

    @ApiModelProperty("直播的id")
    private Long id;
    @ApiModelProperty("在线人数")
    private Integer onlineNum = 1;
    @ApiModelProperty("挖矿人数")
    private Integer minerNum;
    @ApiModelProperty("剩余币数")
    private BigDecimal leftAmount;
    @ApiModelProperty("状态：0 区块确认中，1 创建成功，等待开播，2 直播进行中，3 直播已结束")
    private Integer status;
    @ApiModelProperty("在线人数的前5个头像列表")
    private List<String> headImgs = new ArrayList<>();

}
