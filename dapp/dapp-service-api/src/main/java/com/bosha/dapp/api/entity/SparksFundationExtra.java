package com.bosha.dapp.api.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "基金相关的资质文件和合同")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparksFundationExtra {
    /**
     * 基金id
     */
    @ApiModelProperty(value = "基金id", hidden = true)
    @JsonIgnore
    private Long fundationId;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", required = true)
    @NotBlank(message = "地址不可为空")
    private String url;

    /**
     * 类型：1 资质文件，2 合同
     */
    @ApiModelProperty(value = "类型：1 资质文件，2 合同", required = true)
    @NotNull(message = "类型不可为空")
    private Integer type;
}