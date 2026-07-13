package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 审核状态枚举
 */
@Getter
@AllArgsConstructor
public enum FlowLogStatusEnum {
    // '0-待审核 1-审核通过,2-审核拒绝 3-撤销',
    STATUS_0(0,"待审核"),
    STATUS_1(1,"审核通过"),
    STATUS_2(2,"审核拒绝"),
    STATUS_5(5,"审核中"),
    STATUS_3(3,"撤销");

    private Integer value;
    private String desc;
}
