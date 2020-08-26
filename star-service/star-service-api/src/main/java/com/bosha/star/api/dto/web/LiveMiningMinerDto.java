package com.bosha.star.api.dto.web;

import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningMinerDto
 * @Author liqingping
 * @Date 2020-03-25 14:09
 */
@Data
@ApiModel("我参与的挖矿")
public class LiveMiningMinerDto {

    @ApiModelProperty("直播标题")
    private String title;
    @ApiModelProperty("直播创建者所在的星系名称")
    private String starName;
    @ApiModelProperty("直播的id")
    private Long id;
    @ApiModelProperty("状态：0 区块确认中，1 创建成功，等待开播，2 直播进行中，3 直播已结束")
    private Integer status;
    @ApiModelProperty("直播创建者的地址")
    private String address;
    @ApiModelProperty("挖矿收益")
    private BigDecimal amount = BigDecimal.ZERO;
    @ApiModelProperty("送出的礼物")
    private BigDecimal gift = BigDecimal.ZERO;
    @ApiModelProperty("参与开始时间")
    private Date joinStartTime;
    @ApiModelProperty("参与结束时间")
    private Date joinEndTime;
    @ApiModelProperty("回看视频的链接")
    private String videoUrl;
    @ApiModelProperty("回看视频的状态：1 正在生成，2 生成成功，3 已失效，4 主播未开播过，不存在回看")
    private Integer videoStatus;
}
