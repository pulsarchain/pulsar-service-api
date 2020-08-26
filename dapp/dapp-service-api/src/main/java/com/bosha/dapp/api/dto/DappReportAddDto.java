package com.bosha.dapp.api.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappReportAddDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 17:53
 */
@Data
@ApiModel("dapp新增")
public class DappReportAddDto {

    @ApiModelProperty(value = "", required = true)
    @NotNull(message = "dappId不可为空")
    private Long dappId;
    @ApiModelProperty(value = "举报的分类id", required = true)
    @NotNull(message = "举报的分类id不可为空")
    private Long reportCategroyId;
    @ApiModelProperty(value = "内容", required = true)
    @NotBlank(message = "举报内容不可为空")
    @Length(min = 1,max = 220,message = "最多200个字")
    private String content;
    @ApiModelProperty(value = "图片列表", required = true)
    @NotEmpty(message = "图片列表不可为空")
    @Size(min = 1, max = 9, message = "size范围 1-9 ")
    @NotNull(message = "图片列表不可为空")
    private List<String> picList;
}
