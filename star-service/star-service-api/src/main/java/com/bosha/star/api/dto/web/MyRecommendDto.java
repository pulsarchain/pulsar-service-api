package com.bosha.star.api.dto.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: MyRecommendDto
 * @Author liqingping
 * @Date 2020-03-06 18:56
 */
@ApiModel("我的推荐")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyRecommendDto implements Serializable {
    private static final long serialVersionUID = -1835973471393961983L;

    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("等级")
    private Integer level;
    @ApiModelProperty("加入时间")
    private Date joinTime;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("当前算力值")
    private BigDecimal currentHz;
    @ApiModelProperty("0 区块确认中，1 加入成功")
    private Integer status;
}
