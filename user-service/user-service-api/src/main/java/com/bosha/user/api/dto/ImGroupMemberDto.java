package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ImGroupMemberDto {
    @ApiModelProperty("组id")
    private Long groupId;
    @ApiModelProperty("成员")
    private List<UserFollowListDto> member;
    @ApiModelProperty("创建人")
    private String address;
}
