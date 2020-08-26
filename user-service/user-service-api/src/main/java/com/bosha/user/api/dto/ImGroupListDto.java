package com.bosha.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ImGroupListDto {
    private Integer id;
    @ApiModelProperty("群名字")
    private String groupName;
    @ApiModelProperty("群通知")
    private String groupNotice;
    @ApiModelProperty("群头像")
    private String groupHead;
    @ApiModelProperty("群人数")
    private Integer number;
}
