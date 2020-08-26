package com.bosha.star.api.enums;

import java.util.Arrays;
import java.util.Optional;


import lombok.Getter;

@Getter
public enum LiveMiningTypeEnum {


    VIEW(1),
    COMMENT(2),
    LIKE(3),
    SHARE(4),
    GIFT(5),

    ;

    private int type;

    LiveMiningTypeEnum(int type) {
        this.type = type;
    }

    public static LiveMiningTypeEnum getInstance(int type) {
        LiveMiningTypeEnum[] enums = LiveMiningTypeEnum.values();
        Optional<LiveMiningTypeEnum> optional = Arrays.stream(enums).filter(item -> item.type == type).findFirst();
        if (optional.isPresent())
            return optional.get();
        throw new RuntimeException("类型错误：" + type);
    }

}
