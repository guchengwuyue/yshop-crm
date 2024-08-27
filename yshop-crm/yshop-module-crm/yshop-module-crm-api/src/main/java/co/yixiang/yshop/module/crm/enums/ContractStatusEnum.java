package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 合同审核状态枚举
 */
@Getter
@AllArgsConstructor
public enum ContractStatusEnum {
    // '0待审核、1审核中、2审核通过、3审核未通过',
    STATUS_0(0,"待审核"),
    STATUS_1(1,"审核中"),
    STATUS_2(2,"审核通过"),
    STATUS_3(3,"审核未通过");

    private Integer value;
    private String desc;
}
