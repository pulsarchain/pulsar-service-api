package com.bosha.dapp.api.dto;

import java.util.Date;
import java.util.List;


import com.bosha.dapp.api.entity.Dapp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappReportPublicListDto
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 18:25
 */
@Data
@ApiModel("dapp举报公示列表")
public class DappReportPublicListDto {

    @ApiModelProperty("举报的id")
    private Long reportId;
    @ApiModelProperty("举报人昵称")
    private String nickName;
    @ApiModelProperty("举报人地址")
    private String address;
    @ApiModelProperty("举报人头像")
    private String headImg;
    @ApiModelProperty("举报时间")
    private Date reportTime;
    @ApiModelProperty("dapp名称")
    private String dappName;
    @ApiModelProperty("举报内容")
    private String reportContent;
    @ApiModelProperty("举报图片列表")
    private List<String> reportPicList;
    @ApiModelProperty("真数量")
    private Integer trueNum;
    @ApiModelProperty("假数量")
    private Integer falseNum;
    @ApiModelProperty("是否已选择：null 未选择，0 假，1 真")
    private Integer select;
    @ApiModelProperty("公示期结束时间")
    private Date publicEndTime;
    @ApiModelProperty("dapp（非详情接口返回null）")
    private Dapp dapp;
}
