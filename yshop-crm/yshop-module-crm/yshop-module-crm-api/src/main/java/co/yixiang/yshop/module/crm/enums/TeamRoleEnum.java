package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 团队角色枚举
 */
@Getter
@AllArgsConstructor
public enum TeamRoleEnum {
    ROLE_1(1,"负责人"),
    ROLE_2(2,"普通员工");

    private Integer value;
    private String desc;
}
