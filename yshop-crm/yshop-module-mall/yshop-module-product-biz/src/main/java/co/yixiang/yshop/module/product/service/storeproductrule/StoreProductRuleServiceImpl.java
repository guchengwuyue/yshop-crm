package co.yixiang.yshop.module.product.service.storeproductrule;

import co.yixiang.yshop.module.product.convert.storeproductrule.StoreProductRuleConvert;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.*;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductrule.StoreProductRuleDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.product.dal.mysql.storeproductrule.StoreProductRuleMapper;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.product.enums.ErrorCodeConstants.STORE_PRODUCT_RULE_NOT_EXISTS;


/**
 * 商品规则值(规格) Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class StoreProductRuleServiceImpl implements StoreProductRuleService {

    @Resource
    private StoreProductRuleMapper storeProductRuleMapper;

    @Override
    public Integer createStoreProductRule(StoreProductRuleCreateReqVO createReqVO) {
        // 插入
        StoreProductRuleDO storeProductRule = StoreProductRuleConvert.INSTANCE.convert(createReqVO);
        storeProductRuleMapper.insert(storeProductRule);
        // 返回
        return storeProductRule.getId();
    }

    @Override
    public void updateStoreProductRule(StoreProductRuleUpdateReqVO updateReqVO) {
        // 校验存在
        validateStoreProductRuleExists(updateReqVO.getId());
        // 更新
        StoreProductRuleDO updateObj = StoreProductRuleConvert.INSTANCE.convert(updateReqVO);
        storeProductRuleMapper.updateById(updateObj);
    }

    @Override
    public void deleteStoreProductRule(Integer id) {
        // 校验存在
        validateStoreProductRuleExists(id);
        // 删除
        storeProductRuleMapper.deleteById(id);
    }

    private void validateStoreProductRuleExists(Integer id) {
        if (storeProductRuleMapper.selectById(id) == null) {
            throw exception(STORE_PRODUCT_RULE_NOT_EXISTS);
        }
    }

    @Override
    public StoreProductRuleDO getStoreProductRule(Integer id) {
        return storeProductRuleMapper.selectById(id);
    }

    @Override
    public List<StoreProductRuleDO> getStoreProductRuleList(Collection<Integer> ids) {
        if (ids.isEmpty()) {
            return storeProductRuleMapper.selectList();
        }
        return storeProductRuleMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<StoreProductRuleDO> getStoreProductRulePage(StoreProductRulePageReqVO pageReqVO) {
        return storeProductRuleMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StoreProductRuleDO> getStoreProductRuleList(StoreProductRuleExportReqVO exportReqVO) {
        return storeProductRuleMapper.selectList(exportReqVO);
    }



}
