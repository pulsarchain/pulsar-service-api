package com.bosha.common.api.dto;

import com.bosha.common.api.entity.SystemContent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SystemContentManagerListDto
 * @Author liqingping
 * @Date 2019-12-31 16:26
 */
@Data
@ApiModel
public class SystemContentManagerListDto extends SystemContent {
    private static final long serialVersionUID = -1623735508929117161L;

    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("分类")
    private String category;

}
