package co.yixiang.yshop.module.crm.service.crmflow;

import java.util.*;
import jakarta.validation.*;
import co.yixiang.yshop.module.crm.controller.admin.crmflow.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowStepDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;

/**
 * 审批流程 Service 接口
 *
 * @author yshop
 */
public interface CrmFlowService {

    /**
     * 创建审批流程
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFlow(@Valid CrmFlowSaveReqVO createReqVO);

    /**
     * 更新审批流程
     *
     * @param updateReqVO 更新信息
     */
    void updateFlow(@Valid CrmFlowSaveReqVO updateReqVO);

    /**
     * 删除审批流程
     *
     * @param id 编号
     */
    void deleteFlow(Long id);

    /**
     * 获得审批流程
     *
     * @param id 编号
     * @return 审批流程
     */
    CrmFlowDO getFlow(Long id);

    /**
     * 获得审批流程分页
     *
     * @param pageReqVO 分页查询
     * @return 审批流程分页
     */
    PageResult<CrmFlowDO> getFlowPage(CrmFlowPageReqVO pageReqVO);

    // ==================== 子表（审批步骤） ====================

    /**
     * 获得审批步骤列表
     *
     * @param flowId 审批流程ID
     * @return 审批步骤列表
     */
    List<CrmFlowStepDO> getFlowStepListByFlowId(Long flowId);

    /**
     * 获取第一级审核人
     * @param flowType
     * @param stepId 不揍ID！
     * @return
     */
    List<Long> getFlowUserIds(String flowType,Long stepId);

    void deleteFlowStep(Long id);

}