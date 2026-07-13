package co.yixiang.yshop.module.crm.service.crmindex;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import co.yixiang.yshop.framework.common.enums.CommonStatusEnum;
import co.yixiang.yshop.framework.common.enums.ShopCommonEnum;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmindex.vo.BrieCountVO;
import co.yixiang.yshop.module.crm.controller.admin.crmindex.vo.CrmIndexRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontractreceivables.CrmContractReceivablesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomercontacts.CrmCustomerContactsDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmflowlog.CrmFlowLogDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmrecord.CrmRecordDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmclues.CrmCluesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontractreceivables.CrmContractReceivablesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomercontacts.CrmCustomerContactsMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmflowlog.CrmFlowLogMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmrecord.CrmRecordMapper;
import co.yixiang.yshop.module.crm.enums.CluesStatusEnum;
import co.yixiang.yshop.module.crm.enums.FlowLogStatusEnum;
import co.yixiang.yshop.module.crm.enums.RelationEnum;
import co.yixiang.yshop.module.crm.enums.TypesEnum;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import co.yixiang.yshop.module.system.dal.dataobject.notice.NoticeDO;
import co.yixiang.yshop.module.system.dal.mysql.notice.NoticeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商机 Service 实现类
 *
 * @author yshop
 */
@Service
@Validated
public class CrmIndexServiceImpl implements CrmIndexService {

    @Resource
    private CrmBusinessMapper businessMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmCluesMapper crmCluesMapper;
    @Resource
    private CrmContractMapper contractMapper;
    @Resource
    private CrmContractReceivablesMapper contractReceivablesMapper;
    @Resource
    private CrmRecordMapper crmRecordMapper;
    @Resource
    private CrmCustomerContactsMapper customerContactsMapper;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private CrmFlowLogMapper crmFlowLogMapper;

    @Resource
    private AdminUserApi adminUserApi;



    @Override
    public CrmIndexRespVO getIndexCount(String relation) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Date todayStart = DateUtil.beginOfDay(new Date());
        Date todayEnd = DateUtil.endOfDay(new Date());
        Date day30 = DateUtil.offsetDay(new Date(),30);

        Long followCustomerCount = customerMapper.selectCount(new LambdaQueryWrapper<CrmCustomerDO>()
                .between(CrmCustomerDO::getNextTime,todayStart,todayEnd).eq(CrmCustomerDO::getOwnerUserId,loginUserId));
        Long followBusinessCount = businessMapper.selectCount(new LambdaQueryWrapper<CrmBusinessDO>()
                .between(CrmBusinessDO::getNextTime,todayStart,todayEnd).eq(CrmBusinessDO::getOwnerUserId,loginUserId));
        Long followCluesCount = crmCluesMapper.selectCount(new LambdaQueryWrapper<CrmCluesDO>()
                .between(CrmCluesDO::getNextTime,todayStart,todayEnd)
                .eq(CrmCluesDO::getStatus, CluesStatusEnum.STATUS_0.getValue())
                .eq(CrmCluesDO::getOwnerUserId,loginUserId));
//        Long contractDueCount = contractMapper.selectCount(new LambdaQueryWrapper<CrmContractDO>()
//                .between(CrmContractDO::getEndTime,todayStart,day30).eq(CrmContractDO::getOwnerUserId,loginUserId));
//        Long customerDueCount = 0L;
//        Long contractReturnCount = contractMapper.selectCount(new LambdaQueryWrapper<CrmContractDO>()
//                .eq(CrmContractDO::getOwnerUserId,loginUserId).apply("money > return_money"));
//        Long myInvoiceCount = crmInvoiceMapper.selectCount(new LambdaQueryWrapper<CrmInvoiceDO>()
//                .eq(CrmInvoiceDO::getCreator,loginUserId));
//        Long productCount = storeProductMapper.selectCount();
//
        Long contractCheckCount = crmFlowLogMapper.selectCount(new LambdaQueryWrapper<CrmFlowLogDO>()
                .eq(CrmFlowLogDO::getTypes, TypesEnum.CONTRACT.getValue())
                .eq(CrmFlowLogDO::getStatus, FlowLogStatusEnum.STATUS_0.getValue())
                .eq(CrmFlowLogDO::getAdminId,loginUserId));
        Long receivablesCheckCount = crmFlowLogMapper.selectCount(new LambdaQueryWrapper<CrmFlowLogDO>()
                .eq(CrmFlowLogDO::getTypes, TypesEnum.RECEIVABLES.getValue())
                .eq(CrmFlowLogDO::getStatus, FlowLogStatusEnum.STATUS_0.getValue())
                .eq(CrmFlowLogDO::getAdminId,loginUserId));
        Long invoiceCheckCount = crmFlowLogMapper.selectCount(new LambdaQueryWrapper<CrmFlowLogDO>()
                .eq(CrmFlowLogDO::getTypes, TypesEnum.INVOICE.getValue())
                .eq(CrmFlowLogDO::getStatus, FlowLogStatusEnum.STATUS_0.getValue())
                .eq(CrmFlowLogDO::getAdminId,loginUserId));

        return CrmIndexRespVO.builder()
               .followCustomerCount(followCustomerCount)
              .followBusinessCount(followBusinessCount)
               .followCluesCount(followCluesCount)
//                .contractDueCount(contractDueCount)
//                .customerDueCount(customerDueCount)
//                .contractReturnCount(contractReturnCount)
//                .myInvoiceCount(myInvoiceCount)
//                .productCount(productCount)
               .contractCheckCount(contractCheckCount)
               .receivablesCheckCount(receivablesCheckCount)
                .invoiceCheckCount(invoiceCheckCount)
                .brieCountVO(getBrieCount(relation))
                .build();
    }


    /**
     * 统计当天
     * @return
     */
    private BrieCountVO getBrieCount(String relation) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Date todayStart = DateUtil.beginOfDay(new Date());
        Date todayEnd = DateUtil.endOfDay(new Date());

        Date yesterdayStart = DateUtil.beginOfDay(DateUtil.yesterday());
        Date yesterdayEnd = DateUtil.endOfDay(DateUtil.yesterday());

        List<Long> ids = new ArrayList<>();
        Long adminId = SecurityFrameworkUtils.getLoginUserId();
        if(RelationEnum.MY.getValue().equals(relation)){
            ids.add(adminId);
        }else if(RelationEnum.SUB.getValue().equals(relation)){
            ids = adminUserApi.getUserListBySubordinateIds(adminId);
        }

        Long count01 = businessMapper.selectCount(new LambdaQueryWrapper<CrmBusinessDO>()
                .in(!ids.isEmpty(),CrmBusinessDO::getOwnerUserId,ids)
                .between(CrmBusinessDO::getCreateTime,todayStart,todayEnd));
        Long count001 = businessMapper.selectCount(new LambdaQueryWrapper<CrmBusinessDO>()
                .in(!ids.isEmpty(),CrmBusinessDO::getOwnerUserId,ids)
                .between(CrmBusinessDO::getCreateTime,yesterdayStart,yesterdayEnd));
        String per001 = "0";
        if(count001 > 0){
            per001 = NumberUtil.div(NumberUtil.sub(count01,count001),count001,2)
                    .multiply(new BigDecimal("100")).toString();
        }

        Long count02 = crmCluesMapper.selectCount(new LambdaQueryWrapper<CrmCluesDO>()
                .in(!ids.isEmpty(),CrmCluesDO::getOwnerUserId,ids)
                .between(CrmCluesDO::getCreateTime,todayStart,todayEnd));
        Long count002 = crmCluesMapper.selectCount(new LambdaQueryWrapper<CrmCluesDO>()
                .in(!ids.isEmpty(),CrmCluesDO::getOwnerUserId,ids)
                .between(CrmCluesDO::getCreateTime,yesterdayStart,yesterdayEnd));
        String per002 = "0";
        if(count002 > 0){
            per002 = NumberUtil.div(NumberUtil.sub(count02,count002),count002,2)
                    .multiply(new BigDecimal("100")).toString();
        }

        Long count03 = customerMapper.selectCount(new LambdaQueryWrapper<CrmCustomerDO>()
                .in(!ids.isEmpty(),CrmCustomerDO::getOwnerUserId,ids)
                .between(CrmCustomerDO::getCreateTime,todayStart,todayEnd));
        Long count003 = customerMapper.selectCount(new LambdaQueryWrapper<CrmCustomerDO>()
                .in(!ids.isEmpty(),CrmCustomerDO::getOwnerUserId,ids)
                .between(CrmCustomerDO::getCreateTime,yesterdayStart,yesterdayEnd));
        String per003 = "0";
        if(count003 > 0){
            per003 = NumberUtil.div(NumberUtil.sub(count03,count003),count003,2)
                    .multiply(new BigDecimal("100")).toString();
        }

        Long count04 = crmRecordMapper.selectCount(new LambdaQueryWrapper<CrmRecordDO>()
                .in(!ids.isEmpty(),CrmRecordDO::getCreator,ids)
                .between(CrmRecordDO::getCreateTime,todayStart,todayEnd));
        Long count004 = crmRecordMapper.selectCount(new LambdaQueryWrapper<CrmRecordDO>()
                .in(!ids.isEmpty(),CrmRecordDO::getCreator,ids)
                .between(CrmRecordDO::getCreateTime,yesterdayStart,yesterdayEnd));
        String per004 = "0";
        if(count004 > 0){
            per004 = NumberUtil.div(NumberUtil.sub(count04,count004),count004,2)
                    .multiply(new BigDecimal("100")).toString();
        }

        Long count05 = contractMapper.selectCount(new LambdaQueryWrapper<CrmContractDO>()
                .in(!ids.isEmpty(),CrmContractDO::getOwnerUserId,ids)
                .between(CrmContractDO::getCreateTime,todayStart,todayEnd));
        Long count005 = contractMapper.selectCount(new LambdaQueryWrapper<CrmContractDO>()
                .in(!ids.isEmpty(),CrmContractDO::getOwnerUserId,ids)
                .between(CrmContractDO::getCreateTime,yesterdayStart,yesterdayEnd));
        String per005 = "0";
        if(count005 > 0){
            per005 = NumberUtil.div(NumberUtil.sub(count05,count005),count005,2)
                    .multiply(new BigDecimal("100")).toString();
        }

        QueryWrapper<CrmContractDO> queryWrapper06 = new QueryWrapper<>();
        queryWrapper06.select("sum(money) as count06");
        queryWrapper06.eq("to_days(create_time)","to_days(now())");
        queryWrapper06.in(!ids.isEmpty(),"owner_user_id",ids);
        CrmContractDO crmContractDO = contractMapper.selectOne(queryWrapper06);
        BigDecimal count06 = BigDecimal.ZERO;
        if(crmContractDO != null){
            count06 = crmContractDO.getCount06();
        }

        QueryWrapper<CrmContractDO> queryWrapper006 = new QueryWrapper<>();
        queryWrapper006.select("sum(money) as count006");
        queryWrapper006.eq("to_days(now())-to_days(create_time)",1);
        queryWrapper006.in(!ids.isEmpty(),"owner_user_id",ids);
        CrmContractDO crmContractDO006 = contractMapper.selectOne(queryWrapper006);
        BigDecimal count006 = BigDecimal.ZERO;
        if(crmContractDO006 != null){
            count006 = crmContractDO006.getCount006();
        }
        String per006 = "0";
        if(count006.compareTo(BigDecimal.ZERO) > 0){
            per006 = NumberUtil.div(NumberUtil.sub(count06,count006),count006,2)
                    .multiply(new BigDecimal("100")).toString();
        }


        QueryWrapper<CrmContractReceivablesDO> queryWrapper07 = new QueryWrapper<>();
        queryWrapper07.select("sum(money) as count07");
        queryWrapper07.eq("to_days(create_time)","to_days(now())");
        CrmContractReceivablesDO crmContractReceivablesDO = contractReceivablesMapper.selectOne(queryWrapper07);
        BigDecimal count07 = BigDecimal.ZERO;
        if(crmContractDO != null){
            count07 = crmContractReceivablesDO.getCount07();
        }
        QueryWrapper<CrmContractReceivablesDO> queryWrapper007 = new QueryWrapper<>();
        queryWrapper007.select("sum(money) as count007");
        queryWrapper007.eq("to_days(now())-to_days(create_time)",1);
        CrmContractReceivablesDO crmContractReceivablesDO007 = contractReceivablesMapper.selectOne(queryWrapper007);
        BigDecimal count007 = BigDecimal.ZERO;
        if(crmContractReceivablesDO007 != null){
            count007 = crmContractReceivablesDO007.getCount007();
        }
        String per007 = "0";
        if(count007.compareTo(BigDecimal.ZERO) > 0){
            per007 = NumberUtil.div(NumberUtil.sub(count07,count007),count007,2)
                    .multiply(new BigDecimal("100")).toString();
        }

        Long count08 = customerContactsMapper.selectCount(new LambdaQueryWrapper<CrmCustomerContactsDO>()
                .in(!ids.isEmpty(),CrmCustomerContactsDO::getOwnerUserId,ids)
                .between(CrmCustomerContactsDO::getCreateTime,todayStart,todayEnd));
        Long count008 = customerContactsMapper.selectCount(new LambdaQueryWrapper<CrmCustomerContactsDO>()
                .in(!ids.isEmpty(),CrmCustomerContactsDO::getOwnerUserId,ids)
                .between(CrmCustomerContactsDO::getCreateTime,yesterdayStart,yesterdayEnd));
        String per008 = "0";
        if(count008 > 0){
            per008 = NumberUtil.div(NumberUtil.sub(count08,count008),count008,2)
                    .multiply(new BigDecimal("100")).toString();
        }

        Long count09 = customerMapper.selectCount(new LambdaQueryWrapper<CrmCustomerDO>()
                .between(CrmCustomerDO::getDealTime,todayStart,todayEnd)
                .eq(CrmCustomerDO::getDealStatus, ShopCommonEnum.IS_STATUS_1.getValue())
                .eq(CrmCustomerDO::getCreator,loginUserId));
        Long count10 = customerMapper.selectCount(new LambdaQueryWrapper<CrmCustomerDO>()
                .between(CrmCustomerDO::getDealTime,todayStart,todayEnd)
                .eq(CrmCustomerDO::getDealStatus, ShopCommonEnum.IS_STATUS_0.getValue())
                .eq(CrmCustomerDO::getCreator,loginUserId));
        Long count11 = businessMapper.selectCount(new LambdaQueryWrapper<CrmBusinessDO>()
                .between(CrmBusinessDO::getDealTime,todayStart,todayEnd)
                .eq(CrmBusinessDO::getIsEnd, ShopCommonEnum.IS_STATUS_1.getValue())
                .eq(CrmBusinessDO::getCreator,loginUserId));
        Long count12 = businessMapper.selectCount(new LambdaQueryWrapper<CrmBusinessDO>()
                .between(CrmBusinessDO::getDealTime,todayStart,todayEnd)
                .eq(CrmBusinessDO::getIsEnd, ShopCommonEnum.IS_STATUS_0.getValue())
                .eq(CrmBusinessDO::getCreator,loginUserId));
        Long count13 = noticeMapper.selectCount(new LambdaQueryWrapper<NoticeDO>()
                .between(NoticeDO::getCreateTime,todayStart,todayEnd)
                .eq(NoticeDO::getStatus, CommonStatusEnum.ENABLE.getStatus()));

        return BrieCountVO.builder()
                .count01(count01).count001(count001).per001(per001)
                .count02(count02).count002(count002).per002(per002)
                .count03(count03).count003(count003).per003(per003)
                .count04(count04).count004(count004).per004(per004)
                .count05(count05).count005(count005).per005(per005)
                .count06(count06).count006(count006).per006(per006)
                .count07(count07).count007(count007).per007(per007)
                .count08(count08).count008(count008).per008(per008)
                .count09(count09)
                .count10(count10)
                .count11(count11)
                .count12(count12)
                .count13(count13)
                .build();


    }
}