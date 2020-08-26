package com.bosha.dapp.api.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-bosha-dapp-api-entity-SparksOrg")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksOrg {

    public static final int STATUS_UN_INVITE = 1, STATUS_WITNESSING = 2, STATUS_SUCCESS = 3;

    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户地址
     */
    @ApiModelProperty(value = "用户地址")
    private String address;

    /**
     * 状态：1 未邀请见证，2 邀请见证中，3 成功
     */
    @ApiModelProperty(value = "状态：1 未邀请见证，2 邀请见证中，3 成功")
    private Integer status;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 分类
     */
    @ApiModelProperty(value="分类")
    private String category;
    /**
     * logo
     */
    @ApiModelProperty(value = "logo", required = true)
    @NotBlank(message = "名称不可为空")
    private String logo;

    /**
     * 成立时间
     */
    @ApiModelProperty(value = "成立时间", required = true)
    @NotNull(message = "成立时间不可为空")
    private Date startTime;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank(message = "名称不可为空")
    private String contactNumber;

    /**
     * 联系地址
     */
    @ApiModelProperty(value = "联系地址", required = true)
    @NotBlank(message = "名称不可为空")
    private String contactAddress;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介", required = true)
    @NotBlank(message = "名称不可为空")
    private String intro;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "资质文件", required = true)
    @NotEmpty(message = "资质文件不可为空")
    private List<String> certificates;
}