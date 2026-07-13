package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 商机状态枚举
 */
@Getter
@AllArgsConstructor
public enum BusinessStatusEnum {
    //成交状态0洽淡中，1成交2失败3无效
    STATUS_0(0,"洽淡中"),
    STATUS_1(1,"成交"),
    STATUS_2(2,"失败"),
    STATUS_3(3,"无效");

    private Integer value;
    private String desc;
}
