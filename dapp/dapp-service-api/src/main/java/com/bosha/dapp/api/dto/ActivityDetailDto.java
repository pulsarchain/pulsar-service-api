package com.bosha.dapp.api.dto;

import java.util.List;


import com.bosha.dapp.api.entity.SparksActivity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: ActivityDetailDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-28 14:43
 */
@Data
@ApiModel("活动详情")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDetailDto {

    @ApiModelProperty("活动")
    private SparksActivity activity;
    @ApiModelProperty("参加人数")
    private Integer joinNum;
    @ApiModelProperty("感兴趣人数")
    private Integer interestNum;
    @ApiModelProperty("头像列表：只返回前10个")
    private List<String> headImgs;
    @ApiModelProperty("是否已收藏：0 否，1 是")
    private Integer collect;
    @ApiModelProperty("是否已参加： 0 否，1 是")
    private Integer join;
    @ApiModelProperty("是否感兴趣：0 否，1 是")
    private Integer interest;
    @ApiModelProperty("剩余名额")
    private Integer left;
}
