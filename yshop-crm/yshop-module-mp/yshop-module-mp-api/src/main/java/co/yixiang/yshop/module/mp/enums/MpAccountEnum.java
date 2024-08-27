package co.yixiang.yshop.module.mp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信账户枚举
 * @author yshop
 */
@Getter
@AllArgsConstructor
public enum MpAccountEnum {
    IS_MAIN_0(0, " 不是主账户"),
    IS_MAIN_1(1, "是主账户"),
    IS_MINI_0(0, "不是小程序"),
    IS_MINI_1(1, "是小程序");

    /**
     * 匹配
     */
    private final Integer value;
    /**
     * 匹配的名字
     */
    private final String name;


}
