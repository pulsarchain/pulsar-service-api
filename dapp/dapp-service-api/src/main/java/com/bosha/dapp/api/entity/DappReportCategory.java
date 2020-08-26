package com.bosha.dapp.api.entity;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="dapp举报分类")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DappReportCategory {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 父级id
    */
    @ApiModelProperty(value="父级id")
    @JsonIgnore
    private Long parentId;

    /**
    * 级别
    */
    @ApiModelProperty(value="级别")
    private Integer level;

    /**
    * 名称
    */
    @ApiModelProperty(value="名称")
    private String name;

    /**
    * 排序：越小越靠前
    */
    @ApiModelProperty(value="排序：越小越靠前")
    @JsonIgnore
    private Integer sort;

    /**
    * 是否显示：0 否，1 是
    */
    @ApiModelProperty(value="是否显示：0 否，1 是")
    @JsonIgnore
    private Integer show;

    @ApiModelProperty("二级分类")
    private List<DappReportCategory> list;
}