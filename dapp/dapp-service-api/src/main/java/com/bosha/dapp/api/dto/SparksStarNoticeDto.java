package com.bosha.dapp.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: SparksStarNoticeDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 13:19
 */
@Data
@ApiModel("")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SparksStarNoticeDto {

    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("星星之火的id")
    private Long id;
    @ApiModelProperty("类型：1 点亮，2 擦亮，3 造星")
    private Integer type;
}
