package com.bosha.star.api.dto.web;

import java.io.Serializable;


import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: QueryMinerPoolListDto
 * @Author liqingping
 * @Date 2019-07-31 9:48
 */
@Data
@ApiModel("星系列表")
public class QueryStarListDto extends Page implements Serializable {
    private static final long serialVersionUID = -6523467245997285035L;

    @ApiModelProperty("搜索的星系名称")
    private String name;

    @ApiModelProperty(value = "排序字段：恒星数-starNum，能量值-hz，不传则默认按 恒星数排序")
    // @NotBlank(message = "排序字段不可为空")
    private String field = "starNum";
    @ApiModelProperty(value = "排序：asc 正序，desc 倒序，不传则默认 desc 倒序")
    //@NotBlank(message = "排序方式不可为空")
    private String orderBy = "desc";
}
