package co.yixiang.yshop.module.product.convert.storeproductrule;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import co.yixiang.yshop.module.product.controller.admin.storeproductrule.vo.*;
import co.yixiang.yshop.module.product.dal.dataobject.storeproductrule.StoreProductRuleDO;

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
