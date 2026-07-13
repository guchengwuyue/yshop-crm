package co.yixiang.yshop.module.crm.service.crmflow;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.common.exception.ErrorCode;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmmessage.CrmMessageDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmflowlog.CrmFlowLogMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmmessage.CrmMessageMapper;
import co.yixiang.yshop.module.crm.enums.FlowLogStatusEnum;
import co.yixiang.yshop.module.crm.enums.FlowStepEnum;
import co.yixiang.yshop.module.crm.enums.MessageTypesEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.message.enums.WechatTempateEnum;
import co.yixiang.yshop.module.message.mq.producer.WeixinNoticeProducer;
import co.yixiang.yshop.module.system.controller.admin.user.vo.user.UserRespVO;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import co.yixiang.yshop.module.system.dal.mysql.user.AdminUserMapper;
import co.yixiang.yshop.module.system.service.user.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Resource
    private CrmFlowLogMapper flowLogMapper;
    @Resource
    private CrmMessageMapper crmMessageMapper;
    @Resource
    private WeixinNoticeProducer weixinNoticeProducer;

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
    public List<CrmFlowStepRespVO> getFlowUserIds(String flowType,Long dataId) {
        CrmFlowDO crmFlowDO = flowMapper.selectOne(new LambdaQueryWrapper<CrmFlowDO>()
                .eq(CrmFlowDO::getTypes,flowType)
                .orderByDesc(CrmFlowDO::getId)
                .last("limit 1"));
        if(crmFlowDO == null){
            return null;
        }
        List<CrmFlowStepDO> flowStepDOS = flowStepMapper.selectList(new LambdaQueryWrapper<CrmFlowStepDO>()
                .eq(CrmFlowStepDO::getFlowId,crmFlowDO.getId())
                .orderByAsc(CrmFlowStepDO::getRelation));
        //}
        List<CrmFlowStepRespVO> flowStepRespVOS = BeanUtils.toBean(flowStepDOS,CrmFlowStepRespVO.class);
        flowStepRespVOS.forEach(v -> {
           List<AdminUserDO> adminUserDOS = userMapper.selectList(new LambdaQueryWrapper<AdminUserDO>()
                    .in(AdminUserDO::getId,StrUtil.split(v.getAdminIds(),",")));
           List<UserRespVO> userRespVOList = BeanUtils.toBean(adminUserDOS, UserRespVO.class);
           int count = 0;
           for(UserRespVO vv : userRespVOList){
               CrmFlowLogDO crmFlowLogDO = flowLogMapper.selectOne(new LambdaQueryWrapper<CrmFlowLogDO>()
                       .eq(CrmFlowLogDO::getFlowId,crmFlowDO.getId())
                       .eq(CrmFlowLogDO::getTypesId,dataId)
                       .ne(CrmFlowLogDO::getStatus, ShopCommonEnum.IS_STATUS_0.getValue())
                       .eq(CrmFlowLogDO::getAdminId,vv.getId()));
               if(crmFlowLogDO != null){
                   if(FlowLogStatusEnum.STATUS_1.getValue().equals(crmFlowLogDO.getStatus())){
                       count++;
                   }else if(FlowLogStatusEnum.STATUS_2.getValue().equals(crmFlowLogDO.getStatus())){
                       count = -9;
                   }
                   vv.setFlowCheckRemark(crmFlowLogDO.getRemark());
                   vv.setFlowCheckStatus(crmFlowLogDO.getStatus());
                   vv.setFlowCheckTime(crmFlowLogDO.getAdminTime());
               }
           }
           if(dataId != null && dataId > 0){
               if(userRespVOList.size() == count){
                   v.setStatus(FlowLogStatusEnum.STATUS_1.getValue());
               }else if(count == 0){
                   v.setStatus(FlowLogStatusEnum.STATUS_0.getValue());
               }else if(count == -9){
                   v.setStatus(FlowLogStatusEnum.STATUS_2.getValue());
               }else{
                   v.setStatus(FlowLogStatusEnum.STATUS_5.getValue());
               }
           }else{
               v.setStatus(-9);
           }


           v.setUserRespVOList(userRespVOList);

        });



        return flowStepRespVOS;
    }

    private void createFlowStepList(Long flowId, List<CrmFlowStepDO> list) {
        List<String> stringList = new ArrayList<>();
        int i = 1;
        for (CrmFlowStepDO o : list){
            o.setFlowId(flowId);
            if(FlowStepEnum.TYPE_3.getValue().equals(o.getType())){
                if(o.getAdminIds2() == null){
                    throw exception(new ErrorCode(202505191,"请选择指定人员"));
                }
                o.setAdminIds(StrUtil.join(",",o.getAdminIds2()));
                stringList.addAll(o.getAdminIds2());
            }else{
                if(StrUtil.isEmpty(o.getAdminIds())){
                    throw exception(new ErrorCode(202505190,"请选择指定人员"));
                }
                stringList.add(o.getAdminIds());
            }
            o.setRelation(i);
            i++;
        }
        long distinctCount = stringList.stream().distinct().count();
        if(distinctCount != stringList.size()){
            throw exception(new ErrorCode(202505231,"同一个流程不能选择同一个人审核"));
        }
        flowStepMapper.insertBatch(list);
    }

    private void updateFlowStepList(Long flowId, List<CrmFlowStepDO> list) {
        deleteFlowStepByFlowId(flowId);
		list.forEach(o ->  o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createFlowStepList(flowId, list);
    }


    private void deleteFlowStepByFlowId(Long flowId) {
        flowStepMapper.deleteByFlowId(flowId);
    }

    @Override
    public void deleteFlowStep(Long id) {
        flowStepMapper.deleteById(id);
    }

    @Override
    public CrmFlowStepResultVO sendNextStep(String flowType, Long dataId, String checkedUserId) {
        //获取最新流程步骤
        CrmFlowDO crmFlowDO = flowMapper.selectOne(new LambdaQueryWrapper<CrmFlowDO>()
                .eq(CrmFlowDO::getTypes, flowType)
                .orderByDesc(CrmFlowDO::getId)
                .last("limit 1"));
        List<CrmFlowStepDO> crmFlowStepDOS = flowStepMapper.selectList(new LambdaQueryWrapper<CrmFlowStepDO>()
                .eq(CrmFlowStepDO::getFlowId,crmFlowDO.getId())
                .orderByAsc(CrmFlowStepDO::getRelation));
        List<String> checkedUserIds = StrUtil.split(checkedUserId,",");

        for(CrmFlowStepDO step : crmFlowStepDOS){
            //处理任意一个人或签
            if(FlowStepEnum.TYPE_2.getValue().equals(step.getType())){
                if(!checkedUserIds.contains(step.getAdminIds())){
                    //处理添加审批
                    addStepLog(flowType,dataId,step.getAdminIds(),crmFlowDO.getId());
                    return CrmFlowStepResultVO.builder().isSuccess(false).stepId(step.getId()).build();
                }
            }else{//多人会签
                Boolean isAll = true;
                List<String> adminList = StrUtil.split(step.getAdminIds(),",");
                for(String adminId : adminList){
                    if(!checkedUserIds.contains(adminId)){
                        isAll = false;
                        // 处理添加审批
                        addStepLog(flowType,dataId,adminId,crmFlowDO.getId());
                    }
                }
                if(!isAll) {
                    return CrmFlowStepResultVO.builder().isSuccess(false).stepId(step.getId()).build();
                }


            }
        }

        return CrmFlowStepResultVO.builder().isSuccess(true).stepId(0L).build();
    }

    /**
     * 添加审核日志消息并发送审核通知
     * @param flowType 类型
     * @param dataId 数据ID
     * @param userId 通知用户ID
     */
    public void addStepLog(String flowType, Long dataId,String userId,Long flowId){
        Long num = flowLogMapper.selectCount(new LambdaQueryWrapper<CrmFlowLogDO>()
                .eq(CrmFlowLogDO::getTypes,flowType)
                .eq(CrmFlowLogDO::getTypesId,dataId)
                .eq(CrmFlowLogDO::getAdminId,userId)
                .eq(CrmFlowLogDO::getStatus, FlowLogStatusEnum.STATUS_0.getValue()));
        //已经通知过的 不再通知
        if(num <= 0){
            CrmFlowLogDO crmFlowLogDO = CrmFlowLogDO.builder()
                    .flowId(flowId)
                    .types(flowType)
                    .typesId(dataId)
                    .adminId(Long.valueOf(userId))
                    .build();
            flowLogMapper.insert(crmFlowLogDO);

            //发送通知
            addMessage(MessageTypesEnum.EXAMINE.getValue(),crmFlowLogDO.getId(),
                    Long.valueOf(userId), SecurityFrameworkUtils.getLoginUserId(),"您有一个新的审批,请注意查收！");
        }
    }

    @Override
    public void addMessage(String type, Long relationId, Long toUserId, Long fromUserId,String content) {

        CrmMessageDO crmMessageDO = CrmMessageDO.builder()
                .relationType(type)
                .relationId(relationId)
                .toUserId(toUserId)
                .fromUserId(fromUserId)
                .content(content)
                .sendTime(LocalDateTime.now())
                .build();
        crmMessageMapper.insert(crmMessageDO);

        //模板消息通知 或者短信  邮件通知
        weixinNoticeProducer.sendNoticeMessage(toUserId,
                    WechatTempateEnum.CHECK_NOTICE.getValue(),WechatTempateEnum.TEMPLATES.getValue(),content, DateUtil.now());
    }
}