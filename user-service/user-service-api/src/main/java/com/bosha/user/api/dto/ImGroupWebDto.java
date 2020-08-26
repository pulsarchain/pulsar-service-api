package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ImGroupWebDto {
    @ApiModelProperty("组Id")
    private Long id;
    @ApiModelProperty("群名称")
    private String groupName;
    @ApiModelProperty("群头像")
    private String groupHead;
    @ApiModelProperty("群公告")
    private String groupNotice;
    @ApiModelProperty("是否群主1是，0否")
    private Integer owner;
    @ApiModelProperty("群人数")
    private Integer number;
    @ApiModelProperty("群成员")
    private List<ImGroupMemberWebDto> imGroupMemberWebDtos;
    @ApiModelProperty("当前用户是否在群里面，1在，0，不在")
    private Integer exist;
}
