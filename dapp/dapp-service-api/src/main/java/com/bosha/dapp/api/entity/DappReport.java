package com.bosha.dapp.api.entity;

import java.util.Date;
import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="dapp举报")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DappReport {
    @ApiModelProperty(value="")
    private Long id;

    @ApiModelProperty(value="")
    private Long dappId;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
    * 举报内容
    */
    @ApiModelProperty(value="举报内容")
    private String content;

    /**
    * 举报分类id
    */
    @ApiModelProperty(value="举报分类id")
    private Long reportCategroyId;

    /**
    * 状态：0 投票中，1 举报不真实，2 整改中，3 已过举报公示期 
    */
    @ApiModelProperty(value="状态：0 投票中，1 举报不真实，2 整改中，3 已整改，4 已过举报公示期，5 整改到期未处理 ")
    private Integer status;

    /**
     * 公示期结束时间
     */
    @ApiModelProperty(value="公示期结束时间")
    private Date publicEndTime;

    /**
    * dapp整改结束时间
    */
    @ApiModelProperty(value="dapp整改结束时间")
    private Date dappModifyEndTime;

    @ApiModelProperty(value="举报时间")
    private Date createTime;

    @ApiModelProperty(value="")
    private Date updateTime;

    @ApiModelProperty("举报图片列表")
    private List<String> picList;
}