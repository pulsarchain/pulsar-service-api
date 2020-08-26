package com.bosha.star.api.enums;


import com.bosha.commons.exception.ErrorMsgEnum;

public enum StarErrorMessageEnum implements ErrorMsgEnum {
    /**
     * 用户服务的错误码为 4000-4999
     */
    ONE_ADDRESS_ONE_STAR(4000,"ONE_ADDRESS_ONE_STAR"),
    STAR_NOT_FOUND(4001,"STAR_NOT_FOUND"),
    GIFT_NOT_EXIST(4002,"GIFT_NOT_EXIST"),
    LIVE_NOT_EXIST(4003,"LIVE_NOT_EXIST"),
    GIFT_BALANCE_NOT_ENOUGH(4004,"GIFT_BALANCE_NOT_ENOUGH"),

    ;
    private int code;
    private String msg;

    StarErrorMessageEnum(int code, String msg) {
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
