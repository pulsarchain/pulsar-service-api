package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel("用户地址薄")
public class UserFollowListDto {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 关注用户Id
     */

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("用户名")
    private String nickName;
    @ApiModelProperty("头像")
    private String headImg;

    /**
     * 昵称备注
     */
    @ApiModelProperty("昵称")
    private String remark;

    @ApiModelProperty("是否关注1，关注 ，0未关注")
    private Integer follow;

    /**
     * 是否好友1.好友，0非好友
     */
    @ApiModelProperty("是否好友1.好友，0非好友")
    private Integer friend;
}
