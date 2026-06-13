package co.yixiang.yshop.module.shop.convert.material;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.shop.controller.admin.material.vo.MaterialCreateReqVO;
import co.yixiang.yshop.module.shop.controller.admin.material.vo.MaterialExcelVO;
import co.yixiang.yshop.module.shop.controller.admin.material.vo.MaterialRespVO;
import co.yixiang.yshop.module.shop.controller.admin.material.vo.MaterialUpdateReqVO;
import co.yixiang.yshop.module.shop.dal.dataobject.material.MaterialDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 素材库 Convert
 *
 * @author yshop
 */
@Mapper
public interface MaterialConvert {

    MaterialConvert INSTANCE = Mappers.getMapper(MaterialConvert.class);

    MaterialDO convert(MaterialCreateReqVO bean);

    MaterialDO convert(MaterialUpdateReqVO bean);

    MaterialRespVO convert(MaterialDO bean);

    List<MaterialRespVO> convertList(List<MaterialDO> list);

    PageResult<MaterialRespVO> convertPage(PageResult<MaterialDO> page);

    List<MaterialExcelVO> convertList02(List<MaterialDO> list);

}
