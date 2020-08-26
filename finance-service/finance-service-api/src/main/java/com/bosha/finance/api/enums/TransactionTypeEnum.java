package com.bosha.finance.api.enums;

public enum TransactionTypeEnum {
    //类型：1 转入，2 转出
    INTO(1),
    OUT(2);


    private Integer type;


    TransactionTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
