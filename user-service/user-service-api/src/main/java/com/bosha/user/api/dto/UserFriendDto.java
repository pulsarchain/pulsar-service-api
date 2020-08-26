package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserFriendDto {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("用户名")
    private String nickName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("是否关注1，关注 ，0未关注")
    private Integer follow;
    @ApiModelProperty("是否好友1.好友，0非好友")
    private Integer friend;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("头像base64")
    private String headImgBase64;

}
