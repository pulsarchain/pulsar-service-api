package com.bosha.finance.api.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import lombok.Getter;

/**
 * @Description 财务账单类型
 * @Author liqingping
 * @Date 2019-2-28 028 9:17
 * @return
 */
@Getter
public enum FinanceServiceTypeEnum {
    RECHARGE(1, "充值"),
    WITHDRAW(2, "提现"),
    INVITATION(3, "获得邀请奖励"),
    ARTICLE_BROSE(4, "浏览资讯收获燃料"),
    ARTICLE_SHARE(5, "分享资讯收获燃料"),
    FLASH_SHARE(6, "浏览快讯收获燃料"),
    PROJECT_CHECK(7, "分享项目收获燃料"),
    TEXT_IMAGE_READ(8, "阅读图文收获燃料"),
    TEXT_IMAGE_FORWARD(9, "转发图文收获燃料"),
    TEXT_IMAGE_COMMENT(10, "留言图文收获燃料"),
    TEXT_IMAGE_LIKE(11, "点赞图文收获燃料"),
    STAR_INCOME_(12,"星系收益"),
    STAR_INCOME_CREATE_CHARITY(13, "星系创建捐赠"),
    STAR_INCOME_JOIN_CHARITY(14, "星系加入捐赠"),
    STAR_INCOME_SYSTEM(15, "星系平台收益"),
    STAR_INCOME_RECOMMEND(16, "星系推荐"),
    LIVE_MINING_SEND_GIFT(17, "打赏主播"),
    LIVE_MINING_VIEW(18, "观看直播"),
    LIVE_MINING_COMMENT(19, "直播留言"),
    LIVE_MINING_LIKE(20, "直播点赞"),
    LIVE_MINING_SHARE(21, "直播转发"),
    LIVE_MINING_GIFT(22, "直播打赏"),
    LIVE_MINING_CHARITY(23, "直播捐赠"),
    LIVE_MINING_SYSTEM(24, "直播平台收益"),

    ;


    private int type;
    private String remark;

    FinanceServiceTypeEnum(int type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    public static FinanceServiceTypeEnum getInstance(int type) {
        FinanceServiceTypeEnum[] values = FinanceServiceTypeEnum.values();
        for (FinanceServiceTypeEnum value : values) {
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
    public static FinanceServiceTypeEnum getEnum(int type) {
        FinanceServiceTypeEnum[] enums = FinanceServiceTypeEnum.values();
        Optional<FinanceServiceTypeEnum> optional = Arrays.stream(enums).filter(item -> item.type == type).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 获取所有的枚举值
     *
     * @return
     */
    public static List<FinanceServiceTypeEnum> selectEnums() {
        List<FinanceServiceTypeEnum> financeServiceTypeEnums = new ArrayList<FinanceServiceTypeEnum>(values().length);
        FinanceServiceTypeEnum[] values = FinanceServiceTypeEnum.values();
        financeServiceTypeEnums = Arrays.asList(values);
        return financeServiceTypeEnums;
    }

}
