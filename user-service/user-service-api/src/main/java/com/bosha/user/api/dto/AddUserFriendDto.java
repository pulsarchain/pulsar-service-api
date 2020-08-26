package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AddUserFriendDto {
    @ApiModelProperty("好友地址")
    private String followUserAddress;
    @ApiModelProperty("当前用户，不用传")
    private String userAddress;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("对方备注")
    private String fromRemark;
}
