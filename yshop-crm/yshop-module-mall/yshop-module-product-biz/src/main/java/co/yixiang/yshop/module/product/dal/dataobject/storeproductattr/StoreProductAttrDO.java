package co.yixiang.yshop.module.product.dal.dataobject.storeproductattr;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 商品属性 DO
 *
 * @author yshop
 */
@TableName("yshop_store_product_attr")
@KeySequence("yshop_store_product_attr_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductAttrDO{

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
     * 属性名
     */
    private String attrName;
    /**
     * 属性值
     */
    private String attrValues;

}
