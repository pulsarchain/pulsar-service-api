package com.bosha.common.api.enums;

import lombok.Getter;

@Getter
public enum SmsTypeEnum {

    COMMON(0, "通用"),

    ;
    private int type;
    private String desc;

    SmsTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static SmsTypeEnum getInstance(int type) {
        for (SmsTypeEnum value : SmsTypeEnum.values()) {
            if (value.getType() == type)
                return value;
        }
        throw new RuntimeException("错误的短信类型：" + type);
    }

}
