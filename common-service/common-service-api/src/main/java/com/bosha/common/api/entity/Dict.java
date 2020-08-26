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

@ApiModel(value = "字典表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dict implements Serializable {
    private Long id;

    @ApiModelProperty(value = "key", required = true)
    @NotBlank(message = "key不可为空")
    private String key;

    @ApiModelProperty(value = "value")
    //@NotBlank(message = "value不可为空")
    private String value;

    /**
     * 关键字
     */
    @ApiModelProperty(value = "类型：行情：market，币种：coin，项目：project，文章：article，快讯：flash", required = true)
    @NotBlank(message = "不可为空")
    private String type;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id，没有父级时为0", required = true)
    @NotNull(message = "不可为空")
    private Long parentId;

    /**
     * 排序：值越小越靠前
     */
    @ApiModelProperty(value = "排序：值越小越靠前", required = true)
    @NotNull(message = "排序不可为空")
    private Integer sort;

    /**
     * 是否可用：0 否，1 是
     */
    @ApiModelProperty(value = "是否可用：0 否，1 是", required = true)
    @NotNull(message = "是否可用不可为空")
    private Integer enable;

    @ApiModelProperty(value = "备注")
    private String reamrk;
    /**
     * 添加人
     */
    @ApiModelProperty(value = "添加人")
    private String operator;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private List<Dict> children;

    private String parentName;

    @ApiModelProperty(value = "多语言名称",required = true)
    @NotEmpty(message = "多语言类型不可为空")
    private List<DictI18n> i18ns;

    private static final long serialVersionUID = 1L;

}