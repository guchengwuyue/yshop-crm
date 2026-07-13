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
     * 获取审核流程
     * @param flowType
     * @param dataId 数据ID！
     * @return
     */
    List<CrmFlowStepRespVO> getFlowUserIds(String flowType,Long dataId);

    /**
     * 删除流程步骤
     * @param id
     */
    void deleteFlowStep(Long id);

    /**
     * 发送下个审核步骤
     * @param flowType 流程类型
     * @param dataId 关联 数据ID 比如合同 或者 收款
     * @param checkedUserId 已审核人
     * @return CrmFlowStepResultVO
     */
    CrmFlowStepResultVO sendNextStep(String flowType,Long dataId,String checkedUserId);

    /**
     * 添加通知消息
     * @param type 通知类型
     * @param RelationId 数据ID
     * @param toUserId 接收人
     * @param fromUserId 发送人
     * @param content 内容
     */
    void addMessage(String type,Long RelationId,Long toUserId,Long fromUserId,String content);




}