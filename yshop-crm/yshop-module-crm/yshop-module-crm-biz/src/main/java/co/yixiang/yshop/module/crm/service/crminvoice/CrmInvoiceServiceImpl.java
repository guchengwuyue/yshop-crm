package co.yixiang.yshop.module.crm.service.crminvoice;

import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.common.exception.ErrorCode;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CheckInfoVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo.CrmContractReceivablesRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoicePageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoiceRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crminvoice.vo.CrmInvoiceSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowStepDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crminvoice.CrmInvoiceDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflow.CrmFlowMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflow.CrmFlowStepMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflowlog.CrmFlowLogMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crminvoice.CrmInvoiceMapper;
import co.yixiang.yshop.module.crm.enums.*;
import co.yixiang.yshop.module.crm.service.crmflow.CrmFlowService;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import co.yixiang.yshop.module.system.dal.mysql.user.AdminUserMapper;
import co.yixiang.yshop.module.system.service.user.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.*;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.FLOW_NOT_EXISTS;

/**
 * 发票 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
@Slf4j
public class CrmInvoiceServiceImpl implements CrmInvoiceService {

    @Resource
    private CrmInvoiceMapper invoiceMapper;
    @Resource
    private CrmContractMapper contractMapper;
    @Resource
    private CrmFlowMapper flowMapper;
    @Resource
    private CrmFlowStepMapper flowStepMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private CrmFlowLogMapper crmFlowLogMapper;
    @Resource
    private CrmFlowService flowService;

    private static final String LOCK_KEY = "invoice:check:lock";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInvoice(CrmInvoiceSaveReqVO createReqVO) {
        CrmContractDO crmContractDO = preCheck(createReqVO);
        // 插入
        CrmInvoiceDO invoice = BeanUtils.toBean(createReqVO, CrmInvoiceDO.class);
        invoice.setCustomerId(crmContractDO.getCustomerId());
        invoiceMapper.insert(invoice);

        updateAfter(invoice.getId());
        // 返回
        return invoice.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInvoice(CrmInvoiceSaveReqVO updateReqVO) {
        CrmContractDO crmContractDO = preCheck(updateReqVO);
        // 校验存在
        validateInvoiceExists(updateReqVO.getId());
        // 更新
        CrmInvoiceDO updateObj = BeanUtils.toBean(updateReqVO, CrmInvoiceDO.class);
        updateObj.setCustomerId(crmContractDO.getCustomerId());
        invoiceMapper.updateById(updateObj);

        updateAfter(updateReqVO.getId());
    }

    @Override
    public void deleteInvoice(Long id) {
        // 校验存在
        validateInvoiceExists(id);
        // 删除
        invoiceMapper.deleteById(id);
    }

    public CrmContractDO preCheck(CrmInvoiceSaveReqVO createReqVO){
        CrmContractDO crmContractDO = contractMapper.selectById(createReqVO.getContractId());
        if(crmContractDO == null){
            throw exception(CONTRACT_NOT_EXISTS);
        }
        if(!ContractStatusEnum.STATUS_2.getValue().equals(crmContractDO.getCheckStatus())){
            throw exception(new ErrorCode(202406160,"合同未通过审核不可以申请发票"));
        }
        Integer[] checkStatus = {0, 1};
        Long count = invoiceMapper.selectCount(new LambdaQueryWrapper<CrmInvoiceDO>()
                .in(CrmInvoiceDO::getCheckStatus,checkStatus).or().eq(CrmInvoiceDO::getStatus,checkStatus));
        if(count > 0){
            throw exception(new ErrorCode(202406161,"该合同还有未审核或者未开票等申请，不可以继续申请发票"));
        }
        //判断开票金额
        BigDecimal money = createReqVO.getMoney().add(crmContractDO.getInvoiceMoney());
        if(money.compareTo(crmContractDO.getMoney()) > 0){
            throw exception(new ErrorCode(202406161,"累积开票金额不能大于合同金额"));
        }

        return crmContractDO;
    }

    public void updateAfter(Long id){
        CrmFlowDO crmFlowDO = flowMapper.selectOne(new LambdaQueryWrapper<CrmFlowDO>()
                .eq(CrmFlowDO::getTypes, TypesEnum.INVOICE.getValue())
                .orderByDesc(CrmFlowDO::getId)
                .last("limit 1"));
        CrmFlowStepDO crmFlowStepDO = flowStepMapper.selectOne(new LambdaQueryWrapper<CrmFlowStepDO>()
                .eq(CrmFlowStepDO::getFlowId,crmFlowDO.getId())
                .orderByAsc(CrmFlowStepDO::getId)
                .last("limit 1"));

        invoiceMapper.updateById(CrmInvoiceDO.builder()
                .id(id)
                .stepId(crmFlowStepDO.getId())
                .flowId(crmFlowDO.getId()).build());
    }

    private void validateInvoiceExists(Long id) {
        if (invoiceMapper.selectById(id) == null) {
            throw exception(INVOICE_NOT_EXISTS);
        }
    }

    @Override
    public CrmInvoiceRespVO getInvoice(Long id) {
        CrmInvoiceDO invoiceDO = invoiceMapper.selectById(id);
        CrmInvoiceRespVO invoiceRespVO = BeanUtils.toBean(invoiceDO,CrmInvoiceRespVO.class);
        invoiceRespVO.setFlowAdminId2(invoiceDO.getFlowAdminId());
        CrmCustomerDO customerDO = customerMapper.selectById(invoiceDO.getCustomerId());
        invoiceRespVO.setCustomerName(customerDO.getName());
        CrmContractDO crmContractDO = contractMapper.selectById(invoiceDO.getContractId());
        invoiceRespVO.setContractName(crmContractDO.getName());
        return invoiceRespVO;
    }

    @Override
    public PageResult<CrmInvoiceRespVO> getInvoicePage(CrmInvoicePageReqVO pageReqVO) {
        List<Long> ids = new ArrayList<>();
        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(pageReqVO.getRelation())){
            ids.add(loginAdminId);
        }else if(RelationEnum.SUB.getValue().equals(pageReqVO.getRelation())){
            ids = adminUserApi.getUserListBySubordinateIds(loginAdminId);
        }
        PageResult<CrmInvoiceRespVO> pageResult = invoiceMapper.selectPage2(pageReqVO,ids);
        for (CrmInvoiceRespVO respVO : pageResult.getList()){
            List<String> adminIds = StrUtil.split(respVO.getFlowAdminId(),",");
            if(adminIds.contains(loginAdminId.toString())){
                respVO.setIsCheck(true);
            }else {
                respVO.setIsCheck(false);
            }
        }
        return pageResult;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void check(CheckInfoVO checkInfoVO) {
        CrmInvoiceDO invoiceDO = invoiceMapper.selectById(checkInfoVO.getId());
        if(invoiceDO == null){
            throw exception(INVOICE_NOT_EXISTS);
        }
        CrmFlowDO crmFlowDO = flowMapper.selectById(invoiceDO.getFlowId());
        if(crmFlowDO == null){
            throw exception(FLOW_NOT_EXISTS);
        }
        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();
        if(!invoiceDO.getFlowAdminId().contains(loginAdminId)){
            throw exception(new ErrorCode(202408161,"无权限审核"));
        }
        if(invoiceDO.getCheckStatus() >= ContractStatusEnum.STATUS_2.getValue()){
            throw exception(new ErrorCode(202408160,"已经审核过"));
        }

        //组合审批日志
        CrmFlowLogDO crmFlowLogDO = CrmFlowLogDO.builder()
                .flowId(invoiceDO.getFlowId())
                .types(TypesEnum.INVOICE.getValue())
                .typesId(invoiceDO.getId())
                .adminId(SecurityFrameworkUtils.getLoginUserId())
                .nickname(SecurityFrameworkUtils.getLoginUserNickname())
                .build();

        CrmFlowStepDO crmFlowStepDO = flowStepMapper.selectById(invoiceDO.getStepId());
        if(crmFlowStepDO == null){
            throw exception(new ErrorCode(202412045,"当前审核人不存在无法审核！"));
        }
        List<Long> userIds = flowService.getFlowUserIds("",crmFlowStepDO.getId());
        if(!userIds.contains(loginAdminId)){
            throw exception(new ErrorCode(202412046,"当前审核人已经变更无法审核！"));
        }

        RLock lock = redissonClient.getLock(LOCK_KEY);
        if (lock.tryLock()) {
            //加锁防止并发审核
            try {
                //查询审核步骤
                Long count = flowStepMapper.selectCount(new LambdaQueryWrapper<CrmFlowStepDO>()
                        .eq(CrmFlowStepDO::getFlowId,invoiceDO.getFlowId()));
                //加入已经审核人
                String checkAdminId = StrUtil.isEmpty(invoiceDO.getCheckAdminId()) ? loginAdminId.toString() : invoiceDO.getCheckAdminId() + "," + loginAdminId;
                invoiceDO.setCheckAdminId(checkAdminId);
                if(ShopCommonEnum.AGREE_1.getValue().equals(checkInfoVO.getAgreeType())){
                    //查询当前合同处于那一步骤
                    if(crmFlowStepDO.getRelation() < count){
                        invoiceDO.setCheckStatus(ContractStatusEnum.STATUS_1.getValue());
                        //判断当前步骤是不是最后一个人,如果是 开始审核后开始存储下一步信息
                        if(invoiceDO.getFlowAdminId().size() == 1){
                            List<Long> list = new  ArrayList<>();
                            CrmFlowStepDO next = flowStepMapper.selectOne(new LambdaQueryWrapper<CrmFlowStepDO>()
                                    .eq(CrmFlowStepDO::getFlowId,invoiceDO.getFlowId()).eq(CrmFlowStepDO::getRelation,crmFlowStepDO.getRelation()+1));
                            if(FlowStepEnum.TYPE_3.getValue().equals(next.getType())){
                                Set<Long> deptIds = adminUserService.getDeptCondition(next.getAdminIds());
                                List<AdminUserDO> adminUserDOS =  userMapper.selectList(new LambdaQueryWrapperX<AdminUserDO>()
                                        .in(AdminUserDO::getDeptId,deptIds));
                                list = adminUserDOS.stream().map(AdminUserDO::getId).collect(Collectors.toList());
                            }else{
                                list.add(next.getAdminIds());
                            }
                            invoiceDO.setFlowAdminId(list);
                            invoiceDO.setStepId(next.getId());
                        }else {
                            //移除已经审核等人
                            List<Long> nowAdminId = invoiceDO.getFlowAdminId();
                            nowAdminId.remove(loginAdminId);
                            invoiceDO.setFlowAdminId(nowAdminId);
                        }

                    }else{
                        //步骤走完
                        invoiceDO.setCheckStatus(ContractStatusEnum.STATUS_2.getValue());
                        invoiceDO.setFlowAdminId(new ArrayList<>());
                        crmFlowLogDO.setIsEnd(ShopCommonEnum.IS_STATUS_1.getValue());//审核结束
                    }
                    crmFlowLogDO.setStatus(ShopCommonEnum.IS_STATUS_1.getValue());//审核通过
                }else {
                    //拒绝
                    invoiceDO.setCheckStatus(ContractStatusEnum.STATUS_3.getValue());
                    invoiceDO.setCheckAdminId("");
                    invoiceDO.setFlowAdminId(new ArrayList<>());
                    crmFlowLogDO.setRemark(checkInfoVO.getRemark());
                }
                invoiceMapper.updateById(invoiceDO);
                //存入审批日志
                crmFlowLogMapper.insert(crmFlowLogDO);
            } catch (Exception ex) {
                log.error("[message][执行异常]", ex);
            } finally {
                lock.unlock();
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void issueInvoice(CrmInvoiceSaveReqVO updateReqVO) {
        CrmInvoiceDO crmInvoiceDO = invoiceMapper.selectById(updateReqVO.getId());
        if(InvoiceStatusEnum.STATUS_2.getValue().equals(crmInvoiceDO.getStatus())){
            throw exception(new ErrorCode(202408240,"发票已经开具啦"));
        }
        CrmInvoiceDO updateObj = BeanUtils.toBean(updateReqVO, CrmInvoiceDO.class);
        updateObj.setStatus(InvoiceStatusEnum.STATUS_2.getValue());
        invoiceMapper.updateById(updateObj);

        CrmContractDO crmContractDO = contractMapper.selectById(crmInvoiceDO.getContractId());
        BigDecimal money = crmContractDO.getInvoiceMoney().add(updateObj.getMoney());
        crmContractDO.setInvoiceMoney(money);
        contractMapper.updateById(crmContractDO);

    }
}