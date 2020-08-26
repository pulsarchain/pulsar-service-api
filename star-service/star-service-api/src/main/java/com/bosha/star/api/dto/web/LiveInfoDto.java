package com.bosha.star.api.dto.web;

import java.math.BigDecimal;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveInfoDto
 * @Author liqingping
 * @Date 2020-03-13 12:26
 */
@Data
@ApiModel("直播信息")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveInfoDto {

    @ApiModelProperty("创建直播的系统打币地址")
    private String systemAddress;
    @ApiModelProperty("刷礼物的系统打币地址")
    private String giftAddress;
    @ApiModelProperty("直播的每分钟费用，例如：用户选择直播时长为100分钟，则直播的额外费用为 100 x liveFeePerMin")
    private BigDecimal liveFeePerMin;
    @ApiModelProperty("当前地址的星系成员状态：null 未加入和创建任何星系，0 区块确认中，1 加入成功")
    private Integer starStatus;
    @ApiModelProperty("当前地址的星系名称")
    private String starName;
    @ApiModelProperty("等级：null 未加入和创建任何星系，1 脉冲星，2 脉冲双星，3 小绿人")
    private Integer level;
    @ApiModelProperty("当前用户的昵称")
    private String nickName;
    @ApiModelProperty("当前用户的头像")
    private String headImg;
    @ApiModelProperty("是否可以创建直播：0 已经创建了 处于区块确认中，1 创建成功，等待开播，2 直播进行中，3 可以创建")
    private Integer createStatus;
    @ApiModelProperty("当前地址的聊天的userSig")
    private String imUserSig;
    @ApiModelProperty("当前地址的直播的userSig")
    private String liveUserSig;
    @ApiModelProperty("聊天的appid")
    private Long imSdkAppid;
    @ApiModelProperty("直播的appid")
    private Long liveSdkAppid;
}
