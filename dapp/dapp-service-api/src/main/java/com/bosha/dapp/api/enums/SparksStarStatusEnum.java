package com.bosha.dapp.api.enums;

import com.bosha.commons.exception.BaseException;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: SparksStarStatusEnum
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-26 9:57
 */
@Getter
public enum  SparksStarStatusEnum {

    UN_INVITE_WITNESS(1),
    WITNESSING(2),
    SUCCESS(3),
    OVER(4),
    OFF_LINE(5),

    ;

    private int status;

    SparksStarStatusEnum(int status) {
        this.status = status;
    }

    public static SparksStarStatusEnum getInstance(int status) {
        for (SparksStarStatusEnum value : SparksStarStatusEnum.values()) {
            if (value.getStatus() == status)
                return value;
        }
        throw new BaseException("状态异常");
    }
}
