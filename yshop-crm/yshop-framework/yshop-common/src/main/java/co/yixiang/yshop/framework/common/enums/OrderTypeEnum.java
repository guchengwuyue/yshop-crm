package co.yixiang.yshop.framework.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 类型枚举
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {
    TYPE_WORK("work","工作台"),
    TYPE_COMMON("common","普通");

    private String value;
    private String desc;
}
