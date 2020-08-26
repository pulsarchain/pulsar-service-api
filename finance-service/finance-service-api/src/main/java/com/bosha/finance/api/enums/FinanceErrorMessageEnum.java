package com.bosha.finance.api.enums;


import com.bosha.commons.exception.ErrorMsgEnum;

public enum FinanceErrorMessageEnum implements ErrorMsgEnum {
    /**
     * 用户服务的错误码为 5000-5999
     */

    ADDRESS_POOL_IS_NULL(5000, "ADDRESS_POOL_IS_NULL"),
    COIN_IS_NULL(5001, "COIN_IS_NULL"),
    COIN_BALANCE_IS_NULL(5002,"COIN_BALANCE_IS_NULL"),
    PLATFORM_BALANCE_INSUFFICIENT(5003,"PLATFORM_BALANCE_INSUFFICIENT"),
    GOOGLE_CODE_ERROR(5004,"GOOGLE_CODE_ERROR"),
    COIN_WITHDRAW_STATUS_ERROR(5005,"COIN_WITHDRAW_STATUS_ERROR"),
    COIN_WITHDRAW_BALANCE_MIN_ERROR(5006,"COIN_WITHDRAW_BALANCE_MIN_ERROR"),
    USER_ASSET_ERROR(5007,"USER_ASSET_ERROR"),
    USER_ASSET_BALANCE_ERROR(5008,"USER_ASSET_BALANCE_ERROR"),

    ;
    private int code;
    private String msg;

    FinanceErrorMessageEnum(int code, String msg) {
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
