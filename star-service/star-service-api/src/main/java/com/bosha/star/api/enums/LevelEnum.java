package com.bosha.star.api.enums;

import java.util.Arrays;
import java.util.Optional;


import lombok.Getter;

/**
 * 状态枚举
 * 1、旷工等级
 **/
@Getter
public enum LevelEnum {

    STAR(1),
    DOUBLE_STAR(2),
    GREEN_MAN(3);

    private int level;


    LevelEnum(int level) {
        this.level = level;
    }

    /**
     * 根据状态值获得枚举
     *
     * @param status
     * @return
     */
    public static LevelEnum getEnum(int status) {
        LevelEnum[] enums = LevelEnum.values();
        Optional<LevelEnum> optional = Arrays.stream(enums).filter(item -> item.level == status).findFirst();
        if (optional.isPresent())
            return optional.get();
        throw new RuntimeException("类型错误：" + status);
    }
}
