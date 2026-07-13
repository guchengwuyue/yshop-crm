package co.yixiang.yshop.module.product.convert.storeproduct;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.StoreProductCreateReqVO;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.StoreProductExcelVO;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.StoreProductRespVO;
import co.yixiang.yshop.module.product.controller.admin.storeproduct.vo.StoreProductUpdateReqVO;
import co.yixiang.yshop.module.product.dal.dataobject.storeproduct.StoreProductDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品 Convert
 *
 * @author yshop
 */
@Mapper
public interface StoreProductConvert {

    StoreProductConvert INSTANCE = Mappers.getMapper(StoreProductConvert.class);

    StoreProductDO convert(StoreProductCreateReqVO bean);

    StoreProductDO convert(StoreProductUpdateReqVO bean);

    StoreProductRespVO convert(StoreProductDO bean);


    List<StoreProductRespVO> convertList(List<StoreProductDO> list);

    PageResult<StoreProductRespVO> convertPage(PageResult<StoreProductDO> page);

    List<StoreProductExcelVO> convertList02(List<StoreProductDO> list);

}
