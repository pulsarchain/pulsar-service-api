package com.bosha.dapp.api.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WitnessRelationDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 11:56
 */
@Data
@ApiModel("见证关系")
public class WitnessRelationDto {

    @ApiModelProperty("关联的id")
    @NotNull(message = "关联的id不可为空")
    private Long relatedId;
    @ApiModelProperty("关系列表")
    @NotEmpty(message = "关系列表不可为空")
    @Size(max = 8, min = 1, message = "范围1-8")
    private List<Relation> relations;

    @Data
    @ApiModel("关系")
    public static final class Relation {
        @ApiModelProperty(value = "地址", required = true)
        @NotBlank(message = "地址不可为空")
        private String address;
        @ApiModelProperty(value = "与之关系", required = true)
        @NotBlank(message = "关系不可为空")
        private String relation;
    }
}
