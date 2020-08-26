package com.bosha.dapp.api.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "活动参加")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksActivityJoin {
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id", required = true)
    @NotNull(message = "活动id不可为空")
    private Long activityId;

    /**
     * 类型：1 感兴趣，2 要参加
     */
    @ApiModelProperty(value = "类型：1 感兴趣，2 要参加", required = true)
    @NotNull(message = "类型不可为空不可为空")
    private Integer type;

    /**
     * 携带人数
     */
    @ApiModelProperty(value = "携带人数（只有自己参加时传1）", required = true)
    @NotNull(message = "携带人数不可为空")
    private Integer withFriend;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}