package com.bosha.dapp.api.enums;

import com.bosha.commons.exception.BaseException;
import lombok.Getter;

@Getter
public enum DappStatusEnum {

    UNINVITED_WITNESS(1),
    IN_WITNESS(2),
    RELEASE_SUCCESS(3),
    CANCEL(4),
    HIDE(5),
    OFF_LINE(6),

    ;
    private int status;

    DappStatusEnum(int status) {
        this.status = status;
    }

    public static DappStatusEnum getInstance(int status) {
        for (DappStatusEnum value : DappStatusEnum.values()) {
            if (value.getStatus() == status)
                return value;
        }
        throw new BaseException("状态异常");
    }

}
