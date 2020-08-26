package com.bosha.common.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@ApiModel(value = "系统内容")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemContent implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 关联的字典表的id
     */
    @ApiModelProperty(value = "关联的字典表的id", required = true)
    @NotNull(message = "分类id不可为空")
    private Long dictId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不可为空")
    @Length(min = 1,max = 80,message = "最长80个字")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", required = true)
    @NotBlank(message = "内容不可为空")
    @Length(min =1, max = 6666, message = "最长5000个字")
    private String content;

    /**
     * 状态：0 隐藏，1 显示
     */
    @ApiModelProperty(value = "状态：0 隐藏，1 显示")
    private Integer status;

    /**
     * 是否推送：0 否，1 是
     */
    @ApiModelProperty(value = "是否推送：0 否，1 是")
    private Integer push;

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

    @ApiModelProperty(value = "多语言名称",required = true)
    @NotEmpty(message = "多语言类型不可为空")
    private List<SystemContentI18n> i18ns;

    private static final long serialVersionUID = 1L;
}