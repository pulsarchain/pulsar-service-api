package com.bosha.dapp.api.dto;

import com.bosha.dapp.api.entity.DappReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappReportDetailDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 18:07
 */
@Data
@ApiModel("举报详情")
public class DappReportDetailDto extends DappReport {

    @ApiModelProperty("")
    private String dappLogo;
    @ApiModelProperty("dapp名称")
    private String dappName;
    @ApiModelProperty("dapp简介")
    private String dappIntro;
    @ApiModelProperty("dapp分类名称")
    private String dappTypeName;
    @ApiModelProperty("举报分类名称")
    private String reportTypeName;
    @ApiModelProperty("投票真实地址数")
    private Integer trueAddressNum;
    @ApiModelProperty("投票虚假地址数")
    private Integer falseAddressNum;
}
