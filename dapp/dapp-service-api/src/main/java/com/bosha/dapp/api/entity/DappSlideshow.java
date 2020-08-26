package com.bosha.dapp.api.entity;

import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="Dapp首页轮播图")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DappSlideshow {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 名称
    */
    @ApiModelProperty(value="名称")
    private String name;

    /**
    * 链接地址
    */
    @ApiModelProperty(value="链接地址")
    private String url;

    /**
    * 关联的dapp_id
    */
    @ApiModelProperty(value="关联的dapp_id")
    private Long dappId;

    /**
    * 类型
    */
    @ApiModelProperty(value="类型")
    private Integer type;

    /**
    * 是否显示：0 否，1 是
    */
    @ApiModelProperty(value="是否显示：0 否，1 是")
    private Integer show;

    /**
    * 排序：越小越靠前
    */
    @ApiModelProperty(value="排序：越小越靠前")
    private Integer sort;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}