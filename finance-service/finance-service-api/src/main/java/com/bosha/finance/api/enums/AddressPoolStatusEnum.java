package com.bosha.finance.api.enums;

public enum AddressPoolStatusEnum {
    //0:未使用，1 已使用
    NOT_USED(0),
    USED(1);


    private Integer status;


    AddressPoolStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
