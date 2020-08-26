package com.bosha.finance.api.enums;

public enum TransactionStatusEnum {
    //状态：1 进行中，2 已完成 ，3 失败，4 审核中，5 已撤销，6 未通过，7 审核通过
    ING(1),
    COMPLETED(2),
    FAILED(3),
    REVIEW_ING(4),
    CANCELLED(5),
    NOT_PASS(6),
    PASSED(7),
    ;

    private Integer status;


    TransactionStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
