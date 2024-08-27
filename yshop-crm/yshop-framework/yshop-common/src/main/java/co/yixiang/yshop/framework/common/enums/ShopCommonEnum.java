package co.yixiang.yshop.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 商城常用枚举
 */
@Getter
@AllArgsConstructor
public enum ShopCommonEnum {

    STORE_MODE_1(1,"本地存储"),
    STORE_MODE_2(2,"云存储"),

    ENABLE_1(1,"开启"),
    ENABLE_2(2,"关闭"),

    EXTRACT_MINUS_1(-1,"提现未通过"),
    EXTRACT_0(0,"提现审核中"),
    EXTRACT_1(1,"提现已完成"),

    IS_FINISH_0(0,"未完成"),
    IS_FINISH_1(1,"已完成"),

    IS_FOREVER_0(0,"不是永久"),
    IS_FOREVER_1(1,"永久"),

    AGREE_1(1,"同意"),
    AGREE_2(2,"拒绝"),

    IS_PERMANENT_0(0,"限制"),
    IS_PERMANENT_1(1,"不限制"),

    IS_STATUS_0(0,"否"),
    IS_STATUS_1(1,"是"),


    IS_PROMOTER_0(0,"默认"),
    IS_PROMOTER_1(1,"是客服"),

    IS_NEW_0(0,"默认"),
    IS_NEW_1(1,"新品"),

    IS_SUB_0(0,"不单独分佣"),
    IS_SUB_1(1,"单独分佣"),


    GRADE_0(0,"一级推荐人"),
    GRADE_1(1,"二级推荐人"),

    REPLY_0(0,"未回复"),
    REPLY_1(1,"已回复"),

    ADD_1(1,"增加"),
    ADD_2(2,"减少"),

    DELETE_0(0,"未删除"),
    DELETE_1(1,"已删除"),

    SHOW_0(0,"不显示"),
    SHOW_1(1,"显示"),

    DEFAULT_0(0,"不是默认"),
    DEFAULT_1(1,"默认");




    private Integer value;
    private String desc;
}
