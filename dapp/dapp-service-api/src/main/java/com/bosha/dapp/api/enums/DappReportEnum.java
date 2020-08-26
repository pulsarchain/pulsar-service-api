package com.bosha.dapp.api.enums;

import com.bosha.commons.exception.BaseException;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: DappReportEnum
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-05-12 19:56
 */
@Getter
public enum DappReportEnum {

    VOTING(0),
    NOT_TRUE(1),
    MODIFYING(2),
    MODIFYED(3),
    OVER_PUBLIC_TIME(4),
    OVER_MODIFY_TIME(5),

    ;

    private int status;

    DappReportEnum(int status) {
        this.status = status;
    }

    public static DappReportEnum getInstance(int status) {
        for (DappReportEnum value : DappReportEnum.values()) {
            if (value.getStatus() == status)
                return value;
        }
        throw new BaseException("状态异常");
    }
}
