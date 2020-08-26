package com.bosha.star.api.dto.web;

import java.io.Serializable;
import java.math.BigDecimal;


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
@ApiModel("星系详情/我的星系")
public class StarDetailDto extends Star implements Serializable {
    private static final long serialVersionUID = -5931746851735759088L;

    @ApiModelProperty("创建者昵称")
    private String nickName;
    @ApiModelProperty("加入状态：null 未加入，0 区块确认中，1 加入成功")
    private Integer join;
    @ApiModelProperty("我的级别：1 脉冲星，2 脉冲双星，3 小绿人")
    private Integer level;
    @ApiModelProperty("我的当前算力值")
    private BigDecimal myHz;
    @ApiModelProperty("我推荐加入的人数")
    private Integer myRecommendNum;
    @ApiModelProperty("脉冲星数量")
    private Integer starNum;
    @ApiModelProperty("脉冲双星数量")
    private Integer doubleStarNum;
    @ApiModelProperty("小绿人数量")
    private Integer greenManNum;

}
