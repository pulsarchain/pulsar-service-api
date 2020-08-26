package com.bosha.star.api.entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="直播回看视频拼接确认")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningVodConfirm implements Serializable {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 直播的id
    */
    @ApiModelProperty(value="直播的id")
    private Long liveMiningId;

    /**
    * 状态：1 视频生成中，2 生成成功，3 已过有效期
    */
    @ApiModelProperty(value="状态：1 视频生成中，2 生成成功，3 已过有效期，4 主播未开播，不存在回看")
    private Integer status;

    /**
    * 回看视频的地址
    */
    @ApiModelProperty(value="回看视频的地址")
    private String videoUrl;

    /**
    * 文件类型
    */
    @ApiModelProperty(value="文件类型")
    private String fileType;

    /**
    * 文件id
    */
    @ApiModelProperty(value="文件id")
    private String fileId;

    /**
    * 腾讯云合成任务id
    */
    @ApiModelProperty(value="腾讯云合成任务id")
    private String taskId;

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