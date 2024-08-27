package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 枚举
 */
@Getter
@AllArgsConstructor
public enum FlowStepEnum {
    TYPE_2(2,"指定人员"),
    TYPE_3(3,"指定部门");

    private Integer value;
    private String desc;
}
