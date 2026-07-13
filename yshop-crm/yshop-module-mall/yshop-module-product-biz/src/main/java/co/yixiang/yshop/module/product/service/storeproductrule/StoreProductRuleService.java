package co.yixiang.yshop.module.product.service.storeproductrule;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductrule.StoreProductRuleDO;

/**
 * 商品规则值(规格) Service 接口
 *
 * @author yshop
 */
public interface StoreProductRuleService {

    /**
     * 创建商品规则值(规格)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createStoreProductRule(@Valid StoreProductRuleCreateReqVO createReqVO);

    /**
     * 更新商品规则值(规格)
     *
     * @param updateReqVO 更新信息
     */
    void updateStoreProductRule(@Valid StoreProductRuleUpdateReqVO updateReqVO);

    /**
     * 删除商品规则值(规格)
     *
     * @param id 编号
     */
    void deleteStoreProductRule(Integer id);

    /**
     * 获得商品规则值(规格)
     *
     * @param id 编号
     * @return 商品规则值(规格)
     */
    StoreProductRuleDO getStoreProductRule(Integer id);

    /**
     * 获得商品规则值(规格)列表
     *
     * @param ids 编号
     * @return 商品规则值(规格)列表
     */
    List<StoreProductRuleDO> getStoreProductRuleList(Collection<Integer> ids);

    /**
     * 获得商品规则值(规格)分页
     *
     * @param pageReqVO 分页查询
     * @return 商品规则值(规格)分页
     */
    PageResult<StoreProductRuleDO> getStoreProductRulePage(StoreProductRulePageReqVO pageReqVO);

    /**
     * 获得商品规则值(规格)列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 商品规则值(规格)列表
     */
    List<StoreProductRuleDO> getStoreProductRuleList(StoreProductRuleExportReqVO exportReqVO);



}
