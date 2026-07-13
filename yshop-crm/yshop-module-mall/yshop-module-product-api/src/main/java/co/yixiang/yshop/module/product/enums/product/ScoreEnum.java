package co.yixiang.yshop.module.product.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 产品相关规格类型枚举
 */
@Getter
@AllArgsConstructor
public enum ScoreEnum {
    DEFAULT_0(0,"0分数"),
    DEFAULT_1(1,"1分数"),
    DEFAULT_2(2,"2分数"),
    DEFAULT_3(3,"3分数"),
    DEFAULT_4(4,"4分数"),
    DEFAULT_5(5,"5分数");

    private Integer value;
    private String desc;
}
