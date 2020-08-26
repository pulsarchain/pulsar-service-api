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

@ApiModel(value = "我的收藏")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksFavorite {
    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "")
    private String address;

    @ApiModelProperty(value = "关联的id", required = true)
    @NotNull(message = "关联的id不可为空")
    private Long relatedId;

    /**
     * 类型：1 捐赠，2 活动
     */
    @ApiModelProperty(value = "类型：1 捐赠，2 活动", required = true)
    @NotNull(message = "类型不可为空")
    private Integer type;

    @ApiModelProperty(value="")
    private Date createTime;
}