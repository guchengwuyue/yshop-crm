package co.yixiang.yshop.module.product.dal.dataobject.storeproductattrvalue;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;

/**
 * 商品属性值 DO
 *
 * @author yshop
 */
@TableName("yshop_store_product_attr_value")
@KeySequence("yshop_store_product_attr_value_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductAttrValueDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品属性索引值 (attr_value|attr_value[|....])
     */
    private String sku;
    /**
     * 属性对应的库存
     */
    private Integer stock;
    /**
     * 销量
     */
    private Integer sales;
    /**
     * 属性金额
     */
    private BigDecimal price;
    /**
     * 图片
     */
    private String image;
    /**
     * 唯一值
     */
    @TableField(value = "`unique`")
    private String unique;
    /**
     * 成本价
     */
    private BigDecimal cost;
    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 原价
     */
    private BigDecimal otPrice;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 体积
     */
    private BigDecimal volume;
    /**
     * 一级返佣
     */
    private BigDecimal brokerage;
    /**
     * 二级返佣
     */
    private BigDecimal brokerageTwo;
    /**
     * 拼团价
     */
    private BigDecimal pinkPrice;
    /**
     * 拼团库存
     */
    private Integer pinkStock;
    /**
     * 秒杀价
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀库存
     */
    private Integer seckillStock;
    /**
     * 需要多少积分兑换
     */
    private Integer integral;

}
