package co.yixiang.yshop.module.product.service.storeproductattrresult;

import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrresult.StoreProductAttrResultDO;
import co.yixiang.yshop.module.product.dal.mysql.storeproductattrresult.StoreProductAttrResultMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.product.enums.ErrorCodeConstants.STORE_PRODUCT_ATTR_RESULT_NOT_EXISTS;

/**
 * 商品属性详情 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class StoreProductAttrResultServiceImpl extends ServiceImpl<StoreProductAttrResultMapper, StoreProductAttrResultDO> implements StoreProductAttrResultService {

    @Resource
    private StoreProductAttrResultMapper storeProductAttrResultMapper;


    @Override
    public void deleteStoreProductAttrResult(Long id) {
        // 校验存在
        validateStoreProductAttrResultExists(id);
        // 删除
        storeProductAttrResultMapper.deleteById(id);
    }

    private void validateStoreProductAttrResultExists(Long id) {
        if (storeProductAttrResultMapper.selectById(id) == null) {
            throw exception(STORE_PRODUCT_ATTR_RESULT_NOT_EXISTS);
        }
    }

    @Override
    public StoreProductAttrResultDO getStoreProductAttrResult(Long id) {
        return storeProductAttrResultMapper.selectById(id);
    }

    @Override
    public List<StoreProductAttrResultDO> getStoreProductAttrResultList(Collection<Long> ids) {
        return storeProductAttrResultMapper.selectBatchIds(ids);
    }


    /**
     * 新增商品属性详情
     * @param map map
     * @param productId 商品id
     */
    @Override
    public void insertYxStoreProductAttrResult(Map<String, Object> map, Long productId)
    {
        StoreProductAttrResultDO yxStoreProductAttrResult = new StoreProductAttrResultDO();
        yxStoreProductAttrResult.setProductId(productId);
        yxStoreProductAttrResult.setResult(JSON.toJSONString(map));
        yxStoreProductAttrResult.setChangeTime(new Date());

        long count = this.count(Wrappers.<StoreProductAttrResultDO>lambdaQuery()
                .eq(StoreProductAttrResultDO::getProductId,productId));
        if(count > 0) {
            this.remove(Wrappers.<StoreProductAttrResultDO>lambdaQuery()
                    .eq(StoreProductAttrResultDO::getProductId,productId));
        }

        this.save(yxStoreProductAttrResult);
    }

}
