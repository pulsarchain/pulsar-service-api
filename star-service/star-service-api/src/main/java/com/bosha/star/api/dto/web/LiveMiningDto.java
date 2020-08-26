package com.bosha.star.api.dto.web;

import com.bosha.star.api.entity.LiveMining;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveMiningDto
 * @Author liqingping
 * @Date 2020-03-25 11:51
 */
@Data
@ApiModel("直播列表")
public class LiveMiningDto extends LiveMining {
    private static final long serialVersionUID = 4972086369700498672L;

    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("主播所在的星系名称")
    private String starName;
    @ApiModelProperty("拉流地址，历史列表该字段为空")
    private String pullUrl;
}
