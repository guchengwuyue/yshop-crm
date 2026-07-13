package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 状态枚举
 */
@Getter
@AllArgsConstructor
public enum InvoiceStatusEnum {
    // 状态0未开票1开票中2已开票3开票失败
    STATUS_0(0,"未开票"),
    STATUS_1(1,"开票中"),
    STATUS_2(2,"已开票"),
    STATUS_3(3,"开票失败");

    private Integer value;
    private String desc;
}
