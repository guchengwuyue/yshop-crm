package co.yixiang.yshop.module.product.service.storeproductattrvalue;

import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrvalue.StoreProductAttrValueDO;
import co.yixiang.yshop.module.product.dal.mysql.storeproductattrvalue.StoreProductAttrValueMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.product.enums.ErrorCodeConstants.STORE_PRODUCT_ATTR_VALUE_NOT_EXISTS;

/**
 * 商品属性值 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class StoreProductAttrValueServiceImpl  extends ServiceImpl<StoreProductAttrValueMapper,StoreProductAttrValueDO> implements StoreProductAttrValueService {

    @Resource
    private StoreProductAttrValueMapper storeProductAttrValueMapper;


    @Override
    public void deleteStoreProductAttrValue(Long id) {
        // 校验存在
        validateStoreProductAttrValueExists(id);
        // 删除
        storeProductAttrValueMapper.deleteById(id);
    }

    private void validateStoreProductAttrValueExists(Long id) {
        if (storeProductAttrValueMapper.selectById(id) == null) {
            throw exception(STORE_PRODUCT_ATTR_VALUE_NOT_EXISTS);
        }
    }

    @Override
    public StoreProductAttrValueDO getStoreProductAttrValue(Long id) {
        return storeProductAttrValueMapper.selectById(id);
    }

    @Override
    public List<StoreProductAttrValueDO> getStoreProductAttrValueList(Collection<Long> ids) {
        return storeProductAttrValueMapper.selectBatchIds(ids);
    }


}
