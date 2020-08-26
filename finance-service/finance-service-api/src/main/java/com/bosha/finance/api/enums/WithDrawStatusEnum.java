package com.bosha.finance.api.enums;

public enum WithDrawStatusEnum {
    //提币状态：0 关闭，1 开启
    CLOSE(0),
    OPEN(1);

    private Integer status;


    WithDrawStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
