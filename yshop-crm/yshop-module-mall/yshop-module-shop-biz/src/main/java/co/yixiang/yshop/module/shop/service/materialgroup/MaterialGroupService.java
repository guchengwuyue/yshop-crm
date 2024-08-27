package co.yixiang.yshop.module.shop.service.materialgroup;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.shop.controller.admin.materialgroup.vo.*;
import co.yixiang.yshop.module.shop.dal.dataobject.materialgroup.MaterialGroupDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;

/**
 * 素材分组 Service 接口
 *
 * @author yshop
 */
public interface MaterialGroupService {

    /**
     * 创建素材分组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaterialGroup(@Valid MaterialGroupCreateReqVO createReqVO);

    /**
     * 更新素材分组
     *
     * @param updateReqVO 更新信息
     */
    void updateMaterialGroup(@Valid MaterialGroupUpdateReqVO updateReqVO);

    /**
     * 删除素材分组
     *
     * @param id 编号
     */
    void deleteMaterialGroup(Long id);

    /**
     * 获得素材分组
     *
     * @param id 编号
     * @return 素材分组
     */
    MaterialGroupDO getMaterialGroup(Long id);

    /**
     * 获得素材分组列表
     *
     * @param ids 编号
     * @return 素材分组列表
     */
    List<MaterialGroupDO> getMaterialGroupList(Collection<Long> ids);

    /**
     * 获得素材分组分页
     *
     * @param pageReqVO 分页查询
     * @return 素材分组分页
     */
    PageResult<MaterialGroupDO> getMaterialGroupPage(MaterialGroupPageReqVO pageReqVO);

    /**
     * 获得素材分组列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 素材分组列表
     */
    List<MaterialGroupDO> getMaterialGroupList(MaterialGroupExportReqVO exportReqVO);

}
