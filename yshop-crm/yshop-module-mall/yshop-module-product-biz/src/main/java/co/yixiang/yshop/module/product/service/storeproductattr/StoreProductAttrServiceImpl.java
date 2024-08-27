package co.yixiang.yshop.module.product.service.storeproductattr;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductattr.StoreProductAttrDO;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrvalue.StoreProductAttrValueDO;
import co.yixiang.yshop.module.product.dal.mysql.storeproductattr.StoreProductAttrMapper;
import co.yixiang.yshop.module.product.dal.mysql.storeproductattrvalue.StoreProductAttrValueMapper;
import co.yixiang.yshop.module.product.service.storeproduct.dto.FromatDetailDto;
import co.yixiang.yshop.module.product.service.storeproduct.dto.ProductFormatDto;
import co.yixiang.yshop.module.product.service.storeproductattrresult.StoreProductAttrResultService;
import co.yixiang.yshop.module.product.service.storeproductattrvalue.StoreProductAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.product.enums.ErrorCodeConstants.*;

/**
 * 商品属性 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class StoreProductAttrServiceImpl extends ServiceImpl<StoreProductAttrMapper,StoreProductAttrDO> implements StoreProductAttrService {

    @Resource
    private StoreProductAttrMapper storeProductAttrMapper;
    @Resource
    private StoreProductAttrValueService storeProductAttrValueService;
    @Resource
    private StoreProductAttrValueMapper storeProductAttrValueMapper;
    @Resource
    private StoreProductAttrResultService storeProductAttrResultService;


    @Override
    public void deleteStoreProductAttr(Long id) {
        // 校验存在
        validateStoreProductAttrExists(id);
        // 删除
        storeProductAttrMapper.deleteById(id);
    }

    private void validateStoreProductAttrExists(Long id) {
        if (storeProductAttrMapper.selectById(id) == null) {
            throw exception(STORE_PRODUCT_ATTR_NOT_EXISTS);
        }
    }

    @Override
    public StoreProductAttrDO getStoreProductAttr(Long id) {
        return storeProductAttrMapper.selectById(id);
    }

    @Override
    public List<StoreProductAttrDO> getStoreProductAttrList(Collection<Long> ids) {
        return storeProductAttrMapper.selectBatchIds(ids);
    }


    /**
     * 新增商品属性
     * @param items attr
     * @param attrs value
     * @param productId 商品id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertYxStoreProductAttr(List<FromatDetailDto> items, List<ProductFormatDto> attrs,
                                         Long productId)
    {
        List<StoreProductAttrDO> attrGroup = new ArrayList<>();
        for (FromatDetailDto fromatDetailDto : items) {
            StoreProductAttrDO  storeProductAttr = StoreProductAttrDO.builder()
                    .productId(productId)
                    .attrName(fromatDetailDto.getValue())
                    .attrValues(StrUtil.join(",",fromatDetailDto.getDetail()))
                    .build();

            attrGroup.add(storeProductAttr);
        }

        /*int count = storeProductAttrValueService.count(Wrappers.<YxStoreProductAttrValue>lambdaQuery().eq(YxStoreProductAttrValue::getProductId, productId));
        if (count > 0 ) {
            throw new BadRequestException("该产品已被添加到其他活动,禁止操作!");
        }*/

        List<StoreProductAttrValueDO> valueGroup = new ArrayList<>();
        for (ProductFormatDto productFormatDto : attrs) {

//            if(productFormatDto.getPinkStock()>productFormatDto.getStock() || productFormatDto.getSeckillStock()>productFormatDto.getStock()){
//                throw new BadRequestException("活动商品库存不能大于原有商品库存");
//            }
            List<String> stringList = new ArrayList<>(productFormatDto.getDetail().values());
            Collections.sort(stringList);
            StoreProductAttrValueDO oldAttrValue = storeProductAttrValueService.getOne(new LambdaQueryWrapper<StoreProductAttrValueDO>()
                    .eq(StoreProductAttrValueDO::getSku, productFormatDto.getSku())
                    .eq(StoreProductAttrValueDO::getProductId, productId));

            String unique = IdUtil.simpleUUID();
            if (Objects.nonNull(oldAttrValue)) {
                unique = oldAttrValue.getUnique();
            }

            StoreProductAttrValueDO yxStoreProductAttrValue = StoreProductAttrValueDO.builder()
                    .id(Objects.isNull(oldAttrValue) ? null : oldAttrValue.getId())
                    .productId(productId)
                    .sku(StrUtil.join(",",stringList))
                    .price(BigDecimal.valueOf(productFormatDto.getPrice()))
                    .cost(BigDecimal.valueOf(productFormatDto.getCost()))
                    .otPrice(BigDecimal.valueOf(productFormatDto.getOtPrice()))
                    .unique(unique)
                    .image(productFormatDto.getPic())
                    .barCode(productFormatDto.getBarCode())
                    .weight(BigDecimal.valueOf(productFormatDto.getWeight()))
                    .volume(BigDecimal.valueOf(productFormatDto.getVolume()))
                    .brokerage(BigDecimal.valueOf(productFormatDto.getBrokerage()))
                    .brokerageTwo(BigDecimal.valueOf(productFormatDto.getBrokerageTwo()))
                    .stock(productFormatDto.getStock())
                    .integral(productFormatDto.getIntegral())
                    .pinkPrice(BigDecimal.valueOf(productFormatDto.getPinkPrice()==null?0:productFormatDto.getPinkPrice()))
                    .seckillPrice(BigDecimal.valueOf(productFormatDto.getSeckillPrice()==null?0:productFormatDto.getSeckillPrice()))
                    .pinkStock(productFormatDto.getPinkStock()==null?0:productFormatDto.getPinkStock())
                    .seckillStock(productFormatDto.getSeckillStock()==null?0:productFormatDto.getSeckillStock())
                    .build();

            valueGroup.add(yxStoreProductAttrValue);
        }

        if(attrGroup.isEmpty() || valueGroup.isEmpty()){
            throw exception(STORE_PRODUCT_ATTR_NEED);
        }

        //清理属性
        this.clearProductAttr(productId);

        //批量添加
        this.saveBatch(attrGroup);
        storeProductAttrValueService.saveBatch(valueGroup);

        Map<String,Object> map = new LinkedHashMap<>();
        map.put("attr",items);
        map.put("value",attrs);

        storeProductAttrResultService.insertYxStoreProductAttrResult(map,productId);
    }

    /**
     * 删除YxStoreProductAttrValue表的属性
     * @param productId 商品id
     */
    private void clearProductAttr(Long productId) {
        if(ObjectUtil.isNull(productId)) {
            throw exception(STORE_PRODUCT_NOT_EXISTS);
        }

        storeProductAttrMapper.delete(Wrappers.<StoreProductAttrDO>lambdaQuery()
                .eq(StoreProductAttrDO::getProductId,productId));
        storeProductAttrValueMapper.delete(Wrappers.<StoreProductAttrValueDO>lambdaQuery()
                .eq(StoreProductAttrValueDO::getProductId,productId));

    }


}
