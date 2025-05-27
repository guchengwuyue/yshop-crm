package co.yixiang.yshop.module.crm.service.crmcontract;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.common.exception.ErrorCode;
import co.yixiang.yshop.framework.common.pojo.PageResult;
import co.yixiang.yshop.framework.common.util.object.BeanUtils;
import co.yixiang.yshop.framework.mybatis.core.query.LambdaQueryWrapperX;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CheckInfoVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CrmContractPageReqVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CrmContractRespVO;
import co.yixiang.yshop.module.crm.controller.admin.crmcontract.vo.CrmContractSaveReqVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractProductDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflow.CrmFlowStepDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractProductMapper;
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
import co.yixiang.yshop.module.product.dal.dataobject.storeproductattrvalue.StoreProductAttrValueDO;
import co.yixiang.yshop.module.product.dal.mysql.storeproduct.StoreProductMapper;
import co.yixiang.yshop.module.product.dal.mysql.storeproductattrvalue.StoreProductAttrValueMapper;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static co.yixiang.yshop.framework.common.exception.util.ServiceExceptionUtil.exception;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.CONTRACT_NOT_EXISTS;
import static co.yixiang.yshop.module.crm.enums.ErrorCodeConstants.FLOW_NOT_EXISTS;
import static co.yixiang.yshop.module.product.enums.ErrorCodeConstants.PRODUCT_STOCK_LESS;

/**
 * 合同 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
@Slf4j
public class CrmContractServiceImpl implements CrmContractService {

    @Resource
    private CrmContractMapper contractMapper;
    @Resource
    private CrmContractProductMapper contractProductMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmFlowMapper flowMapper;
    @Resource
    private CrmFlowStepMapper flowStepMapper;
    @Resource
    private CrmBusinessMapper crmBusinessMapper;
    @Resource
    private StoreProductAttrValueMapper storeProductAttrValueMapper;
    @Resource
    private StoreProductMapper storeProductMapper;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private AdminUserMapper userMapper;
    @Resource
    private CrmFlowLogMapper crmFlowLogMapper;
    @Resource
    private  RedissonClient redissonClient;
    @Resource
    private CrmOperatelogService crmOperatelogService;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private CrmFlowService flowService;

    private static final String LOCK_KEY = "contract:check:lock";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createContract(CrmContractSaveReqVO createReqVO) {

        //处理客户
        CrmCustomerDO crmCustomerDO = customerMapper.selectById(createReqVO.getCustomerId());


        // 插入
        CrmContractDO contract = BeanUtils.toBean(createReqVO, CrmContractDO.class);
        contract.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        if(crmCustomerDO != null && crmCustomerDO.getSource() != null){
            contract.setSourceId(crmCustomerDO.getSource());
        }
        contractMapper.insert(contract);

        // 插入子表
        createContractProductList(contract.getId(), createReqVO.getContractProducts());

        //更新审核
        updateAfter(contract.getId());

        //插入日志
        crmOperatelogService.createLog("添加合同",0L,contract.getId(),0L);

        //todo 站内信通知 审批

        // 返回
        return contract.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateContract(CrmContractSaveReqVO updateReqVO) {
        //处理客户
        CrmCustomerDO crmCustomerDO = customerMapper.selectById(updateReqVO.getCustomerId());
        // 校验存在
        validateContractExists(updateReqVO.getId());
        // 更新
        CrmContractDO updateObj = BeanUtils.toBean(updateReqVO, CrmContractDO.class);
        updateObj.setOwnerUserId(SecurityFrameworkUtils.getLoginUserId());
        if(crmCustomerDO != null && crmCustomerDO.getSource() != null){
            updateObj.setSourceId(crmCustomerDO.getSource());
        }
        contractMapper.updateById(updateObj);

        //更新审核
        updateAfter(updateReqVO.getId());

        //todo 站内信通知 审批

        // 更新子表
        updateContractProductList(updateReqVO.getId(), updateReqVO.getContractProducts());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteContract(Long id) {
        // 校验存在
        validateContractExists(id);
        // 删除
        contractMapper.deleteById(id);

        // 删除子表
        deleteContractProductByContractId(id);
    }

    private void updateAfter(Long id){
        CrmFlowDO crmFlowDO = flowMapper.selectOne(new LambdaQueryWrapper<CrmFlowDO>()
                .eq(CrmFlowDO::getTypes, TypesEnum.CONTRACT.getValue())
                .orderByDesc(CrmFlowDO::getId)
                .last("limit 1"));
        CrmFlowStepDO crmFlowStepDO = flowStepMapper.selectOne(new LambdaQueryWrapper<CrmFlowStepDO>()
                .eq(CrmFlowStepDO::getFlowId,crmFlowDO.getId())
                .orderByAsc(CrmFlowStepDO::getId)
                .last("limit 1"));

        contractMapper.updateById(CrmContractDO.builder()
                .id(id)
                .stepId(crmFlowStepDO.getId())
                .flowId(crmFlowDO.getId()).build());
    }

    private void validateContractExists(Long id) {
        if (contractMapper.selectById(id) == null) {
            throw exception(CONTRACT_NOT_EXISTS);
        }
    }

    @Override
    public CrmContractRespVO getContract(Long id) {
        CrmContractDO crmContractDO = contractMapper.selectById(id);
        CrmContractRespVO crmContractRespVO = BeanUtils.toBean(crmContractDO,CrmContractRespVO.class);
        crmContractRespVO.setFlowAdminId2(crmContractDO.getFlowAdminId());
        CrmCustomerDO customerDO = customerMapper.selectById(crmContractDO.getCustomerId());
        crmContractRespVO.setCustomerName(customerDO.getName());
        if(crmContractDO.getBusinessId() != null && crmContractDO.getBusinessId() > 0){
            CrmBusinessDO crmBusinessDO = crmBusinessMapper.selectById(crmContractDO.getBusinessId());
            crmContractRespVO.setBusinessName(crmBusinessDO.getName());
        }

        return crmContractRespVO;
    }

    @Override
    public PageResult<CrmContractRespVO> getContractPage(CrmContractPageReqVO pageReqVO) {
        List<Long> ids = new ArrayList<>();
        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(pageReqVO.getRelation())){
            ids.add(loginAdminId);
        }else if(RelationEnum.SUB.getValue().equals(pageReqVO.getRelation())){
            ids = adminUserApi.getUserListBySubordinateIds(loginAdminId);
        }
        PageResult<CrmContractRespVO> pageResult = contractMapper.selectPage2(pageReqVO,ids);

        for (CrmContractRespVO contractRespVO : pageResult.getList()){
            List<String> adminIds = StrUtil.split(contractRespVO.getFlowAdminId(),",");
            if(adminIds.contains(loginAdminId.toString())){
                contractRespVO.setIsCheck(true);
            }else {
                contractRespVO.setIsCheck(false);
            }
        }
        return pageResult;
    }

    // ==================== 子表（合同产品关系） ====================

    @Override
    public List<CrmContractProductDO> getContractProductListByContractId(Long contractId) {
        List<CrmContractProductDO> crmContractProductDOS = contractProductMapper.selectListByContractId(contractId);
        for (CrmContractProductDO contractProductDO : crmContractProductDOS){
            StoreProductAttrValueDO productAttrValueDO = storeProductAttrValueMapper
                    .selectOne(new LambdaQueryWrapper<StoreProductAttrValueDO>()
                            .eq(StoreProductAttrValueDO::getProductId,contractProductDO.getProductId())
                            .eq(StoreProductAttrValueDO::getUnique,contractProductDO.getProductAttrUnique()));
            contractProductDO.setSku(productAttrValueDO.getSku());
        }
        return crmContractProductDOS;
    }


    @Override
    public String getCode() {
        CrmContractDO crmContractDO = contractMapper
                .selectOne(new LambdaQueryWrapper<CrmContractDO>()
                        .orderByDesc(CrmContractDO::getNumber).last("limit 1"));
        String format = DateUtil.format(new Date(), "yyyyMMdd");
        if(crmContractDO == null) {
            return "HT"+format+"1000";
        }
        List<String> numbers = ReUtil.findAll(PatternPool.NUMBERS, crmContractDO.getNumber(), 0);
        String number = format+"1000";
        if(!numbers.isEmpty()) {
            number = numbers.get(0);
        }
        BigDecimal newNumber = new BigDecimal(number).add(BigDecimal.ONE);
        return "HT" + newNumber;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void check(CheckInfoVO checkInfoVO) {
        CrmContractDO crmContractDO = contractMapper.selectById(checkInfoVO.getId());
        if(crmContractDO == null){
            throw exception(CONTRACT_NOT_EXISTS);
        }
        CrmFlowDO crmFlowDO = flowMapper.selectById(crmContractDO.getFlowId());
        if(crmFlowDO == null){
            throw exception(FLOW_NOT_EXISTS);
        }
        Long loginAdminId = SecurityFrameworkUtils.getLoginUserId();
        if(!crmContractDO.getFlowAdminId().contains(loginAdminId)){
            throw exception(new ErrorCode(202408111,"无权限审核"));
        }
        if(crmContractDO.getCheckStatus() >= ContractStatusEnum.STATUS_2.getValue()){
            throw exception(new ErrorCode(202408110,"已经审核过"));
        }

        //组合审批日志
        CrmFlowLogDO crmFlowLogDO = CrmFlowLogDO.builder()
                .flowId(crmContractDO.getFlowId())
                .types(TypesEnum.CONTRACT.getValue())
                .typesId(crmContractDO.getId())
                .adminId(SecurityFrameworkUtils.getLoginUserId())
                .nickname(SecurityFrameworkUtils.getLoginUserNickname())
                .build();

        CrmFlowStepDO crmFlowStepDO = flowStepMapper.selectById(crmContractDO.getStepId());
        if(crmFlowStepDO == null){
            throw exception(new ErrorCode(202412042,"当前审核人不存在无法审核！"));
        }
        List<Long> userIds = flowService.getFlowUserIds("",crmFlowStepDO.getId());
        if(!userIds.contains(loginAdminId)){
            throw exception(new ErrorCode(202412041,"当前审核人已经变更无法审核！"));
        }

        RLock lock = redissonClient.getLock(LOCK_KEY);
        if (lock.tryLock()) {
            //加锁防止并发审核
            try {
                //查询审核步骤
                Long count = flowStepMapper.selectCount(new LambdaQueryWrapper<CrmFlowStepDO>()
                        .eq(CrmFlowStepDO::getFlowId,crmContractDO.getFlowId()));
                //加入已经审核人
                String checkAdminId = StrUtil.isEmpty(crmContractDO.getCheckAdminId()) ? loginAdminId.toString() : crmContractDO.getCheckAdminId() + "," + loginAdminId;
                crmContractDO.setCheckAdminId(checkAdminId);
                if(ShopCommonEnum.AGREE_1.getValue().equals(checkInfoVO.getAgreeType())){
                    //查询当前合同处于那一步骤
                    if(crmFlowStepDO.getRelation() < count){
                        crmContractDO.setCheckStatus(ContractStatusEnum.STATUS_1.getValue());
                        //判断当前步骤是不是最后一个人,如果是 开始审核后开始存储下一步信息
                        if(crmContractDO.getFlowAdminId().size() == 1){
                            List<Long> list = new  ArrayList<>();
                            CrmFlowStepDO next = flowStepMapper.selectOne(new LambdaQueryWrapper<CrmFlowStepDO>()
                                    .eq(CrmFlowStepDO::getFlowId,crmContractDO.getFlowId()).eq(CrmFlowStepDO::getRelation,crmFlowStepDO.getRelation()+1));
                            if(FlowStepEnum.TYPE_3.getValue().equals(next.getType())){
                                Set<Long> deptIds = adminUserService.getDeptCondition(next.getAdminIds());
                                List<AdminUserDO> adminUserDOS =  userMapper.selectList(new LambdaQueryWrapperX<AdminUserDO>()
                                        .in(AdminUserDO::getDeptId,deptIds));
                                list = adminUserDOS.stream().map(AdminUserDO::getId).collect(Collectors.toList());
                            }else{
                                list.add(next.getAdminIds());
                            }
                            crmContractDO.setFlowAdminId(list);
                            crmContractDO.setStepId(next.getId());
                        }else {
                            //移除已经审核等人
                            List<Long> nowAdminId = crmContractDO.getFlowAdminId();
                            nowAdminId.remove(loginAdminId);
                            crmContractDO.setFlowAdminId(nowAdminId);
                        }

                    }else{
                        //步骤走完
                        crmContractDO.setCheckStatus(ContractStatusEnum.STATUS_2.getValue());
                        crmContractDO.setFlowAdminId(new ArrayList<>());
                        crmFlowLogDO.setIsEnd(ShopCommonEnum.IS_STATUS_1.getValue());//审核结束

                        //客户成交
                        CrmCustomerDO customerDO = customerMapper.selectById(crmContractDO.getCustomerId());
                        customerDO.setDealStatus(ShopCommonEnum.IS_STATUS_1.getValue());
                        customerDO.setDealTime(LocalDateTime.now());
                        customerDO.setPurchaseTimes(customerDO.getPurchaseTimes() + 1);
                        customerDO.setPurchaseTotal(customerDO.getPurchaseTotal().add(crmContractDO.getMoney()));
                        customerMapper.updateById(customerDO);
                        if(crmContractDO.getBusinessId() != null && crmContractDO.getBusinessId() > 0){
                            crmBusinessMapper.update(CrmBusinessDO.builder()
                                            .isEnd(ShopCommonEnum.IS_STATUS_1.getValue())
                                            .remark("合同已签署成交")
                                            .build(),
                                    new LambdaQueryWrapper<CrmBusinessDO>()
                                            .eq(CrmBusinessDO::getId,crmContractDO.getBusinessId()));
                        }
                    }
                    crmFlowLogDO.setStatus(ShopCommonEnum.IS_STATUS_1.getValue());//审核通过
                }else {
                    //拒绝
                    crmContractDO.setCheckStatus(ContractStatusEnum.STATUS_3.getValue());
                    crmContractDO.setCheckAdminId("");
                    crmContractDO.setFlowAdminId(new ArrayList<>());
                    crmFlowLogDO.setRemark(checkInfoVO.getRemark());
                    //回退库存
                    List<CrmContractProductDO> list = contractProductMapper
                            .selectList(new LambdaQueryWrapper<CrmContractProductDO>()
                                    .eq(CrmContractProductDO::getContractId,crmContractDO.getId()));
                    list.forEach(product -> incProductStock(product.getNums(),product.getProductId(),product.getProductAttrUnique()));
                }
                contractMapper.updateById(crmContractDO);
                //存入审批日志
                crmFlowLogMapper.insert(crmFlowLogDO);
            } catch (Exception ex) {
                log.error("[message][执行异常]", ex);
            } finally {
                lock.unlock();
            }
        }

    }

    /**
     * 增加库存 减少销量
     *
     * @param num       数量
     * @param productId 商品id
     * @param unique    sku唯一值
     */
    private void incProductStock(Integer num, Long productId, String unique) {
        //处理属性sku
        if (StrUtil.isNotEmpty(unique)) {
            storeProductAttrValueMapper.incStockDecSales(num, productId, unique);
        }
        //先处理商品库存，活动商品也要处理，因为共享库存
        storeProductMapper.incStockDecSales(num, productId);
    }

    private void createContractProductList(Long contractId, List<CrmContractProductDO> list) {
        list.forEach(o -> {
            o.setContractId(contractId);
            //库存处理
            Long count = storeProductAttrValueMapper.selectCount(new LambdaQueryWrapper<StoreProductAttrValueDO>()
                    .eq(StoreProductAttrValueDO::getProductId,o.getProductId())
                    .eq(StoreProductAttrValueDO::getUnique,o.getProductAttrUnique()));
            if(NumberUtil.compare(count,o.getNums()) < 0){
                throw exception(new ErrorCode(202408250,"该商品ID：" + o.getProductId() + "库存不足"));
            }

            this.decProductStock(o.getNums().intValue(),o.getProductId(),o.getProductAttrUnique());

        });
        contractProductMapper.insertBatch(list);
    }

    /**
     * 减少库存
     * @param num 数量
     * @param productId 产品ID
     * @param unique sku唯一值
     */
    private void decProductStock(int num, Long productId, String unique) {
        int res = 0;
        res =  storeProductAttrValueMapper.decStockIncSales(num,productId,unique);

        if(res == 0) {
            throw exception(PRODUCT_STOCK_LESS);
        }

        int product = storeProductMapper.decStockIncSales(num, productId);
        if (product == 0) {
            throw exception(PRODUCT_STOCK_LESS);
        }


    }

    private void updateContractProductList(Long contractId, List<CrmContractProductDO> list) {
        deleteContractProductByContractId(contractId);
		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createContractProductList(contractId, list);
    }

    private void deleteContractProductByContractId(Long contractId) {
        contractProductMapper.deleteByContractId(contractId);
    }

}