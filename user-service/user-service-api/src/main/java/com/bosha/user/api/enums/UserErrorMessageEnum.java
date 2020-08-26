package com.bosha.user.api.enums;


import com.bosha.commons.exception.ErrorMsgEnum;

public enum UserErrorMessageEnum implements ErrorMsgEnum {
    /**
     * 用户服务的错误码为 2000-2999
     */

    USER_NOT_FOUND(2000, "USER_NOT_FOUND"),
    GOOGLE_CODE_ERROR(2001, "GOOGLE_CODE_ERROR"),
    INVITE_CODE_ERROR(2002, "INVITE_CODE_ERROR"),
    ADMIN_USER_LOGINING(2003, "ADMIN_USER_LOGINING"),
    ADDRESS_ERROR(2004, "ADDRESS_ERROR"),
    CAN_NOT_INVITE_YOUR_SELF(2005, "CAN_NOT_INVITE_YOUR_SELF"),
    VIEW_CREDIT_SCORE_BEFORE_TRANSFER(2006, "VIEW_CREDIT_SCORE_BEFORE_TRANSFER"),
    VIEW_CREDIT_SCORE_TRANSFER_CONFIRMING(2007, "VIEW_CREDIT_SCORE_TRANSFER_CONFIRMING"),
    AUTHENTICATION_NOT_FOUND(2008,"AUTHENTICATION_NOT_FOUND"),
    USER_FOLLOW_SELF_ERROR(2009,"USER_FOLLOW_SELF_ERROR"),
    USER_FRIEND_SELF_ERROR(2010,"USER_FRIEND_SELF_ERROR"),
    GROUP_NOT_OWNER(2011,"GROUP_NOT_OWNER"),
    GROUP_NOT_FOUND(2012,"GROUP_NOT_FOUND"),
    GROUP_MEMBER_NOT_FOUND(2013,"GROUP_MEMBER_NOT_FOUND"),
    IM_RED_NOT_FOUND(2014,"IM_RED_NOT_FOUND"),
    IM_RED_STATUS_ERROR(2015,"IM_RED_STATUS_ERROR"),
    IM_RED_PEOPLE_NUMBER_ERROR(2016,"IM_RED_PEOPLE_NUMBER_ERROR"),
    IM_RED_IS_JOIN_ERROR(2017,"IM_RED_IS_JOIN_ERROR"),
    IM_RED_HAS_NOT_PAY_ERROR(2018,"IM_RED_HAS_NOT_PAY_ERROR"),
    PLEASE_COMPLETE_SELF_CERTIFICATION_FIRST(2019,"PLEASE_COMPLETE_SELF_CERTIFICATION_FIRST"),
    NO_NEED_RE_AUTHENTICATE(2020,"NO_NEED_RE_AUTHENTICATE"),
    ;
    private int code;
    private String msg;

    UserErrorMessageEnum(int code, String msg) {
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
