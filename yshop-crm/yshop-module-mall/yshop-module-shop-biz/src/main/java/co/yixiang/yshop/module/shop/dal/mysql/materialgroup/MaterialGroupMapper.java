package co.yixiang.yshop.module.shop.dal.mysql.materialgroup;

import java.util.*;

import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.mybatis.core.mapper.BaseMapperX;
import co.yixiang.yshop.module.shop.dal.dataobject.materialgroup.MaterialGroupDO;
import org.apache.ibatis.annotations.Mapper;
import co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo.*;

/**
 * 素材分组 Mapper
 *
 * @author yshop
 */
@Mapper
public interface MaterialGroupMapper extends BaseMapperX<MaterialGroupDO> {

    default PageResult<MaterialGroupDO> selectPage(MaterialGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaterialGroupDO>()
                .betweenIfPresent(MaterialGroupDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(MaterialGroupDO::getName, reqVO.getName())
                .orderByDesc(MaterialGroupDO::getId));
    }

    default List<MaterialGroupDO> selectList(MaterialGroupExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MaterialGroupDO>()
                .betweenIfPresent(MaterialGroupDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(MaterialGroupDO::getName, reqVO.getName())
                .orderByDesc(MaterialGroupDO::getId));
    }

}
