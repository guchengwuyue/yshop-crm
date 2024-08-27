package co.yixiang.yshop.module.product.convert.category;

import co.yixiang.yshop.module.product.controller.admin.category.vo.ProductCategoryCreateReqVO;
import co.yixiang.yshop.module.product.controller.admin.category.vo.ProductCategoryRespVO;
import co.yixiang.yshop.module.product.controller.admin.category.vo.ProductCategoryUpdateReqVO;
import co.yixiang.yshop.module.product.dal.dataobject.category.ProductCategoryDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品分类 Convert
 *
 * @author yshop
 */
@Mapper
public interface ProductCategoryConvert {

    ProductCategoryConvert INSTANCE = Mappers.getMapper(ProductCategoryConvert.class);

    ProductCategoryDO convert(ProductCategoryCreateReqVO bean);

    ProductCategoryDO convert(ProductCategoryUpdateReqVO bean);

    ProductCategoryRespVO convert(ProductCategoryDO bean);

    List<ProductCategoryRespVO> convertList(List<ProductCategoryDO> list);

}
