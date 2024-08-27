package co.yixiang.yshop.module.shop.convert.material;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import co.yixiang.yshop.module.shop.controller.admin.material.vo.*;
import co.yixiang.yshop.module.shop.dal.dataobject.material.MaterialDO;

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
