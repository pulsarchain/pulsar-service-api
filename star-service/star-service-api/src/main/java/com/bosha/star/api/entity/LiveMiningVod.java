package com.bosha.star.api.entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="直播录制回看")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveMiningVod implements Serializable {
    @ApiModelProperty(value="")
    private Long id;

    /**
    * 直播挖矿的id
    */
    @ApiModelProperty(value="直播挖矿的id")
    private Long liveMiningId;

    /**
    * 流id
    */
    @ApiModelProperty(value="流id")
    private String streamId;

    /**
    * 流id
    */
    @ApiModelProperty(value="流id")
    private String channelId;

    /**
    * 签名
    */
    @ApiModelProperty(value="签名")
    private String sign;

    /**
    * 时间戳
    */
    @ApiModelProperty(value="时间戳")
    private Long t;

    /**
    * 录制开始时间戳
    */
    @ApiModelProperty(value="录制开始时间戳")
    private Long startTime;

    /**
    * 录制结束时间戳
    */
    @ApiModelProperty(value="录制结束时间戳")
    private Long endTime;

    /**
    * flv，hls，mp4，aac
    */
    @ApiModelProperty(value="flv，hls，mp4，aac")
    private String fileFormat;

    /**
    * 录制文件的id
    */
    @ApiModelProperty(value="录制文件的id")
    private String fileId;

    /**
    * 文件大小 单位字节
    */
    @ApiModelProperty(value="文件大小 单位字节")
    private Long fileSize;

    /**
    * 录制文件时长，单位秒
    */
    @ApiModelProperty(value="录制文件时长，单位秒")
    private Long duration;

    /**
     * 推流所带参数
     */
    @ApiModelProperty(value="推流所带参数")
    private String streamParam;

    /**
    * 回看url
    */
    @ApiModelProperty(value="回看url")
    private String videoUrl;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}