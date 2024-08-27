package co.yixiang.yshop.module.product.dal.mysql.category;

import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.product.controller.admin.category.vo.ProductCategoryListReqVO;
import co.yixiang.yshop.module.product.dal.dataobject.category.ProductCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类 Mapper
 *
 * @author yshop
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapperX<ProductCategoryDO> {

    default List<ProductCategoryDO> selectList(ProductCategoryListReqVO listReqVO) {
        return selectList(new LambdaQueryWrapperX<ProductCategoryDO>()
                .likeIfPresent(ProductCategoryDO::getName, listReqVO.getName())
                .likeIfPresent(ProductCategoryDO::getShopName, listReqVO.getShopName())
                .eqIfPresent(ProductCategoryDO::getShopId,listReqVO.getShopId())
                .orderByDesc(ProductCategoryDO::getId));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(ProductCategoryDO::getParentId, parentId);
    }

    default List<ProductCategoryDO> selectListByStatus(Integer status,Integer shopId) {
        return selectList(new LambdaQueryWrapperX<ProductCategoryDO>()
                .eqIfPresent(ProductCategoryDO::getStatus, status)
                .eqIfPresent(ProductCategoryDO::getShopId, shopId)
                .orderByDesc(ProductCategoryDO::getId));
    }

}
