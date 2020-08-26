package com.bosha.common.api.enums;


import com.bosha.commons.exception.ErrorMsgEnum;

public enum CommonsErrorMessageEnum implements ErrorMsgEnum {
    /**
     * commons服务的错误码为 1100-1999
     */

    MINING_RULE_NOT_EXITS(1100, "MINING_RULE_NOT_EXITS"),

    ;
    private int code;
    private String msg;

    CommonsErrorMessageEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}
