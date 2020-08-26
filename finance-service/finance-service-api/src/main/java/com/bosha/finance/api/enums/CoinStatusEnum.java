package com.bosha.finance.api.enums;

public enum CoinStatusEnum {
    //0:下架，1 上架
    UPPER(0),
    LOWER(1);

    private Integer status;


    CoinStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
