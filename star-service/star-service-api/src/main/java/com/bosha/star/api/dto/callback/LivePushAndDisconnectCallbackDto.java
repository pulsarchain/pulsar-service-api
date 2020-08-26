package com.bosha.star.api.dto.callback;

import java.io.Serializable;


import com.bosha.star.api.enums.LiveCallbackErrorMsgEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveStartPushDto
 * @Author liqingping
 * @Date 2020-03-27 16:19
 */

@Data
public class LivePushAndDisconnectCallbackDto extends LiveCallbackCommonDto implements Serializable {
    private static final long serialVersionUID = -5437780331806142114L;

    @ApiModelProperty("推流域名")
    private String app;
    @ApiModelProperty("推流路径")
    private String appname;
    @ApiModelProperty("直播流id")
    private String stream_id;
    @ApiModelProperty("直播流id")
    private String channel_id;
    @ApiModelProperty("时间产生的UNIX时间戳")
    private Long event_time;
    @ApiModelProperty("消息序列号，标识一次推流活动，一次推流活动会产生相同序列号的推流和断流消息")
    private String sequence;
    @ApiModelProperty("直播接入点的 IP")
    private String node;
    @ApiModelProperty("用户推流 IP")
    private String user_ip;
    @ApiModelProperty("用户推流 URL 所带参数")
    private String stream_param;
    @ApiModelProperty("断流事件通知推流时长，单位毫秒")
    private String push_duration;
    @ApiModelProperty("推断流错误码")
    private Integer errcode;
    @ApiModelProperty("推断流错误描述")
    private String errmsg;

    /*
            * 错误码	错误描述	错误原因
                1	recv rtmp deleteStream	主播端主动断流
                2	recv rtmp closeStream	主播端主动断流
                3	recv() return 0	主播端主动断开 TCP 连接
                4	recv() return error	主播端 TCP 连接异常
                7	rtmp message large than 1M	收到流数据异常
                其他	直播服务内部异常	如需处理请联系腾讯商务人员或者提交工单
    * */
    public String getErrorMsg() {
        if (this.errcode == 0)
            return "";
        return LiveCallbackErrorMsgEnum.MSG.getOrDefault(this.errcode, "未知错误，errcode=" + this.errcode);
    }

    public boolean correctStreamId() {
        if (!stream_id.contains("_"))
            return false;
        String[] split = this.stream_id.split("_");
        return split.length == 2 && StringUtils.isNumeric(split[1]);
    }
}
