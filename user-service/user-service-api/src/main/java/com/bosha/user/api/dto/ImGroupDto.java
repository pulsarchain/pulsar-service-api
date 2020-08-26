package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ImGroupDto {
    private Integer id;
    @ApiModelProperty("群名字")
    private String groupName;
    @ApiModelProperty("群通知")
    private String groupNotice;
    @ApiModelProperty("群头像")
    private String groupHead;
    @ApiModelProperty("群成员")
    private List<UserFollowListDto> member;
    @ApiModelProperty("创建者")
    private String address;
}
