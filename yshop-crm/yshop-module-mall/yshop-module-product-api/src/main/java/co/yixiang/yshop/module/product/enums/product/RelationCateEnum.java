package co.yixiang.yshop.module.product.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hupeng
 * 产品收藏类型枚举
 */
@Getter
@AllArgsConstructor
public enum RelationCateEnum {
    COMMON("common","普通商品"),
    SECKILL("seckill","秒杀商品"),
    BARGAIN("bargain","砍价商品"),
    PINK("pink","拼团商品");

    private String value;
    private String desc;
}
