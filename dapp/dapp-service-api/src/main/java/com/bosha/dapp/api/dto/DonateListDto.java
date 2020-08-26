package com.bosha.dapp.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DonateListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 17:41
 */
@Data
@ApiModel("捐赠列表")
public class DonateListDto {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("捐赠的物品名称")
    private String name;
    @ApiModelProperty("图片")
    private String img;
    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("类型：1 点亮，2 擦亮，3 造星")
    private Integer type;
    @ApiModelProperty("分类")
    private String category;
    @ApiModelProperty("描述")
    private String desc;
    @ApiModelProperty("数量")
    private Integer num;
    @ApiModelProperty("库存")
    private Integer sku;
    @ApiModelProperty("是否已收藏，0 否，1 是")
    private Integer collect;
    @ApiModelProperty("单位")
    private String unit;
}
