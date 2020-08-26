package com.bosha.dapp.api.dto;

import java.util.Date;
import java.util.List;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappWitnessNoticeDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 15:38
 */
@Data
@Builder
public class DappReportNoticeDto {

    @ApiModelProperty("举报内容")
    private String content;
    @ApiModelProperty("dapp的id")
    private Long dappId;
    @ApiModelProperty("图片列表")
    private List<String> picList;
    @ApiModelProperty("整改的到期时间")
    private Date modifyEndTime;
}
