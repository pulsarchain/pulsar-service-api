package com.bosha.star.api.dto.web;

import com.bosha.star.api.enums.LiveMiningChatRoomPushEnum;
import lombok.Builder;
import lombok.Data;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ImGroupPushMessage
 * @Author liqingping
 * @Date 2020-04-02 17:16
 */
@Data
@Builder
public class ImGroupPushMessage {

    private LiveMiningChatRoomPushEnum type;

    private Long liveMiningId;

    private Object data;
}
