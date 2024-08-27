package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 关系枚举
 */
@Getter
@AllArgsConstructor
public enum RelationEnum {
    MY("my","本人"),
    SUB("sub","下属");

    private String value;
    private String desc;
}
