package co.yixiang.yshop.module.crm.service.crmindex;

import cn.hutool.core.date.DateUtil;
import co.yixiang.yshop.framework.security.core.util.SecurityFrameworkUtils;
import co.yixiang.yshop.module.crm.controller.admin.crmindex.vo.CrmIndexRespVO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmbusiness.CrmBusinessDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmclues.CrmCluesDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcontract.CrmContractDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crmcustomer.CrmCustomerDO;
import co.yixiang.yshop.module.crm.dal.dataobject.crminvoice.CrmInvoiceDO;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmbusiness.CrmBusinessProductMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmclues.CrmCluesMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcontract.CrmContractMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crmcustomer.CrmCustomerMapper;
import co.yixiang.yshop.module.crm.dal.mysql.crminvoice.CrmInvoiceMapper;
import co.yixiang.yshop.module.product.dal.dataobject.storeproduct.StoreProductDO;
import co.yixiang.yshop.module.product.dal.mysql.storeproduct.StoreProductMapper;
import co.yixiang.yshop.module.product.dal.mysql.storeproductattrvalue.StoreProductAttrValueMapper;
import co.yixiang.yshop.module.system.api.user.AdminUserApi;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

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
    private StoreProductMapper storeProductMapper;
    @Resource
    private CrmInvoiceMapper crmInvoiceMapper;
    @Resource
    private CrmCustomerMapper customerMapper;
    @Resource
    private CrmCluesMapper crmCluesMapper;
    @Resource
    private CrmContractMapper contractMapper;


    @Override
    public CrmIndexRespVO getIndexCount() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Date todayStart = DateUtil.beginOfDay(new Date());
        Date todayEnd = DateUtil.endOfDay(new Date());
        Date day30 = DateUtil.offsetDay(new Date(),30);

        Long followCustomerCount = customerMapper.selectCount(new LambdaQueryWrapper<CrmCustomerDO>()
                .between(CrmCustomerDO::getNextTime,todayStart,todayEnd).eq(CrmCustomerDO::getOwnerUserId,loginUserId));
        Long followBusinessCount = businessMapper.selectCount(new LambdaQueryWrapper<CrmBusinessDO>()
                .between(CrmBusinessDO::getNextTime,todayStart,todayEnd).eq(CrmBusinessDO::getOwnerUserId,loginUserId));
        Long followCluesCount = crmCluesMapper.selectCount(new LambdaQueryWrapper<CrmCluesDO>()
                .between(CrmCluesDO::getNextTime,todayStart,todayEnd).eq(CrmCluesDO::getOwnerUserId,loginUserId));
        Long contractDueCount = contractMapper.selectCount(new LambdaQueryWrapper<CrmContractDO>()
                .between(CrmContractDO::getEndTime,todayStart,day30).eq(CrmContractDO::getOwnerUserId,loginUserId));
        Long customerDueCount = 0L;
        Long contractReturnCount = contractMapper.selectCount(new LambdaQueryWrapper<CrmContractDO>()
                .eq(CrmContractDO::getOwnerUserId,loginUserId).apply("money > return_money"));
        Long myInvoiceCount = crmInvoiceMapper.selectCount(new LambdaQueryWrapper<CrmInvoiceDO>()
                .eq(CrmInvoiceDO::getCreator,loginUserId));
        Long productCount = storeProductMapper.selectCount();

        return CrmIndexRespVO.builder()
                .followCustomerCount(followCustomerCount)
                .followBusinessCount(followBusinessCount)
                .followCluesCount(followCluesCount)
                .contractDueCount(contractDueCount)
                .customerDueCount(customerDueCount)
                .contractReturnCount(contractReturnCount)
                .myInvoiceCount(myInvoiceCount)
                .productCount(productCount)
                .build();
    }
}