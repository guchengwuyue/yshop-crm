package co.yixiang.yshop.module.crm.service.crmflow;

import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.module.crm.enums.FlowStepEnum;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import co.yixiang.yshop.module.system.dal.mysql.user.AdminUserMapper;
import co.yixiang.yshop.module.system.service.user.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import co.yixiang.yshop.module.crm.controller.admin.crmflow.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowStepDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;

import co.yixiang.yshop.module.crm.dal.mysql.crmflow.CrmFlowMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflow.CrmFlowStepMapper;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.*;

/**
 * 审批流程 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmFlowServiceImpl implements CrmFlowService {

    @Resource
    private CrmFlowMapper flowMapper;
    @Resource
    private CrmFlowStepMapper flowStepMapper;
    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private AdminUserService adminUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFlow(CrmFlowSaveReqVO createReqVO) {
        // 插入
        CrmFlowDO flow = BeanUtils.toBean(createReqVO, CrmFlowDO.class);
        flowMapper.insert(flow);

        // 插入子表
        createFlowStepList(flow.getId(), createReqVO.getFlowSteps());
        // 返回
        return flow.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFlow(CrmFlowSaveReqVO updateReqVO) {
        // 校验存在
        validateFlowExists(updateReqVO.getId());
        // 更新
        CrmFlowDO updateObj = BeanUtils.toBean(updateReqVO, CrmFlowDO.class);
        flowMapper.updateById(updateObj);

        // 更新子表
        updateFlowStepList(updateReqVO.getId(), updateReqVO.getFlowSteps());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFlow(Long id) {
        // 校验存在
        validateFlowExists(id);
        // 删除
        flowMapper.deleteById(id);

        // 删除子表
        deleteFlowStepByFlowId(id);
    }

    private void validateFlowExists(Long id) {
        if (flowMapper.selectById(id) == null) {
            throw exception(FLOW_NOT_EXISTS);
        }
    }

    @Override
    public CrmFlowDO getFlow(Long id) {
        return flowMapper.selectById(id);
    }

    @Override
    public PageResult<CrmFlowDO> getFlowPage(CrmFlowPageReqVO pageReqVO) {
        return flowMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（审批步骤） ====================

    @Override
    public List<CrmFlowStepDO> getFlowStepListByFlowId(Long flowId) {
        return flowStepMapper.selectListByFlowId(flowId);
    }

    @Override
    public List<Long> getFlowUserIds(String flowType,Long stepId) {
        List<Long> list = new  ArrayList<>();
        CrmFlowStepDO crmFlowStepDO = null;
        if(stepId > 0){
            crmFlowStepDO = flowStepMapper.selectById(stepId);
        }else{
            CrmFlowDO crmFlowDO = flowMapper.selectOne(new LambdaQueryWrapper<CrmFlowDO>()
                    .eq(CrmFlowDO::getTypes,flowType)
                    .orderByDesc(CrmFlowDO::getId)
                    .last("limit 1"));
            if(crmFlowDO == null){
                return list;
            }
            crmFlowStepDO = flowStepMapper.selectOne(new LambdaQueryWrapper<CrmFlowStepDO>()
                    .eq(CrmFlowStepDO::getFlowId,crmFlowDO.getId())
                    .orderByAsc(CrmFlowStepDO::getId)
                    .last("limit 1"));
        }

        if(FlowStepEnum.TYPE_2.getValue().equals(crmFlowStepDO.getType())){
            list.add(crmFlowStepDO.getAdminIds());
        }else if(FlowStepEnum.TYPE_3.getValue().equals(crmFlowStepDO.getType())){
            Set<Long> deptIds = adminUserService.getDeptCondition(crmFlowStepDO.getAdminIds());
            List<AdminUserDO> adminUserDOS =  userMapper.selectList(new LambdaQueryWrapperX<AdminUserDO>()
                    .in(AdminUserDO::getDeptId,deptIds));
            list = adminUserDOS.stream().map(AdminUserDO::getId).collect(Collectors.toList());
        }

        return list;
    }

    private void createFlowStepList(Long flowId, List<CrmFlowStepDO> list) {
        int i = 1;
        for (CrmFlowStepDO o : list){
            o.setFlowId(flowId);
            o.setRelation(i);
            i++;
        }
        flowStepMapper.insertBatch(list);
    }

    private void updateFlowStepList(Long flowId, List<CrmFlowStepDO> list) {
        flowStepMapper.updateBatch(list);
//        deleteFlowStepByFlowId(flowId);
//		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
//        createFlowStepList(flowId, list);
    }

    private void deleteFlowStepByFlowId(Long flowId) {
        flowStepMapper.deleteByFlowId(flowId);
    }

    @Override
    public void deleteFlowStep(Long id) {
        flowStepMapper.deleteById(id);
    }

}