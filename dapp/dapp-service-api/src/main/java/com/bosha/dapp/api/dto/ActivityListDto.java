package com.bosha.dapp.api.dto;

import java.util.Date;
import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ActivityListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 14:32
 */
@Data
@ApiModel("活动（走进星星）列表")
public class ActivityListDto {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("图片")
    private String img;
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("地址")
    private String location;
    @ApiModelProperty("出发时间")
    private Date time;
    @ApiModelProperty("收藏：0 否，1 是")
    private Integer collect;
    @ApiModelProperty("参与人数")
    private Integer joinNum;
    @ApiModelProperty("头像列表：只返回前10个")
    private List<String> headImgs;
}
