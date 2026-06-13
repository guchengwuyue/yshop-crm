package co.yixiang.yshop.module.product.convert.storeproductrule;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.StoreProductRuleCreateReqVO;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.StoreProductRuleExcelVO;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.StoreProductRuleRespVO;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.StoreProductRuleUpdateReqVO;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductrule.StoreProductRuleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品规则值(规格) Convert
 *
 * @author yshop
 */
@Mapper
public interface StoreProductRuleConvert {

    StoreProductRuleConvert INSTANCE = Mappers.getMapper(StoreProductRuleConvert.class);

    StoreProductRuleDO convert(StoreProductRuleCreateReqVO bean);

    StoreProductRuleDO convert(StoreProductRuleUpdateReqVO bean);

    StoreProductRuleRespVO convert(StoreProductRuleDO bean);

    List<StoreProductRuleRespVO> convertList(List<StoreProductRuleDO> list);

    PageResult<StoreProductRuleRespVO> convertPage(PageResult<StoreProductRuleDO> page);

    List<StoreProductRuleExcelVO> convertList02(List<StoreProductRuleDO> list);

}
