package co.yixiang.yshop.module.crm.service.crmflowlog;

import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.common.exception.ErrorCode;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmflow.vo.CrmFlowStepResultVO;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.CrmFlowLogPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.CrmFlowLogRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crmflowlog.vo.CrmFlowLogSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crminvoice.CrmInvoiceDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmmessage.CrmMessageDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontractreceivables.CrmContractReceivablesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflowlog.CrmFlowLogMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crminvoice.CrmInvoiceMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmmessage.CrmMessageMapper;
import co.yixiang.yshop.module.crm.enums.*;
import co.yixiang.yshop.module.crm.service.crmflow.CrmFlowService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 审核日志 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmFlowLogServiceImpl implements CrmFlowLogService {

    @Resource
    private CrmFlowLogMapper flowLogMapper;
    @Resource
    private CrmFlowService flowService;
    @Resource
    private CrmContractMapper contractMapper;
    @Resource
    private CrmMessageMapper crmMessageMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmBusinessMapper crmBusinessMapper;
    @Resource
    private CrmContractReceivablesMapper contractReceivablesMapper;
    @Resource
    private CrmInvoiceMapper crmInvoiceMapper;

    @Override
    public PageResult<CrmFlowLogRespVO> getFlowLogPage(CrmFlowLogPageReqVO pageReqVO) {
        if(TypesEnum.RECEIVABLES.getValue().equals(pageReqVO.getTypes())){
            return flowLogMapper.selectPage3(pageReqVO);
        }else if(TypesEnum.INVOICE.getValue().equals(pageReqVO.getTypes())){
            return flowLogMapper.selectPage4(pageReqVO);
        }else {
            return flowLogMapper.selectPage2(pageReqVO);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void examine(CrmFlowLogSaveReqVO saveReqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        CrmFlowLogDO crmFlowLogDO = flowLogMapper.selectOne(new LambdaQueryWrapper<CrmFlowLogDO>()
                .eq(CrmFlowLogDO::getId,saveReqVO.getId())
                .eq(CrmFlowLogDO::getStatus, FlowLogStatusEnum.STATUS_0.getValue())
                .eq(CrmFlowLogDO::getAdminId, loginUserId));
        if(crmFlowLogDO == null){
            throw exception(new ErrorCode(202505220,"已经审核过"));
        }

        Long toUserId = 0L;
        if(CheckTypesEnum.AGREE.getValue().equals(saveReqVO.getCheckType())){
            //审核通过
            crmFlowLogDO.setStatus(FlowLogStatusEnum.STATUS_1.getValue());
            String checkedUserIds = "" + loginUserId;
            //发送审核
            switch (TypesEnum.toType(crmFlowLogDO.getTypes())){
                case CONTRACT:
                    CrmContractDO crmContractDO = contractMapper.selectById(crmFlowLogDO.getTypesId());
                    if(StrUtil.isNotEmpty(crmContractDO.getCheckAdminId())){
                        checkedUserIds = checkedUserIds + "," + crmContractDO.getCheckAdminId();
                    }
                    break;
                case RECEIVABLES:
                    CrmContractReceivablesDO crmContractReceivablesDO = contractReceivablesMapper.selectById(crmFlowLogDO.getTypesId());
                    if(StrUtil.isNotEmpty(crmContractReceivablesDO.getCheckAdminId())){
                        checkedUserIds = checkedUserIds + "," + crmContractReceivablesDO.getCheckAdminId();
                    }
                    break;
                case INVOICE:
                    CrmInvoiceDO crmInvoiceDO = crmInvoiceMapper.selectById(crmFlowLogDO.getTypesId());
                    if(StrUtil.isNotEmpty(crmInvoiceDO.getCheckAdminId())){
                        checkedUserIds = checkedUserIds + "," + crmInvoiceDO.getCheckAdminId();
                    }
                    break;
            }
            //发送下一步审核
            CrmFlowStepResultVO flowStepResultVO = flowService.sendNextStep(crmFlowLogDO.getTypes(),
                    crmFlowLogDO.getTypesId(),checkedUserIds);
            if(flowStepResultVO.getIsSuccess()){//审核完成
                switch (TypesEnum.toType(crmFlowLogDO.getTypes())){
                    case CONTRACT:
                        //更新合同
                        CrmContractDO crmContractDO = contractMapper.selectById(crmFlowLogDO.getTypesId());
                        crmContractDO.setCheckAdminId(checkedUserIds);
                        crmContractDO.setCheckStatus(ContractStatusEnum.STATUS_2.getValue());
                        crmContractDO.setStepId(flowStepResultVO.getStepId());
                        contractMapper.updateById(crmContractDO);

                        toUserId = crmContractDO.getOwnerUserId();
                        //客户成交
                        CrmCustomerDO customerDO = customerMapper.selectById(crmContractDO.getCustomerId());
                        customerDO.setDealStatus(ShopCommonEnum.IS_STATUS_1.getValue());
                        customerDO.setDealTime(LocalDateTime.now());
                        customerDO.setPurchaseTimes(customerDO.getPurchaseTimes() + 1);
                        customerDO.setPurchaseTotal(customerDO.getPurchaseTotal().add(crmContractDO.getMoney()));
                        customerMapper.updateById(customerDO);
                        //如果关联了商机更新商机
                        if(crmContractDO.getBusinessId() != null && crmContractDO.getBusinessId() > 0){
                            crmBusinessMapper.update(CrmBusinessDO.builder()
                                            .isEnd(ShopCommonEnum.IS_STATUS_1.getValue())
                                            .remark("合同已签署成交")
                                            .build(),
                                    new LambdaQueryWrapper<CrmBusinessDO>()
                                            .eq(CrmBusinessDO::getId,crmContractDO.getBusinessId()));
                        }
                        break;
                    case RECEIVABLES:
                        //更新回款
                        CrmContractReceivablesDO crmContractReceivablesDO = contractReceivablesMapper.selectById(crmFlowLogDO.getTypesId());
                        crmContractReceivablesDO.setCheckAdminId(checkedUserIds);
                        crmContractReceivablesDO.setCheckStatus(ContractStatusEnum.STATUS_2.getValue());
                        crmContractReceivablesDO.setStepId(flowStepResultVO.getStepId());
                        contractReceivablesMapper.updateById(crmContractReceivablesDO);

                        toUserId = crmContractReceivablesDO.getOwnerUserId();
                        //合同增加回款
                        contractMapper.incReturnMoney(crmContractReceivablesDO.getMoney(),crmContractReceivablesDO.getContractId());
                        break;
                    case INVOICE:
                        //更新发票
                        CrmInvoiceDO crmInvoiceDO = crmInvoiceMapper.selectById(crmFlowLogDO.getTypesId());
                        crmInvoiceDO.setCheckAdminId(checkedUserIds);
                        crmInvoiceDO.setCheckStatus(ContractStatusEnum.STATUS_2.getValue());
                        crmInvoiceDO.setStepId(flowStepResultVO.getStepId());
                        crmInvoiceMapper.updateById(crmInvoiceDO);

                        toUserId = Long.valueOf(crmInvoiceDO.getCreator());
                        break;
                }

                //发布完成通知
                flowService.addMessage(MessageTypesEnum.EXAMINE_FINISH.getValue(),crmFlowLogDO.getId(),
                        Long.valueOf(toUserId), SecurityFrameworkUtils.getLoginUserId(),"您提交的审核已经审批通过！");

            }else {
                //审核进行中
                switch (TypesEnum.toType(crmFlowLogDO.getTypes())){
                    case CONTRACT:
                        //更新合同
                        contractMapper.update(CrmContractDO.builder()
                                        .checkAdminId(checkedUserIds)
                                        .checkStatus(ContractStatusEnum.STATUS_1.getValue())
                                        .stepId(flowStepResultVO.getStepId()).build(),
                                new LambdaQueryWrapper<CrmContractDO>().eq(CrmContractDO::getId,crmFlowLogDO.getTypesId()));
                        break;
                    case RECEIVABLES:
                        //更新合同
                        contractReceivablesMapper.update(CrmContractReceivablesDO.builder()
                                        .checkAdminId(checkedUserIds)
                                        .checkStatus(ContractStatusEnum.STATUS_1.getValue())
                                        .stepId(flowStepResultVO.getStepId()).build(),
                                new LambdaQueryWrapper<CrmContractReceivablesDO>()
                                        .eq(CrmContractReceivablesDO::getId,crmFlowLogDO.getTypesId()));
                        break;
                    case INVOICE:
                        //更新发票
                        crmInvoiceMapper.update(CrmInvoiceDO.builder()
                                        .checkAdminId(checkedUserIds)
                                        .checkStatus(ContractStatusEnum.STATUS_1.getValue())
                                        .stepId(flowStepResultVO.getStepId()).build(),
                                new LambdaQueryWrapper<CrmInvoiceDO>()
                                        .eq(CrmInvoiceDO::getId,crmFlowLogDO.getTypesId()));
                        break;
                }
            }

        }else{
            //审核拒绝
            crmFlowLogDO.setStatus(FlowLogStatusEnum.STATUS_2.getValue());
            //Long toUserId = 0L;
            switch (TypesEnum.toType(crmFlowLogDO.getTypes())){
                case CONTRACT:
                    CrmContractDO crmContractDO = contractMapper.selectById(crmFlowLogDO.getTypesId());
                    //更新合同
                    crmContractDO.setCheckAdminId("");
                    crmContractDO.setCheckStatus(ContractStatusEnum.STATUS_3.getValue());
                    contractMapper.updateById(crmContractDO);
                    toUserId = crmContractDO.getOwnerUserId();
                    //如果关联了商机更新商机为失败
                    if(crmContractDO.getBusinessId() != null && crmContractDO.getBusinessId() > 0){
                        crmBusinessMapper.update(CrmBusinessDO.builder()
                                        .isEnd(BusinessStatusEnum.STATUS_2.getValue())
                                        .remark("合同被拒绝签约失败")
                                        .build(),
                                new LambdaQueryWrapper<CrmBusinessDO>()
                                        .eq(CrmBusinessDO::getId,crmContractDO.getBusinessId()));
                    }
                    break;
                case RECEIVABLES:
                    CrmContractReceivablesDO crmContractReceivablesDO = contractReceivablesMapper.selectById(crmFlowLogDO.getTypesId());
                    crmContractReceivablesDO.setCheckAdminId("");
                    crmContractReceivablesDO.setCheckStatus(ContractStatusEnum.STATUS_3.getValue());
                    contractReceivablesMapper.updateById(crmContractReceivablesDO);
                    toUserId = crmContractReceivablesDO.getOwnerUserId();
                    break;
                case INVOICE:
                    CrmInvoiceDO crmInvoiceDO = crmInvoiceMapper.selectById(crmFlowLogDO.getTypesId());
                    crmInvoiceDO.setCheckAdminId("");
                    crmInvoiceDO.setCheckStatus(ContractStatusEnum.STATUS_3.getValue());
                    crmInvoiceMapper.updateById(crmInvoiceDO);
                    toUserId = Long.valueOf(crmInvoiceDO.getCreator());
                    break;
            }
            //发布拒绝通知
            flowService.addMessage(MessageTypesEnum.EXAMINE_REFUSE.getValue(),crmFlowLogDO.getId(),
                    Long.valueOf(toUserId), SecurityFrameworkUtils.getLoginUserId(),"您提交的审核被拒绝！");
            //未完成待审批记录处理
//            List<CrmFlowLogDO> crmFlowLogDOList = flowLogMapper.selectList(new LambdaQueryWrapper<CrmFlowLogDO>()
//                    .eq(CrmFlowLogDO::getTypes,crmFlowLogDO.getTypes())
//                    .eq(CrmFlowLogDO::getStatus, FlowLogStatusEnum.STATUS_0.getValue())
//                    .eq(CrmFlowLogDO::getTypesId, crmFlowLogDO.getTypesId()));
            //List<Long> flowLogIds = crmFlowLogDOList.stream().map(CrmFlowLogDO::getId).collect(Collectors.toList());
            //所有相关通知更新为已读
//            crmMessageMapper.update(CrmMessageDO.builder().readTime(LocalDateTime.now())
//                            .status(ShopCommonEnum.IS_STATUS_1.getValue()).build(),
//                    new LambdaQueryWrapper<CrmMessageDO>()
//                    .eq(CrmMessageDO::getRelationType,MessageTypesEnum.EXAMINE.getValue())
//                    .in(CrmMessageDO::getRelationId,flowLogIds)
//                    .eq(CrmMessageDO::getStatus, ShopCommonEnum.IS_STATUS_0.getValue()));

            flowLogMapper.update(CrmFlowLogDO.builder().status(FlowLogStatusEnum.STATUS_3.getValue()).build(),
                    new LambdaQueryWrapper<CrmFlowLogDO>()
                            .eq(CrmFlowLogDO::getTypes,crmFlowLogDO.getTypes())
                            .eq(CrmFlowLogDO::getStatus, FlowLogStatusEnum.STATUS_0.getValue())
                            .eq(CrmFlowLogDO::getTypesId, crmFlowLogDO.getTypesId()));


        }

        //更新审核日志
        crmFlowLogDO.setRemark(saveReqVO.getRemark());
        crmFlowLogDO.setAdminTime(LocalDateTime.now());
        flowLogMapper.updateById(crmFlowLogDO);
        //更新消息设置已读
        crmMessageMapper.update(CrmMessageDO.builder().readTime(LocalDateTime.now())
                .status(ShopCommonEnum.IS_STATUS_1.getValue()).build(),
        new LambdaQueryWrapper<CrmMessageDO>()
        .eq(CrmMessageDO::getRelationType,MessageTypesEnum.EXAMINE.getValue())
        .eq(CrmMessageDO::getRelationId,crmFlowLogDO.getId())
        .eq(CrmMessageDO::getToUserId, loginUserId));



    }






}