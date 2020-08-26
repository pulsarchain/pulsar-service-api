package com.bosha.dapp.api.dto;

import java.util.List;


import com.bosha.dapp.api.entity.SparksDonate;
import com.bosha.dapp.api.entity.SparksDonateBuy;
import com.bosha.dapp.api.entity.SparksReceiveAccount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DonateDetailDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-27 15:00
 */
@Data
@ApiModel("捐赠详情")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonateDetailDto {

    @ApiModelProperty("捐赠")
    private SparksDonate donate;
    @ApiModelProperty("是否已收藏：0 否，1 是")
    private Integer collect;
    @ApiModelProperty("是否已通知：0 否，1 是")
    private Integer notice;
    @ApiModelProperty("当前token对应的地址参与的星星之火类型：1 点亮，2 擦亮，3 造星")
    private List<Integer> joinTypes;
    @ApiModelProperty("收款账户")
    private List<SparksReceiveAccount> receiveAccounts;
    @ApiModelProperty("购买人信息")
    private List<SparksDonateBuy> donateBuyList;
}
