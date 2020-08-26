package com.bosha.common.api.dto;

import com.bosha.common.api.enums.DictTypeEnum;
import com.bosha.commons.dto.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: DictRequestDto
 * @Author liqingping
 * @Date 2019-12-20 10:46
 */
@Data
@ApiModel
public class DictRequestDto extends Page {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("类型：全部：all\" +\n" +
            "            \"行情：market，币种：coin，项目分类：project_category，文章：article，快讯：flash，默认为all")
    private DictTypeEnum type = DictTypeEnum.all;


}
