package com.bosha.dapp.api.dto;

import com.bosha.dapp.api.entity.SparksWish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WishDetailDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-31 10:43
 */
@Data
@ApiModel("心愿详情")
public class WishDetailDto extends SparksWish {

    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户头像")
    private String headImg;
}
