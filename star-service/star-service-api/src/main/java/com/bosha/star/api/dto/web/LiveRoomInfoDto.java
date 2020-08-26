package com.bosha.star.api.dto.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
 * @DESCRIPTION: LiveRoomInfoDto
 * @Author liqingping
 * @Date 2020-03-25 15:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("直播间详情")
@Builder
public class LiveRoomInfoDto   implements Serializable {
    private static final long serialVersionUID = 5801766132315876099L;

    @ApiModelProperty("主播地址")
    private String address;
    @ApiModelProperty("直播标题")
    private String title;
    @ApiModelProperty("封面图")
    private String coverImg;
    @ApiModelProperty("主播昵称")
    private String nickName;
    @ApiModelProperty("主播头像")
    private String headImg;
    @ApiModelProperty("主播所在星系的名称")
    private String starName;
    @ApiModelProperty("直播开始时间")
    private Date liveStartTime;
    @ApiModelProperty("直播结束时间")
    private Date liveEndTime;
    @ApiModelProperty("投入币数")
    private BigDecimal amount;
    @ApiModelProperty("当前用户的昵称")
    private String currentUserNickName;
    @ApiModelProperty("当前用户的头像")
    private String currentUserHeadImg;
    @ApiModelProperty("当前用户的星系名称")
    private String currentUserStarName;
    @ApiModelProperty("当前用户的星系等级")
    private Integer currentUserStarLevel;
    @ApiModelProperty("拉流地址")
    private String pullUrl;

    @ApiModelProperty("星系成员数量")
    private Integer starMemberCount;
    @ApiModelProperty("直播的id，也是 聊天室的id")
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
