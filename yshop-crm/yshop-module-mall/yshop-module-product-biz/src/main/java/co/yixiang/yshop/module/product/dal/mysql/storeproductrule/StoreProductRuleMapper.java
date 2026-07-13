package co.yixiang.yshop.module.product.dal.mysql.storeproductrule;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductrule.StoreProductRuleDO;
import org.apache.ibatis.annotations.Mapper;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 商品规则值(规格) Mapper
 *
 * @author yshop
 */
@Mapper
public interface StoreProductRuleMapper extends BaseMapperX<StoreProductRuleDO> {

    default PageResult<StoreProductRuleDO> selectPage(StoreProductRulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StoreProductRuleDO>()
                .likeIfPresent(StoreProductRuleDO::getRuleName, reqVO.getRuleName())
                .orderByDesc(StoreProductRuleDO::getId));
    }

    default List<StoreProductRuleDO> selectList(StoreProductRuleExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StoreProductRuleDO>()
                .likeIfPresent(StoreProductRuleDO::getRuleName, reqVO.getRuleName())
                .orderByDesc(StoreProductRuleDO::getId));
    }



}
