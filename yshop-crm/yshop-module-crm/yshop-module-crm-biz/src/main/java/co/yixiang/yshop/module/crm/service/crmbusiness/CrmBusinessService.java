package co.yixiang.yshop.module.crm.service.crmbusiness;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.crmbusiness.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessProductDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;

/**
 * 商机 Service 接口
 *
 * @author yshop
 */
public interface CrmBusinessService {

    /**
     * 创建商机
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createBusiness(@Valid CrmBusinessSaveReqVO createReqVO);

    /**
     * 更新商机
     *
     * @param updateReqVO 更新信息
     */
    void updateBusiness(@Valid CrmBusinessSaveReqVO updateReqVO);

    /**
     * 删除商机
     *
     * @param id 编号
     */
    void deleteBusiness(Long id);

    /**
     * 获得商机
     *
     * @param id 编号
     * @return 商机
     */
    CrmBusinessRespVO getBusiness(Long id);

    /**
     * 获得商机分页
     *
     * @param pageReqVO 分页查询
     * @return 商机分页
     */
    PageResult<CrmBusinessRespVO> getBusinessPage(CrmBusinessPageReqVO pageReqVO);

    // ==================== 子表（商机产品关联） ====================

    /**
     * 获得商机产品关联列表
     *
     * @param businessId 商机id
     * @return 商机产品关联列表
     */
    List<CrmBusinessProductDO> getBusinessProductListByBusinessId(Long businessId);

}