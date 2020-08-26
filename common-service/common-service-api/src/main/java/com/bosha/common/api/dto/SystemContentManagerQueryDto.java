package com.bosha.common.api.dto;

import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SystemContentManagerQueryDto
 * @Author liqingping
 * @Date 2019-12-31 16:28
 */
@Data
@ApiModel
public class SystemContentManagerQueryDto extends Page {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("创建时间开始，格式：yyyy-MM-dd HH:mm:ss")
    private String createTimeStart;
    @ApiModelProperty("创建时间结束，格式：yyyy-MM-dd HH:mm:ss")
    private String createTimeEnd;
    @ApiModelProperty("状态：0 隐藏，1 显示")
    private Integer status;
    @ApiModelProperty("关联的字典表的id/分类id")
    private Long dictId;
}
