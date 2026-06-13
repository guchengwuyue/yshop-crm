package co.yixiang.yshop.module.shop.convert.materialgroup;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo.MaterialGroupCreateReqVO;
import co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo.MaterialGroupExcelVO;
import co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo.MaterialGroupRespVO;
import co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo.MaterialGroupUpdateReqVO;
import co.yixiang.yshop.module.shop.dal.dataobject.materialgroup.MaterialGroupDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 素材分组 Convert
 *
 * @author yshop
 */
@Mapper
public interface MaterialGroupConvert {

    MaterialGroupConvert INSTANCE = Mappers.getMapper(MaterialGroupConvert.class);

    MaterialGroupDO convert(MaterialGroupCreateReqVO bean);

    MaterialGroupDO convert(MaterialGroupUpdateReqVO bean);

    MaterialGroupRespVO convert(MaterialGroupDO bean);

    List<MaterialGroupRespVO> convertList(List<MaterialGroupDO> list);

    PageResult<MaterialGroupRespVO> convertPage(PageResult<MaterialGroupDO> page);

    List<MaterialGroupExcelVO> convertList02(List<MaterialGroupDO> list);

}
