package com.bosha.common.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: InstructionsInfoDto
 * @Author liqingping
 * @Date 2020-01-03 18:18
 */
@Data
@ApiModel
public class InstructionsInfoDto {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("名字")
    private String name;
}
