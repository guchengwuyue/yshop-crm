package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 客户类型枚举
 */
@Getter
@AllArgsConstructor
public enum CustomerTypesEnum {
    OPEN("open","公海客户"),
    PRIVATE("private","私有客户");

    private String value;
    private String desc;
}
