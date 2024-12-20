package co.yixiang.yshop.module.crm.service.crmcontractreceivables;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.common.exception.ErrorCode;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CheckInfoVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CrmContractRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowStepDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflow.CrmFlowMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflow.CrmFlowStepMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflowlog.CrmFlowLogMapper;
import co.yixiang.yshop.module.crm.enums.ContractStatusEnum;
import co.yixiang.yshop.module.crm.enums.FlowStepEnum;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.crm.service.crmflow.CrmFlowService;
import co.yixiang.yshop.module.crm.service.crmoperatelog.CrmOperatelogService;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import co.yixiang.yshop.module.system.dal.dataobject.user.AdminUserDO;
import co.yixiang.yshop.module.system.dal.mysql.user.AdminUserMapper;
import co.yixiang.yshop.module.system.service.user.AdminUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import co.yixiang.yshop.module.crm.controller.admin.crmcontractreceivables.vo.*;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.pojo.PageParam;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;

import co.yixiang.yshop.module.crm.dal.mysql.crmcontractreceivables.CrmContractReceivablesMapper;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.*;

/**
 * 回款 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
@Slf4j
public class CrmContractReceivablesServiceImpl implements CrmContractReceivablesService {

    @Resource
    private CrmContractReceivablesMapper contractReceivablesMapper;
    @Resource
    private CrmContractMapper contractMapper;
    @Resource
    private CrmOperatelogService crmOperatelogService;
    @Resource
    private CrmFlowMapper flowMapper;
    @Resource
    private CrmFlowStepMapper flowStepMapper;
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
    private AdminUserApi adminUserApi;
    @Resource
    private CrmFlowService flowService;

    private static final String LOCK_KEY = "receivables:check:lock";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContractReceivables(CrmContractReceivablesSaveReqVO createReqVO) {
        // 插入
        CrmContractReceivablesDO contractReceivables = BeanUtils.toBean(createReqVO, CrmContractReceivablesDO.class);
        contractReceivables.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        //合同
        CrmContractDO crmContractDO = contractMapper.selectById(createReqVO.getContractId());
        contractReceivables.setOrderAdminId(crmContractDO.getOrderAdminId());

        contractReceivablesMapper.insert(contractReceivables);

        //更新审核
        updateAfter(contractReceivables.getId());


        //插入日志
        crmOperatelogService.createLog("添加回款",0L,0L,contractReceivables.getId());
        // 返回
        return contractReceivables.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContractReceivables(CrmContractReceivablesSaveReqVO updateReqVO) {
        // 校验存在
        validateContractReceivablesExists(updateReqVO.getId());
        // 更新
        CrmContractReceivablesDO updateObj = BeanUtils.toBean(updateReqVO, CrmContractReceivablesDO.class);
        updateObj.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        //合同
        CrmContractDO crmContractDO = contractMapper.selectById(updateObj.getContractId());
        updateObj.setOrderAdminId(crmContractDO.getOrderAdminId());
        contractReceivablesMapper.updateById(updateObj);

        //更新审核
        updateAfter(updateObj.getId());
    }

    @Override
    public void deleteContractReceivables(Long id) {
        // 校验存在
        validateContractReceivablesExists(id);
        // 删除
        contractReceivablesMapper.deleteById(id);
    }

    public void updateAfter(Long id){
        CrmFlowDO crmFlowDO = flowMapper.selectOne(new LambdaQueryWrapper<CrmFlowDO>()
                .eq(CrmFlowDO::getTypes, TypesEnum.RECEIVABLES.getValue())
                .orderByDesc(CrmFlowDO::getId)
                .last("limit 1"));
        CrmFlowStepDO crmFlowStepDO = flowStepMapper.selectOne(new LambdaQueryWrapper<CrmFlowStepDO>()
                .eq(CrmFlowStepDO::getFlowId,crmFlowDO.getId())
                .orderByAsc(CrmFlowStepDO::getId)
                .last("limit 1"));

        contractReceivablesMapper.updateById(CrmContractReceivablesDO.builder()
                .id(id)
                .stepId(crmFlowStepDO.getId())
                .flowId(crmFlowDO.getId()).build());
    }

    private void validateContractReceivablesExists(Long id) {
        if (contractReceivablesMapper.selectById(id) == null) {
            throw exception(CONTRACT_RECEIVABLES_NOT_EXISTS);
        }
    }

    @Override
    public CrmContractReceivablesRespVO getContractReceivables(Long id) {
        CrmContractReceivablesDO receivablesDO = contractReceivablesMapper.selectById(id);
        CrmContractReceivablesRespVO receivablesRespVO = BeanUtils.toBean(receivablesDO,CrmContractReceivablesRespVO.class);
        receivablesRespVO.setFlowAdminId2(receivablesDO.getFlowAdminId());
        CrmCustomerDO customerDO = customerMapper.selectById(receivablesDO.getCustomerId());
        receivablesRespVO.setCustomerName(customerDO.getName());
        CrmContractDO crmContractDO = contractMapper.selectById(receivablesDO.getContractId());
        receivablesRespVO.setContractName(crmContractDO.getName());
        return receivablesRespVO;
    }

    @Override
    public PageResult<CrmContractReceivablesRespVO> getContractReceivablesPage(CrmContractReceivablesPageReqVO pageReqVO) {
        List<Long> ids = new ArrayList<>();
        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(pageReqVO.getRelation())){
            ids.add(loginAdminId);
        }else if(RelationEnum.SUB.getValue().equals(pageReqVO.getRelation())){
            ids = adminUserApi.getUserListBySubordinateIds(loginAdminId);
        }
        PageResult<CrmContractReceivablesRespVO> pageResult = contractReceivablesMapper.selectPage2(pageReqVO,ids);

        for (CrmContractReceivablesRespVO respVO : pageResult.getList()){
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
    public String getCode() {
        CrmContractReceivablesDO crmContractReceivablesDO = contractReceivablesMapper
                .selectOne(new LambdaQueryWrapper<CrmContractReceivablesDO>()
                        .orderByDesc(CrmContractReceivablesDO::getNumber).last("limit 1"));
        String format = DateUtil.format(new Date(), "yyyyMMdd");
        if(crmContractReceivablesDO == null) {
            return "HK"+format+"1000";
        }
        List<String> numbers = ReUtil.findAll(PatternPool.NUMBERS, crmContractReceivablesDO.getNumber(), 0);
        String number = format+"1000";
        if(!numbers.isEmpty()) {
            number = numbers.get(0);
        }
        BigDecimal newNumber = new BigDecimal(number).add(BigDecimal.ONE);
        return "HK" + newNumber;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void check(CheckInfoVO checkInfoVO) {
        CrmContractReceivablesDO receivablesDO = contractReceivablesMapper.selectById(checkInfoVO.getId());
        if(receivablesDO == null){
            throw exception(CONTRACT_RECEIVABLES_NOT_EXISTS);
        }
        CrmFlowDO crmFlowDO = flowMapper.selectById(receivablesDO.getFlowId());
        if(crmFlowDO == null){
            throw exception(FLOW_NOT_EXISTS);
        }
        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();
        if(!receivablesDO.getFlowAdminId().contains(loginAdminId)){
            throw exception(new ErrorCode(202408151,"无权限审核"));
        }
        if(receivablesDO.getCheckStatus() >= ContractStatusEnum.STATUS_2.getValue()){
            throw exception(new ErrorCode(202408150,"已经审核过"));
        }

        //组合审批日志
        CrmFlowLogDO crmFlowLogDO = CrmFlowLogDO.builder()
                .flowId(receivablesDO.getFlowId())
                .types(TypesEnum.RECEIVABLES.getValue())
                .typesId(receivablesDO.getId())
                .adminId(SecurityFrameworkUtils.getLoginUserId())
                .nickname(SecurityFrameworkUtils.getLoginUserNickname())
                .build();

        CrmFlowStepDO crmFlowStepDO = flowStepMapper.selectById(receivablesDO.getStepId());
        if(crmFlowStepDO == null){
            throw exception(new ErrorCode(202412044,"当前审核人不存在无法审核！"));
        }
        List<Long> userIds = flowService.getFlowUserIds("",crmFlowStepDO.getId());
        if(!userIds.contains(loginAdminId)){
            throw exception(new ErrorCode(202412043,"当前审核人已经变更无法审核！"));
        }


        RLock lock = redissonClient.getLock(LOCK_KEY);
        if (lock.tryLock()) {
            //加锁防止并发审核
            try {
                //查询审核步骤
                Long count = flowStepMapper.selectCount(new LambdaQueryWrapper<CrmFlowStepDO>()
                        .eq(CrmFlowStepDO::getFlowId,receivablesDO.getFlowId()));
                //加入已经审核人
                String checkAdminId = StrUtil.isEmpty(receivablesDO.getCheckAdminId()) ? loginAdminId.toString() : receivablesDO.getCheckAdminId() + "," + loginAdminId;
                receivablesDO.setCheckAdminId(checkAdminId);
                if(ShopCommonEnum.AGREE_1.getValue().equals(checkInfoVO.getAgreeType())){
                    //查询当前合同处于那一步骤
                    if(crmFlowStepDO.getRelation() < count){
                        receivablesDO.setCheckStatus(ContractStatusEnum.STATUS_1.getValue());
                        //判断当前步骤是不是最后一个人,如果是 开始审核后开始存储下一步信息
                        if(receivablesDO.getFlowAdminId().size() == 1){
                            List<Long> list = new  ArrayList<>();
                            CrmFlowStepDO next = flowStepMapper.selectOne(new LambdaQueryWrapper<CrmFlowStepDO>()
                                    .eq(CrmFlowStepDO::getFlowId,receivablesDO.getFlowId()).eq(CrmFlowStepDO::getRelation,crmFlowStepDO.getRelation()+1));
                            if(FlowStepEnum.TYPE_3.getValue().equals(next.getType())){
                                Set<Long> deptIds = adminUserService.getDeptCondition(next.getAdminIds());
                                List<AdminUserDO> adminUserDOS =  userMapper.selectList(new LambdaQueryWrapperX<AdminUserDO>()
                                        .in(AdminUserDO::getDeptId,deptIds));
                                list = adminUserDOS.stream().map(AdminUserDO::getId).collect(Collectors.toList());
                            }else{
                                list.add(next.getAdminIds());
                            }
                            receivablesDO.setFlowAdminId(list);
                            receivablesDO.setStepId(next.getId());
                        }else {
                            //移除已经审核等人
                            List<Long> nowAdminId = receivablesDO.getFlowAdminId();
                            nowAdminId.remove(loginAdminId);
                            receivablesDO.setFlowAdminId(nowAdminId);
                        }

                    }else{
                        //步骤走完
                        receivablesDO.setCheckStatus(ContractStatusEnum.STATUS_2.getValue());
                        receivablesDO.setFlowAdminId(new ArrayList<>());
                        crmFlowLogDO.setIsEnd(ShopCommonEnum.IS_STATUS_1.getValue());//审核结束
                    }
                    crmFlowLogDO.setStatus(ShopCommonEnum.IS_STATUS_1.getValue());//审核通过
                }else {
                    //拒绝
                    receivablesDO.setCheckStatus(ContractStatusEnum.STATUS_3.getValue());
                    receivablesDO.setCheckAdminId("");
                    receivablesDO.setFlowAdminId(new ArrayList<>());
                    crmFlowLogDO.setRemark(checkInfoVO.getRemark());
                }
                contractReceivablesMapper.updateById(receivablesDO);
                //存入审批日志
                crmFlowLogMapper.insert(crmFlowLogDO);
            } catch (Exception ex) {
                log.error("[message][执行异常]", ex);
            } finally {
                lock.unlock();
            }
        }

    }

}