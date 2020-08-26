package com.bosha.star.api.dto.web;

import java.io.Serializable;


import com.bosha.star.api.entity.Star;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: StarListDto
 * @Author liqingping
 * @Date 2020-03-05 15:53
 */
@Data
@NoArgsConstructor
@ApiModel("星系列表")
public class StarListDto extends Star implements Serializable {
    private static final long serialVersionUID = -5931746851735759088L;

    @ApiModelProperty("创建者昵称")
    private String nickName;
    @ApiModelProperty("加入状态：null 未加入，0 区块确认中，1 加入成功")
    private Integer join;
    @ApiModelProperty("脉冲星数量")
    private Integer starNum;
    @ApiModelProperty("脉冲双星数量")
    private Integer doubleStarNum;
    @ApiModelProperty("小绿人数量")
    private Integer greenManNum;

}
