package com.bosha.user.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author shining
 */
@ApiModel(value = "com-bosha-user-api-entity-ImGroupMember")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImGroupMember {
    @ApiModelProperty(value = "null")
    private Long id;

    /**
     * 组id
     */
    @ApiModelProperty(value = "组id")
    private Long groupId;

    /**
     * 成员
     */
    @ApiModelProperty(value = "成员")
    private String address;

    /**
     * 群成员昵称
     */
    @ApiModelProperty(value = "群成员昵称")
    private String nickName;

    /**
     * 入群时间
     */
    @ApiModelProperty(value = "入群时间")
    private Date createTime;



}