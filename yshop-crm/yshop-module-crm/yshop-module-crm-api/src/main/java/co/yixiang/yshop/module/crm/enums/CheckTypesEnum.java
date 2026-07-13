package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 审核类型枚举
 */
@Getter
@AllArgsConstructor
public enum CheckTypesEnum {
    AGREE("agree","同意"),
    REFUSE("refuse","拒绝");

    private String value;
    private String desc;
}
