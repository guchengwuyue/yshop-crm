package co.yixiang.yshop.module.product.dal.mysql.storeproductattrvalue;

import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrvalue.StoreProductAttrValueDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 商品属性值 Mapper
 *
 * @author yshop
 */
@Mapper
public interface StoreProductAttrValueMapper extends BaseMapperX<StoreProductAttrValueDO> {

    /**
     * 普通商品 减库存 加销量
     * @param num
     * @param productId
     * @param unique
     * @return
     */
    @Update("update yshop_store_product_attr_value set stock=stock-#{num}, sales=sales+#{num}" +
            " where product_id=#{productId} and `unique`=#{unique} and stock >= #{num}")
    int decStockIncSales(@Param("num") Integer num, @Param("productId") Long productId,
                         @Param("unique")  String unique);

    /**
     * 正常商品 加库存 减销量
     * @param num
     * @param productId
     * @param unique
     * @return
     */
    @Update("update yshop_store_product_attr_value set stock=stock+#{num}, sales=sales-#{num}" +
            " where product_id=#{productId} and `unique`=#{unique}")
    int incStockDecSales(@Param("num") Integer num,@Param("productId") Long productId,
                         @Param("unique")  String unique);

}
