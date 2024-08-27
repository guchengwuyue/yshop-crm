package co.yixiang.yshop.module.crm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 线索状态枚举
 */
@Getter
@AllArgsConstructor
public enum CluesStatusEnum {
    STATUS_NEG_1(-1,"无效线索"),
    STATUS_0(0,"未转客户"),
    STATUS_1(1,"转成客户");

    private Integer value;
    private String desc;
}
