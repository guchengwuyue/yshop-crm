package co.yixiang.yshop.module.product.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 产品收藏类型枚举
 */
@Getter
@AllArgsConstructor
public enum RelationEnum {
    COLLECT("collect","收藏"),
    FOOT("foot","足迹");

    private String value;
    private String desc;
}
