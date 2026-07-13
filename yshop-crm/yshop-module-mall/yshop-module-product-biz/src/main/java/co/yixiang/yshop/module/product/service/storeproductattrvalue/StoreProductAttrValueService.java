package co.yixiang.yshop.module.product.service.storeproductattrvalue;

import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrvalue.StoreProductAttrValueDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 商品属性值 Service 接口
 *
 * @author yshop
 */
public interface StoreProductAttrValueService extends IService<StoreProductAttrValueDO> {


    /**
     * 删除商品属性值
     *
     * @param id 编号
     */
    void deleteStoreProductAttrValue(Long id);

    /**
     * 获得商品属性值
     *
     * @param id 编号
     * @return 商品属性值
     */
    StoreProductAttrValueDO getStoreProductAttrValue(Long id);

    /**
     * 获得商品属性值列表
     *
     * @param ids 编号
     * @return 商品属性值列表
     */
    List<StoreProductAttrValueDO> getStoreProductAttrValueList(Collection<Long> ids);


}
