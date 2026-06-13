package co.yixiang.yshop.module.product.dal.mysql.storeproductrule;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.StoreProductRuleExportReqVO;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.StoreProductRulePageReqVO;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductrule.StoreProductRuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
