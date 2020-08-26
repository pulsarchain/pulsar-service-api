package com.bosha.common.api.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "系统内容多语言")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemContentI18n implements Serializable {
    @ApiModelProperty(value = "关联的id")
    @JsonIgnore
    private Long scId;

    @ApiModelProperty(value = "名称（系统内容的title）", required = true)
    @NotBlank(message = "名称不可为空")
    private String name;

    @ApiModelProperty(value = "语言：如 zh_CN、en_US等", required = true)
    @NotBlank(message = "语言不可为空")
    private String language;

    @ApiModelProperty(value = "内容",required = true)
    @NotBlank(message = "内容不可为空")
    private String content;


    private static final long serialVersionUID = 1L;
}