package com.bosha.star.api.dto.callback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveRecordCallbackDto
 * @Author liqingping
 * @Date 2020-03-27 16:46
 */
@Data
public class LiveRecordCallbackDto extends LiveCallbackCommonDto {

    @ApiModelProperty("直播流名称")
    private String stream_id;
    @ApiModelProperty("直播流名称")
    private String channel_id;
    @ApiModelProperty("点播 file ID，在点播平台可以唯一定位一个点播视频文件")
    private String file_id;
    @ApiModelProperty("flv，hls，mp4，aac")
    private String file_format;
    @ApiModelProperty("录制文件起始时间戳")
    private Long start_time;
    @ApiModelProperty("录制文件结束时间戳")
    private Long end_time;
    @ApiModelProperty("录制文件时长，单位秒")
    private Long duration;
    @ApiModelProperty("录制文件大小，单位字节")
    private Long file_size;
    @ApiModelProperty("用户推流 URL 所带参数")
    private String stream_param;
    @ApiModelProperty("录制文件文件下载 URL")
    private String video_url;

    public boolean correctStreamId() {
        if (!stream_id.contains("_"))
            return false;
        String[] split = this.stream_id.split("_");
        return split.length == 2 && StringUtils.isNumeric(split[1]);
    }
}
