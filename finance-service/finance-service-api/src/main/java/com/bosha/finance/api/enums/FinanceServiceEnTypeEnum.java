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
public enum FinanceServiceEnTypeEnum {
    RECHARGE(1, "recharge"),
    WITHDRAW(2, "withdrawal"),
    INVITATION(3, "invitation reward"),
    ARTICLE_BROSE(4, "browse information"),
    ARTICLE_SHARE(5, "share information"),
    FLASH_SHARE(6, "browse news"),
    PROJECT_CHECK(7, "sharing project"),
    TEXT_IMAGE_READ(8, "reading textImg"),
    TEXT_IMAGE_FORWARD(9, "forwarding textImg"),
    TEXT_IMAGE_COMMENT(10, "comment textImg"),
    TEXT_IMAGE_LIKE(11, "like textImg"),
    STAR_INCOME_(12,"galaxy"),
    STAR_INCOME_CREATE_CHARITY(13, "galaxy create"),
    STAR_INCOME_JOIN_CHARITY(14, "galaxy join"),
    STAR_INCOME_SYSTEM(15, "galaxy platform"),
    STAR_INCOME_RECOMMEND(16, "galaxy recommend"),
    LIVE_MINING_SEND_GIFT(17, "reward anchor"),
    LIVE_MINING_VIEW(18, "watch live"),
    LIVE_MINING_COMMENT(19, "comment live"),
    LIVE_MINING_LIKE(20, "like live"),
    LIVE_MINING_SHARE(21, "forwarding live"),
    LIVE_MINING_GIFT(22, "reward live"),
    LIVE_MINING_CHARITY(23, "donation live"),
    LIVE_MINING_SYSTEM(24, "live platform"),

    ;


    private int type;
    private String remark;

    FinanceServiceEnTypeEnum(int type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    public static FinanceServiceEnTypeEnum getInstance(int type) {
        FinanceServiceEnTypeEnum[] values = FinanceServiceEnTypeEnum.values();
        for (FinanceServiceEnTypeEnum value : values) {
            if (value.type == type)
                return value;
        }
        throw new RuntimeException("错误的类型：" + type);
    }

    /**
     * 根据状态值获得枚举
     *
     * @param
     * @return
     */
    public static FinanceServiceEnTypeEnum getEnum(int type) {
        FinanceServiceEnTypeEnum[] enums = FinanceServiceEnTypeEnum.values();
        Optional<FinanceServiceEnTypeEnum> optional = Arrays.stream(enums).filter(item -> item.type == type).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 获取所有的枚举值
     *
     * @return
     */
    public static List<FinanceServiceEnTypeEnum> selectEnums() {
        List<FinanceServiceEnTypeEnum> financeServiceTypeEnums = new ArrayList<FinanceServiceEnTypeEnum>(values().length);
        FinanceServiceEnTypeEnum[] values = FinanceServiceEnTypeEnum.values();
        financeServiceTypeEnums = Arrays.asList(values);
        return financeServiceTypeEnums;
    }

}
