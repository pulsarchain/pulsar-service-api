package com.bosha.dapp.api.enums;

import com.bosha.commons.exception.BaseException;
import lombok.Getter;

@Getter
public enum WitnessEnum {

    LIGHT(1),
    WIPE(2),
    MAKE(3),
    ORG(4),
    FUNDATION(5),

    ;

    private int type;

    WitnessEnum(int type) {
        this.type = type;
    }

    public static WitnessEnum getInstance(int type) {
        for (WitnessEnum value : WitnessEnum.values()) {
            if (value.getType() == type)
                return value;
        }
        throw new BaseException("状态异常");
    }
}
