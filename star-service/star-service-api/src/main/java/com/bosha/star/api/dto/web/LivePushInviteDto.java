package com.bosha.star.api.dto.web;

import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LivePushInviteDto
 * @Author liqingping
 * @Date 2020-03-25 10:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivePushInviteDto {

    @ApiModelProperty("发起人地址")
    private String address;
    @ApiModelProperty("直播挖矿的id")
    private Long liveMiningId;
    @ApiModelProperty("直播挖矿的标题")
    private String title;
    @ApiModelProperty("直播挖矿的投入燃料")
    private BigDecimal amount;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("是否已接受邀请")
    private boolean invite;
    @ApiModelProperty("发起人头像")
    private String headImg;
    @ApiModelProperty("发起人昵称")
    private String nickName;

}
