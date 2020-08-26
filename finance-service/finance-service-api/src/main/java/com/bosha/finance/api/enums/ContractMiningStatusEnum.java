package com.bosha.finance.api.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description 财务账单类型
 * @Author liqingping
 * @Date 2019-2-28 028 9:17
 * @return
 */
@Getter
public enum ContractMiningStatusEnum {
    SUCCESS(1),//成功
    ING(0),//处理中
    ;


    private int status;

    ContractMiningStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
