package co.yixiang.yshop.module.pay.convert.merchantdetails;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo.MerchantDetailsCreateReqVO;
import co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo.MerchantDetailsExcelVO;
import co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo.MerchantDetailsRespVO;
import co.yixiang.yshop.module.pay.controller.admin.merchantdetails.vo.MerchantDetailsUpdateReqVO;
import co.yixiang.yshop.module.pay.dal.dataobject.merchantdetails.MerchantDetailsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 支付服务商配置 Convert
 *
 * @author yshop
 */
@Mapper
public interface MerchantDetailsConvert {

    MerchantDetailsConvert INSTANCE = Mappers.getMapper(MerchantDetailsConvert.class);

    MerchantDetailsDO convert(MerchantDetailsCreateReqVO bean);

    MerchantDetailsDO convert(MerchantDetailsUpdateReqVO bean);

    MerchantDetailsRespVO convert(MerchantDetailsDO bean);

    List<MerchantDetailsRespVO> convertList(List<MerchantDetailsDO> list);

    PageResult<MerchantDetailsRespVO> convertPage(PageResult<MerchantDetailsDO> page);

    List<MerchantDetailsExcelVO> convertList02(List<MerchantDetailsDO> list);

}
