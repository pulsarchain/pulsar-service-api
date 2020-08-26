package com.bosha.dapp.api.dto;

import java.math.BigDecimal;
import java.util.List;


import com.bosha.dapp.api.entity.SparksReceiveAccount;
import com.bosha.dapp.api.entity.SparksStar;
import com.bosha.dapp.api.entity.SparksWitness;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: SparksStarDetailDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 10:10
 */
@Data
@ApiModel("详情")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SparksStarDetailDto {

    @ApiModelProperty("")
    private SparksStar sparksStar;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("收款账户")
    private List<SparksReceiveAccount> receiveAccounts;
    @ApiModelProperty("见证列表")
    private List<SparksWitness> witnesses;

    @ApiModelProperty("总共点亮")
    private BigDecimal totalLight;
    @ApiModelProperty("每月点亮（造星时该字段范围null）")
    private BigDecimal monthLight;
    @ApiModelProperty("参与的数字身份")
    private Integer lightNum;
    @ApiModelProperty("点亮进度")
    private BigDecimal lightPercent;

}
