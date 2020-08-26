package com.bosha.common.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "版本更新")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Version implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号", required = true)
    @NotBlank(message = "版本号不可为空")
    private String version;

    /**
     * 更新提示语
     */
    @ApiModelProperty(value = "更新提示语", required = true)
    @NotBlank(message = "更新提示语不可为空")
    private String tips;

    /**
     * 下载地址
     */
    @ApiModelProperty(value = "下载地址", required = true)
    @NotBlank(message = "下载地址不可为空")
    private String download;

    /**
     * 系统：1 安卓，2 iOS
     */
    @ApiModelProperty(value = "系统：1 安卓，2 iOS", required = true)
    @NotNull(message = "系统不可为空")
    private Integer os;

    /**
     * 更新类型：0 提示更新，1 强制更新
     */
    @ApiModelProperty(value = "更新类型：0 提示更新，1 强制更新", required = true)
    @NotNull(message = "更新类型不可为空")
    private Integer updateType;

    /**
     * 状态：0 下架，1 上架
     */
    @ApiModelProperty(value = "状态：0 下架，1 上架")
    private Integer status;

    /**
     * 当前版本是非在审核：0 否，1 是
     */
    @ApiModelProperty(value = "当前版本是非在审核：0 否，1 是")
    private Integer audit;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "数据库创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "数据库更新时间")
    private Date updateTime;

    public Integer getAudit() {
        return this.audit == null ? 0 : this.audit;
    }

    private static final long serialVersionUID = 1L;

}