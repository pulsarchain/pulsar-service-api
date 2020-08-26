package com.bosha.dapp.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="dapp分类")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DappCategories {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 名称
    */
    @ApiModelProperty(value="名称")
    private String name;

    /**
    * 排序：数字越小越靠前
    */
    @ApiModelProperty(value="排序：数字越小越靠前")
    @JsonIgnore
    private Integer sort;

    /**
    * 0 隐藏，1 显示
    */
    @ApiModelProperty(value="0 隐藏，1 显示")
    @JsonIgnore
    private Integer show;

    
}