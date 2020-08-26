package com.bosha.common.api.enums;

public enum DictTypeEnum {

    all("全部"),
    market("行情"),
    coin("币种"),
    article("文章"),
    flash("快讯"),
    help_center("帮助中心"),
    chain("公链"),
    instructions("使用说明"),

    ;

    public String desc;

    DictTypeEnum(String desc) {
        this.desc = desc;
    }

}
