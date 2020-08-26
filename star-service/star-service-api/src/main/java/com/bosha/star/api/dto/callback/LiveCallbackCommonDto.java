package com.bosha.star.api.dto.callback;

import com.bosha.star.api.enums.LiveCallbackEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveCallbackCommonDto
 * @Author liqingping
 * @Date 2020-03-27 16:20
 */
@Data
public class LiveCallbackCommonDto {
    @ApiModelProperty("事件通知信息类型：推流事件为1；断流事件为0；录制事件为100；截图事件为200。")
    private Integer event_type;
    @ApiModelProperty("事件通知签名")
    private String sign;
    @ApiModelProperty("事件通知签名过期 UNIX 时间戳")
    private Long t;

    public LiveCallbackEnum getType() {
        return LiveCallbackEnum.getInstance(this.event_type);
    }

    /*
    * t（过期时间）：来自腾讯云的消息通知默认过期时间是10分钟，
    *                           如果一条消息通知中的 t 值所指定的时间已经过期，
    *                           则可以判定这条通知无效，进而可以防止网络重放攻击。
    *                           t 的格式为十进制 UNIX 时间戳，即从1970年1月1日（UTC/GMT 的午夜）开始所经过的秒数。
        sign（安全签名）：sign = MD5（key + t），
        *           腾讯云把加密 key 和 t 进行字符串拼接后通过 MD5 计算得出 sign 值，
        *               并将其放在通知消息里，您的后台服务器在收到通知消息后可以根据同样的算法确认 sign 是否正确，
        *               进而确认消息是否确实来自腾讯云后台。
    * */
}
