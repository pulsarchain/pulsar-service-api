package com.bosha.common.api.entity;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * area
 *
 * @author
 */
@Data
@ApiModel("地区")
public class Area implements Serializable {

    @ApiModelProperty("ID")
    private Integer id;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("父级名字")
    private Integer parentId;
    @ApiModelProperty("简称")
    private String shortName;
    @ApiModelProperty("1.省 2.市 3.区 4.镇")
    private Integer level;
    @ApiModelProperty(value = "排序", hidden = true)
    @JsonIgnore
    private Integer sort;

    private static final long serialVersionUID = 1L;

}