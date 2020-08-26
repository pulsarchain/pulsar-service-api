package com.bosha.dapp.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: WishListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-31 10:41
 */
@Data
@ApiModel("心愿列表")
public class WishListDto {

    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("图片")
    private String img;
}
