package com.bosha.common.api.dto;

import java.io.Serializable;
import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: HelpCenterDto
 * @Author liqingping
 * @Date 2019-12-31 17:20
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpCenterDto implements Serializable {
    private static final long serialVersionUID = 2563348829106046738L;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类列表")
    private List<HelpCenterCategoryDto> categories;
}
