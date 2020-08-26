package com.bosha.user.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "com-bosha-user-api-entity-ImGroup")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImGroup {
    @ApiModelProperty(value = "null")
    private Long id;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String address;

    /**
     * 群名称
     */
    @ApiModelProperty(value = "群名称")
    private String groupName;

    /**
     * 群名字
     */
    @ApiModelProperty(value = "群名字")
    private String groupNotice;

    /**
     * 群头像
     */
    @ApiModelProperty(value = "群头像")
    private String groupHead;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}