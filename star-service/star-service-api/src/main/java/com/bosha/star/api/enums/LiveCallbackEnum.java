package com.bosha.star.api.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveCallbackEnum
 * @Author liqingping
 * @Date 2020-03-27 16:22
 */

public enum LiveCallbackEnum {

    UNKNOWN(-1),
    START_PUSH(1),
    DISCONNECT(0),
    RECORD(100),

    ;

    private int eventType;

    LiveCallbackEnum(int eventType) {
        this.eventType = eventType;
    }

    public static LiveCallbackEnum getInstance(int eventType) {
        LiveCallbackEnum[] enums = LiveCallbackEnum.values();
        Optional<LiveCallbackEnum> optional = Arrays.stream(enums).filter(item -> item.eventType == eventType).findFirst();
        return optional.orElse(UNKNOWN);
    }
}
