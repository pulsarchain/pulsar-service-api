package com.bosha.star.api.dto.web;

import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: JoinMinerPoolResultDto
 * @Author liqingping
 * @Date 2019-08-01 14:15
 */
@Data
@ApiModel("加入星系返回值")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinStarResultDto implements Serializable {
    private static final long serialVersionUID = -4664431957024023050L;

    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("星系名称")
    private String starName;
    @ApiModelProperty("等级：1 脉冲星，2 脉冲双星，3 小绿人")
    private Integer level;
}
