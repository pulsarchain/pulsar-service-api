package com.bosha.common.api.enums;


import lombok.Getter;

@Getter
public class PushMessageSubTypeEnum {

    @Getter
    public enum System {
        /*辅助认证*/
        AUXILIARY_CERTIFICATION,
        /*公链动态*/
        CHAIN_DYNAMIC,
        /*文章发布*/
        ARTICLES_PUBLISHED,
        /*直播创建区块确认中*/
        LIVE_CREATE_CONFIRMING,
        /*直播创建成功*/
        LIVE_CREATE_SUCCESS,
        /*直播邀请加入*/
        LIVE_INVITE,
        /*开播提醒*/
        LIVE_NOTICE_START,
        /*直播结束提醒*/
        LIVE_NOTICE_END,
        /*购买礼物确认中*/
        LIVE_GIFT_CONFIRMING,
        /*购买的礼物到账*/
        LIVE_GIFT_ARRIVAL,

        ;
    }

    @Getter
    public enum Price {

        ;
    }

    @Getter
    public enum Star {
        /*文章*/
        @Deprecated
        ARTICLE,
        /*快讯*/
        FLASH,
        /*项目*/
        PROJECT,
        /*星系创建区块确认中*/
        STAR_CREATE_CONFIRMING,
        /*星系创建成功*/
        STAR_CREATE_SUCCESS,
        /*加入星系区块确认*/
        STAR_JOIN_CONFIRMING,
        /*加入星系成功*/
        STAR_JOIN_SUCCESS,
        /*邀请加入星系*/
        STAR_INVITE,

        ;
    }

    @Getter
    public enum Chain {
        /*波霎链到账通知*/
        PUL_ARRIVAL_NOTICE,
        CONTRACT_COIN_PUSH_NOTICE,
        ;
    }

    @Getter
    public enum Dapp {
        /*发布dapp见证*/
        RELEASE_WITNESS,
        /*dapp发布成功*/
        RELEASE_SUCCESS,
        /*举报提醒*/
        REPORT_NOTICE,
        /*举报不真实*/
        REPORT_NOT_TRUE,
        /*dapp未整改隐藏*/
        HIDE,
        /*星星之火-邀请见证*/
        SPARKS_INVITE_WITNESS,
        /*机构邀请见证*/
        ORG_INVITE_WITNESS,
        /*基金邀请见证*/
        FUNDATION_INVITE_WITNESS,
        /*爱心捐赠发布*/
        DONATE_RELEASE,
        /*基金创建*/
        FUNDATION_RELEASE,
        /*活动创建*/
        ACTIVITY_RELEASE,
        /*爱心捐赠购买/领取 */
        DONATE_SUCCESS,

        ;
    }

    @Getter
    public enum Text {
        /*文章*/
        ARTICLE,
        ;
    }


}
