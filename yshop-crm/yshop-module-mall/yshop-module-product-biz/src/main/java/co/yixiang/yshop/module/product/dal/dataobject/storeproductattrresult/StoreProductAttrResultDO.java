package co.yixiang.yshop.module.product.dal.dataobject.storeproductattrresult;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 商品属性详情 DO
 *
 * @author yshop
 */
@TableName("yshop_store_product_attr_result")
@KeySequence("yshop_store_product_attr_result_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductAttrResultDO  {

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
     * 商品属性参数
     */
    private String result;
    /**
     * 上次修改时间
     */
    private Date changeTime;

}
