package com.bosha.common.api.entity;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="推送通知消息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushMessage implements Serializable {
    @ApiModelProperty(value="主键id")
    private Long id;

    /**
    * 用户id
    */
    @ApiModelProperty(value="用户id")
    @JsonIgnore
    private Integer userId;

    /**
    * 用户地址
    */
    @ApiModelProperty(value="用户地址")
    private String address;

    /**
    * 标题
    */
    @ApiModelProperty(value="标题")
    private String title;

    /**
    * 内容
    */
    @ApiModelProperty(value="内容")
    private String body;

    /**
    * 附加内容
    */
    @ApiModelProperty(value="附加内容")
    private String extParameters;

    /**
    * 状态：0 未读，1 已读
    */
    @ApiModelProperty(value="状态：0 未读，1 已读")
    private Integer status;

    /**
    * 业务类型：SYSTEM，PRICE，STAR，CHAIN，DAPP 等
    */
    @ApiModelProperty(value="业务类型：SYSTEM，PRICE，STAR，CHAIN，DAPP 等")
    private String type;

    /**
    * 子类型
    */
    @ApiModelProperty(value="子类型")
    private String subType;

    /**
    * 推送类型：MESSAGE 消息，NOTICE 通知
    */
    @ApiModelProperty(value="推送类型：MESSAGE 消息，NOTICE 通知")
    private String pushType;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}