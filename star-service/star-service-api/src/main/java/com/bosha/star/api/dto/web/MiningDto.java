package com.bosha.star.api.dto.web;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


import com.bosha.star.api.enums.LiveMiningTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MiningDto
 * @Author liqingping
 * @Date 2020-03-26 15:39
 */
@Data
@ApiModel("参与挖矿")
public class MiningDto implements Serializable {
    private static final long serialVersionUID = -304188021182585441L;

    @ApiModelProperty(value = "直播挖矿的id", required = true)
    @NotNull(message = "直播挖矿的id")
    private Long liveMiningId;
    @ApiModelProperty(value = "挖矿类型", required = true)
    @NotNull(message = "挖矿类型不可为空")
    private LiveMiningTypeEnum type;
    //@ApiModelProperty(value = "挖矿时间，格式：yyyy-MM-dd HH:mm:ss", required = true)
    //@NotNull(message = "挖矿时间不可为空")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //private Date time;
}
