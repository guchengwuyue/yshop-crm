package co.yixiang.yshop.module.product.dal.mysql.storeproduct;

import java.util.*;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.product.dal.dataobject.storeproduct.StoreProductDO;
import co.yixiang.yshop.module.product.enums.product.DefaultEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 商品 Mapper
 *
 * @author yshop
 */
@Mapper
public interface StoreProductMapper extends BaseMapperX<StoreProductDO> {

    default PageResult<StoreProductDO> selectPage(StoreProductPageReqVO reqVO) {
        LambdaQueryWrapperX<StoreProductDO> wrapper = new LambdaQueryWrapperX<>();
        wrapper.likeIfPresent(StoreProductDO::getStoreName, reqVO.getStoreName())
                .likeIfPresent(StoreProductDO::getShopName, reqVO.getShopName())
                .eqIfPresent(StoreProductDO::getIsPostage, reqVO.getIsPostage())
                .eqIfPresent(StoreProductDO::getCateId,reqVO.getCateId())
                .orderByDesc(StoreProductDO::getId);

        wrapper.eq(StoreProductDO::getIsShow,Convert.toInt(reqVO.getIsShow()));
        if(DefaultEnum.DEFAULT_0.getValue().equals(Convert.toInt(reqVO.getStock()))){
            wrapper.eq(StoreProductDO::getStock,DefaultEnum.DEFAULT_0.getValue());
        }
//
//        if(CollUtil.isNotEmpty(reqVO.getCatIds())){
//            wrapper.in(StoreProductDO::getCateId,reqVO.getCatIds());
//        }

        return selectPage(reqVO, wrapper);

    }

    default List<StoreProductDO> selectList(StoreProductExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StoreProductDO>()
                .likeIfPresent(StoreProductDO::getStoreName, reqVO.getStoreName())
                .eqIfPresent(StoreProductDO::getIsPostage, reqVO.getIsPostage())
                .orderByDesc(StoreProductDO::getId));
    }

    @Update("update yshop_store_product set is_show = #{status} where id = #{id}")
    void updateOnsale(@Param("status") Integer status, @Param("id") Long id);


    /**
     * 正常商品库存 加库存 减销量
     * @param num
     * @param productId
     * @return
     */
    @Update("update yshop_store_product set stock=stock+#{num}, sales=sales-#{num}" +
            " where id=#{productId}")
    int incStockDecSales(@Param("num") Integer num,@Param("productId") Long productId);

    /**
     * 正常商品库存 减库存 加销量
     * @param num
     * @param productId
     * @return
     */
    @Update("update yshop_store_product set stock=stock-#{num}, sales=sales+#{num}" +
            " where id=#{productId} and stock >= #{num}")
    int decStockIncSales(@Param("num") Integer num,@Param("productId") Long productId);

}
