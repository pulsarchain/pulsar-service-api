package com.bosha.common.api.dto;

import javax.validation.constraints.NotNull;


import com.bosha.common.api.enums.DictTypeEnum;
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
public class SystemContentQueryDto extends Page {

    @ApiModelProperty("typeEnum枚举值")
    @NotNull(message = "分类不可为空")
    private DictTypeEnum typeEnum;

}
