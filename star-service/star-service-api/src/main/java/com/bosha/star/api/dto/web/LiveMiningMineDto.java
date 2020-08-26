package com.bosha.star.api.dto.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningMineDto
 * @Author liqingping
 * @Date 2020-03-25 13:35
 */
@Data
@ApiModel("我发起的直播")
public class LiveMiningMineDto implements Serializable {
    private static final long serialVersionUID = 105996439584290692L;

    @ApiModelProperty("发起人头像")
    private String headImg;
    @ApiModelProperty("直播标题")
    private String title;
    @ApiModelProperty("发起人地址")
    private String address;
    @ApiModelProperty("直播id")
    private Long id;
    @ApiModelProperty("主播所在的星系名称")
    private String starName;
    @ApiModelProperty("直播开始时间")
    private Date liveStartTime;
    @ApiModelProperty("直播结束时间")
    private Date liveEndTime;
    @ApiModelProperty("状态：0 区块确认中，1 创建成功，等待开播，2 直播进行中，3 直播已结束")
    private Integer status;
    @ApiModelProperty("在线人数")
    private Integer onlineNum;
    @ApiModelProperty("挖矿人数")
    private Integer minerNum;
    @ApiModelProperty("受邀星系数量")
    private Integer inviteStarNum;
    @ApiModelProperty("投入燃料")
    private BigDecimal amount;
    @ApiModelProperty("挖矿消耗")
    private BigDecimal cost;
    @ApiModelProperty("收到的礼物")
    private BigDecimal gift;
    @ApiModelProperty("回看视频的链接（全部为mp4格式）")
    private String videoUrl;
    @ApiModelProperty("回看视频的状态：1 正在生成，2 生成成功，3 已失效，4 主播未开播过，不存在回看")
    private Integer videoStatus;
}
