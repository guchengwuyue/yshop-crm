package co.yixiang.yshop.module.product.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 产品相关规格类型枚举
 */
@Getter
@AllArgsConstructor
public enum DefaultEnum {
    DEFAULT_0(0,"默认值0"),
    DEFAULT_1(1,"默认值1");

    private Integer value;
    private String desc;
}
